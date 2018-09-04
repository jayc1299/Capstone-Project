package com.nanodegree.newyorktravel.fragments;

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
        attractions.add(new Attraction("Empire State Building"));
        attractions.add(new Attraction("Statue Of Liberty"));
        attractions.add(new Attraction("Rockerfella Building"));
        attractions.add(new Attraction("Madison Square Guardens"));
        attractions.add(new Attraction("Brooklyn Bridge"));

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new AttractionsAdapter(attractions);
        recyclerView.setAdapter(adapter);
    }
}