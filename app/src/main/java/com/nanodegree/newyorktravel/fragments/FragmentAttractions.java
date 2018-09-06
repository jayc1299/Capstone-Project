package com.nanodegree.newyorktravel.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.nanodegree.newyorktravel.R;
import com.nanodegree.newyorktravel.activities.AttractionDetail;
import com.nanodegree.newyorktravel.adapters.AttractionsAdapter;
import com.nanodegree.newyorktravel.holders.Attraction;
import com.nanodegree.newyorktravel.utils.UiUitls;
import com.nanodegree.newyorktravel.utils.UserUtils;

import java.util.ArrayList;

public class FragmentAttractions extends Fragment {

    private static final String TAG = FragmentAttractions.class.getSimpleName();
    private RecyclerView recyclerView;
    private AttractionsAdapter adapter;
    private SharedPreferences mPrefs;
    private UserUtils userUtils;
    private FirebaseDatabase database;
    private UiUitls uiUitls;

	private static final String SAVED_LAYOUT_MANAGER = "saved_layout_manager";
	private Parcelable layoutManagerSavedState;

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

        database = FirebaseDatabase.getInstance();
        mPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        userUtils = new UserUtils();
        uiUitls = new UiUitls();

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new AttractionsAdapter(getActivity(), new ArrayList<Attraction>(), attractionListener);
        recyclerView.setAdapter(adapter);

        refreshData();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        Parcelable state = recyclerView.getLayoutManager().onSaveInstanceState();
        outState.putParcelable(SAVED_LAYOUT_MANAGER, state);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            layoutManagerSavedState = savedInstanceState.getParcelable(SAVED_LAYOUT_MANAGER);
        }
    }

    private boolean isShowFavourites() {
        return mPrefs.getBoolean(getString(R.string.pref_favs_key), false);
    }

    public void refreshData() {
        if (getActivity() != null && !getActivity().isFinishing()) {
            if (isShowFavourites()) {
                DatabaseReference favsRef = database.getReference("favouriteAttractions");
                Query favsQuery = favsRef.orderByChild("userId").equalTo(userUtils.getUserId(getActivity()));
                favsQuery.addValueEventListener(attractionValueEventListener);
            } else {
                DatabaseReference attractionRef = database.getReference("attractions");
                attractionRef.addValueEventListener(attractionValueEventListener);
            }
        }
    }

    ValueEventListener attractionValueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            ArrayList<Attraction> attractions = new ArrayList<>();
            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                Attraction attraction = postSnapshot.getValue(Attraction.class);
                if (attraction != null) {
                    attraction.setId(postSnapshot.getKey());
                    attractions.add(attraction);
                }
            }
            adapter.updateAttractions(attractions);

			if (layoutManagerSavedState != null) {
				recyclerView.getLayoutManager().onRestoreInstanceState(layoutManagerSavedState);
			}
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "onCancelled: ", databaseError.toException());
			uiUitls.showErrorAlert(getActivity(), databaseError.toException());
        }
    };

    AttractionsAdapter.AttractionListener attractionListener = new AttractionsAdapter.AttractionListener() {
        @Override
        public void onAttractionClicked(Attraction attraction) {
            if (getActivity() != null && !getActivity().isFinishing()) {
                Intent intent = new Intent(getActivity(), AttractionDetail.class);
                intent.putExtra(AttractionDetail.TAG_ATTRACTION, attraction);
                getActivity().startActivityForResult(intent, AttractionDetail.ACTIVITY_DETAIL_REQ_CODE);
            }
        }
    };
}