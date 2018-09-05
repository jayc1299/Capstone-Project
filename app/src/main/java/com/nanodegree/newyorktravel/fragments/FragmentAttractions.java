package com.nanodegree.newyorktravel.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nanodegree.newyorktravel.R;
import com.nanodegree.newyorktravel.activities.AttractionDetail;
import com.nanodegree.newyorktravel.adapters.AttractionsAdapter;
import com.nanodegree.newyorktravel.holders.Attraction;

import java.util.ArrayList;

public class FragmentAttractions extends Fragment {

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

        ArrayList<Attraction> attractions = new ArrayList<>();
        Attraction attraction = new Attraction("Empire State Building");
        attraction.setImageUrl("https://guideandgo.com/image/cache/catalog/new-york/products/main-deck-ticket-empire-state-building/empire-state-building-3-1000x1000.jpg");
        attraction.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.");
        attractions.add(attraction);
        attraction = new Attraction("Statue Of Liberty");
        attraction.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.");
        attractions.add(attraction);
        attraction = new Attraction("Rockerfella Building");
        attraction.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.");
        attractions.add(attraction);
        attraction = new Attraction("Madison Square Guardens");
        attraction.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.");
        attractions.add(attraction);
        attraction = new Attraction("Brooklyn Bridge");
        attraction.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. \n\n Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.");
        attractions.add(attraction);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new AttractionsAdapter(getActivity(), attractions, attractionListener);
        recyclerView.setAdapter(adapter);
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