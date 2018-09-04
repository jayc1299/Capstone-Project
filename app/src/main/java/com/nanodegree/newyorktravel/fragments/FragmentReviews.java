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
import android.widget.TextView;

import com.nanodegree.newyorktravel.R;
import com.nanodegree.newyorktravel.adapters.AttractionsAdapter;
import com.nanodegree.newyorktravel.adapters.ReviewsAdapter;
import com.nanodegree.newyorktravel.holders.Attraction;
import com.nanodegree.newyorktravel.holders.Review;

import java.util.ArrayList;

public class FragmentReviews extends Fragment{

    private TextView emptyView;
    private RecyclerView recyclerView;
    private ReviewsAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reviews, container, false);

        emptyView = view.findViewById(R.id.fragment_reviews_empty);
        recyclerView = view.findViewById(R.id.frag_reviews_recycler);

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
    }
}