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
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.nanodegree.newyorktravel.R;
import com.nanodegree.newyorktravel.adapters.ReviewsAdapter;
import com.nanodegree.newyorktravel.holders.Review;

import java.util.ArrayList;
import java.util.List;

public class FragmentReviews extends Fragment {

    private TextView emptyView;
    private RecyclerView recyclerView;
    private ReviewsAdapter adapter;
    private Spinner attractionSpinner;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reviews, container, false);

        emptyView = view.findViewById(R.id.fragment_reviews_empty);
        recyclerView = view.findViewById(R.id.frag_reviews_recycler);
        attractionSpinner = view.findViewById(R.id.fragment_reviews_spinner);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ArrayList<Review> attractions = new ArrayList<>();
        Review review = new Review();
        review.setReviewTitle("I love the Empire State");
        review.setReviewContent("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.");
        attractions.add(review);
        review = new Review();
        review.setReviewTitle("I hate the Empire State");
        review.setReviewContent("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.");
        attractions.add(review);
        review = new Review();
        review.setReviewTitle("Boring Exhibit");
        review.setReviewContent("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.");
        attractions.add(review);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ReviewsAdapter(attractions);
        recyclerView.setAdapter(adapter);

        List<String> simpleAttractions = new ArrayList<>();
        simpleAttractions.add("Empire State Building");
        simpleAttractions.add("Statue Of Liberty");
        simpleAttractions.add("Central Park");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, simpleAttractions);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        attractionSpinner.setAdapter(dataAdapter);
    }
}