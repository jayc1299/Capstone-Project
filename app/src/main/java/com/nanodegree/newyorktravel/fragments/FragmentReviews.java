package com.nanodegree.newyorktravel.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.nanodegree.newyorktravel.R;
import com.nanodegree.newyorktravel.activities.ActivityAddReview;
import com.nanodegree.newyorktravel.activities.ActivityReviewDetail;
import com.nanodegree.newyorktravel.adapters.ReviewsAdapter;
import com.nanodegree.newyorktravel.holders.Attraction;
import com.nanodegree.newyorktravel.holders.Review;
import com.nanodegree.newyorktravel.utils.UiUitls;

import java.util.ArrayList;
import java.util.List;

public class FragmentReviews extends Fragment {

    private static final String TAG = FragmentReviews.class.getSimpleName();

    private TextView emptyView;
    private RecyclerView recyclerView;
    private ReviewsAdapter reviewsAdapter;
    private Spinner attractionSpinner;
    private View addFabBtn;

    private FirebaseDatabase database;
    private ArrayAdapter<String> dataAdapter;
    private ArrayList<Attraction> attractions;
    private Attraction selectedAttraction;
    private String changeSelectedAttractionRequestId;
    private UiUitls uiUitls;

	private static final String SAVED_LAYOUT_MANAGER = "saved_layout_manager";
	private static final String SAVED_SELECTED_ATTRACION = "saved_selected_attraction";
	private Parcelable layoutManagerSavedState;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reviews, container, false);

        emptyView = view.findViewById(R.id.fragment_reviews_empty);
        recyclerView = view.findViewById(R.id.frag_reviews_recycler);
        attractionSpinner = view.findViewById(R.id.fragment_reviews_spinner);
        addFabBtn = view.findViewById(R.id.fragment_reviews_add_review);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        database = FirebaseDatabase.getInstance();
        uiUitls = new UiUitls();

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        reviewsAdapter = new ReviewsAdapter(reviewsListener, new ArrayList<Review>());
        recyclerView.setAdapter(reviewsAdapter);

        addFabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ActivityAddReview.class);
                startActivity(intent);
            }
        });

		setupAttractionSpinner();
    }

	@Override
	public void onSaveInstanceState(@NonNull Bundle outState) {
		Parcelable state = recyclerView.getLayoutManager().onSaveInstanceState();
		outState.putParcelable(SAVED_LAYOUT_MANAGER, state);
		outState.putString(SAVED_SELECTED_ATTRACION, selectedAttraction.getId());
		super.onSaveInstanceState(outState);
	}

	@Override
	public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
		super.onViewStateRestored(savedInstanceState);
		if (savedInstanceState != null) {
			layoutManagerSavedState = savedInstanceState.getParcelable(SAVED_LAYOUT_MANAGER);
			changeSelectedAttractionRequestId = savedInstanceState.getString(SAVED_SELECTED_ATTRACION, "");
		}
	}

    /**
     * Other components need to tell the fragment what attraction to set as selected
     *
     * @param attractionId attraction ID.
     */
    public void setSelectedAttraction(String attractionId){
        changeSelectedAttractionRequestId = attractionId;
    }

	/**
	 * Setup the spinner with attractions
	 */
	private void setupAttractionSpinner() {
        dataAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, new ArrayList<String>());
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        attractionSpinner.setAdapter(dataAdapter);

        DatabaseReference attractionRef = database.getReference("attractions");

        attractionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> simpleAttractions = new ArrayList<>();
                attractions = new ArrayList<>();
                int i = 0;
                int selectedIndex = 0;
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Attraction attraction = postSnapshot.getValue(Attraction.class);
                    if (attraction != null) {
                        attraction.setId(postSnapshot.getKey());
                        simpleAttractions.add(attraction.getName());
                        attractions.add(attraction);
                        if(changeSelectedAttractionRequestId != null && attraction.getId().equals(changeSelectedAttractionRequestId)){
                            selectedIndex = i;
                        }
                        i++;
                    }
                }

                //Reload reviewsAdapter data
                dataAdapter.clear();
                dataAdapter.addAll(simpleAttractions);
                dataAdapter.notifyDataSetChanged();

                attractionSpinner.setSelection(selectedIndex);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "onCancelled: ", databaseError.toException());
				uiUitls.showErrorAlert(getActivity(), databaseError.toException());
            }
        });

        //Set selected attraction
        attractionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                selectedAttraction = attractions.get(position);
                getReviews();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //Do nothing
            }
        });
    }

    private void getReviews() {
        DatabaseReference reviewRef = database.getReference("reviews");
        Query reviewQuery = reviewRef.orderByChild("attractionId").equalTo(selectedAttraction.getId());

        reviewQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Review> reviews = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Review review = snapshot.getValue(Review.class);
                    if (review != null) {
                        reviews.add(review);
                    }
                }

                if (reviews.size() > 0) {
                    reviewsAdapter.updateReviews(reviews);
                    emptyView.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }else{
                    emptyView.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }

				if (layoutManagerSavedState != null) {
					recyclerView.getLayoutManager().onRestoreInstanceState(layoutManagerSavedState);
				}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "onCancelled: ", databaseError.toException());
				uiUitls.showErrorAlert(getActivity(), databaseError.toException());
            }
        });
    }

    ReviewsAdapter.ReviewsListener reviewsListener = new ReviewsAdapter.ReviewsListener() {
        @Override
        public void onReviewClicked(Review review) {
            Intent intent = new Intent(getActivity(), ActivityReviewDetail.class);
            intent.putExtra(ActivityReviewDetail.TAG_REVIEW, review);
            startActivity(intent);
        }
    };
}