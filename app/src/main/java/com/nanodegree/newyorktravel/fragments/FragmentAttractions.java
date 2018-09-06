package com.nanodegree.newyorktravel.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nanodegree.newyorktravel.R;
import com.nanodegree.newyorktravel.activities.AttractionDetail;
import com.nanodegree.newyorktravel.adapters.AttractionsAdapter;
import com.nanodegree.newyorktravel.holders.Attraction;

import java.util.ArrayList;

public class FragmentAttractions extends Fragment {

    private static final String TAG = FragmentAttractions.class.getSimpleName();
    private RecyclerView recyclerView;
    private AttractionsAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_attractions, container, false);

        recyclerView = view.findViewById(R.id.frag_attractions_recycler);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference attractionRef = database.getReference("attractions");

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new AttractionsAdapter(getActivity(), new ArrayList<Attraction>(), attractionListener);
        recyclerView.setAdapter(adapter);

        attractionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Attraction> attractions = new ArrayList<>();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Attraction attraction = postSnapshot.getValue(Attraction.class);
                    if(attraction != null) {
                        attraction.setId(postSnapshot.getKey());
                        attractions.add(attraction);
                    }
                }
                adapter.updateAttractions(attractions);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "onCancelled: ", databaseError.toException());
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    AttractionsAdapter.AttractionListener attractionListener = new AttractionsAdapter.AttractionListener() {
        @Override
        public void onAttractionClicked(Attraction attraction) {
            if(getActivity() != null && !getActivity().isFinishing()){
                Intent intent = new Intent(getActivity(), AttractionDetail.class);
                intent.putExtra(AttractionDetail.TAG_ATTRACTION, attraction);
                getActivity().startActivityForResult(intent, AttractionDetail.ACTIVITY_DETAIL_REQ_CODE);
            }
        }
    };
}