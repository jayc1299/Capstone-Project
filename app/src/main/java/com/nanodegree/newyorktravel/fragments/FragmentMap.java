package com.nanodegree.newyorktravel.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nanodegree.newyorktravel.R;
import com.nanodegree.newyorktravel.holders.Attraction;

import java.util.ArrayList;

public class FragmentMap extends Fragment{

    private static final String TAG = FragmentMap.class.getSimpleName();
    private GoogleMap mMap;
    private SupportMapFragment mapFragment;

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

    OnMapReadyCallback mapReadyCallback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;

            // Add a marker in NYC and move the camera
            LatLng nyc = new LatLng(40.7127837, -74.0059413);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(nyc));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(10));

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference attractionRef = database.getReference("attractions");

            attractionRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                        Attraction attraction = postSnapshot.getValue(Attraction.class);
                        if(attraction != null && attraction.getLatitude() != 0 && attraction.getLongitude() != 0) {
                            LatLng markerLocation = new LatLng(attraction.getLatitude(), attraction.getLongitude());
                            mMap.addMarker(new MarkerOptions().position(markerLocation).title(attraction.getName()));
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e(TAG, "onCancelled: ", databaseError.toException());
                }
            });
        }
    };
}