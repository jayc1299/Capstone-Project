package com.nanodegree.newyorktravel.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nanodegree.newyorktravel.R;
import com.nanodegree.newyorktravel.activities.AttractionDetail;
import com.nanodegree.newyorktravel.holders.Attraction;

public class FragmentMap extends Fragment {

    private static final String TAG = FragmentMap.class.getSimpleName();
    private GoogleMap mMap;
    private SupportMapFragment mapFragment;
    private String changeSelectedAttractionRequestId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        if (mapFragment == null) {
            mapFragment = SupportMapFragment.newInstance();
        }
        mapFragment.getMapAsync(mapReadyCallback);

        getChildFragmentManager().beginTransaction().replace(R.id.map, mapFragment).commit();

        return view;
    }

    /**
     * Other components have the ability to force the map fragment to highlight a specific attraction.
     *
     * @param attractionId string attraction ID.
     */
    public void setSelectedAttraction(String attractionId) {
        changeSelectedAttractionRequestId = attractionId;
    }

    @Override
    public void onPause() {
        super.onPause();
        changeSelectedAttractionRequestId = null;
    }

    OnMapReadyCallback mapReadyCallback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;

            //Add marker click callback so we can link
            googleMap.setOnInfoWindowClickListener(markerClickListener);

            // Add a marker in NYC and move the camera
            LatLng nyc = new LatLng(40.7127837, -74.0059413);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(nyc));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(11));

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference attractionRef = database.getReference("attractions");

            attractionRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Log.d(TAG, "onDataChange: ");
                    LatLng selectedMarker = null;
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        Attraction attraction = postSnapshot.getValue(Attraction.class);
                        if (attraction != null && attraction.getLatitude() != 0 && attraction.getLongitude() != 0) {
                            attraction.setId(postSnapshot.getKey());
                            LatLng markerLocation = new LatLng(attraction.getLatitude(), attraction.getLongitude());
                            Marker marker = mMap.addMarker(new MarkerOptions().position(markerLocation).title(attraction.getName()));
                            marker.setTag(attraction);
                            //If we want a specific marker selected, let's show its window.
                            if (changeSelectedAttractionRequestId != null && changeSelectedAttractionRequestId.equals(attraction.getId())) {
                                marker.showInfoWindow();
                                selectedMarker = markerLocation;
                            }
                        }
                    }

                    if (selectedMarker != null) {
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(selectedMarker));
                        mMap.animateCamera(CameraUpdateFactory.zoomTo(12));
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e(TAG, "onCancelled: ", databaseError.toException());
                }
            });
        }
    };

    GoogleMap.OnInfoWindowClickListener markerClickListener = new GoogleMap.OnInfoWindowClickListener() {
        @Override
        public void onInfoWindowClick(Marker marker) {
            //Open the attraction detail page.
            Intent intent = new Intent(getActivity(), AttractionDetail.class);
            intent.putExtra(AttractionDetail.TAG_ATTRACTION, (Attraction) marker.getTag());
            getActivity().startActivityForResult(intent, AttractionDetail.ACTIVITY_DETAIL_REQ_CODE);
        }
    };
}