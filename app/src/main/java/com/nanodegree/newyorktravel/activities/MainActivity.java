package com.nanodegree.newyorktravel.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.nanodegree.newyorktravel.R;
import com.nanodegree.newyorktravel.fragments.FragmentAttractions;
import com.nanodegree.newyorktravel.fragments.FragmentMap;
import com.nanodegree.newyorktravel.fragments.FragmentReviews;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_ATTRACTION_ID = "extra_attraction_id";
    public static final String EXTRA_GOTO_MAP = "extra_goto_map";
    public static final String EXTRA_GOTO_REVIEWS = "extra_goto_reviews";

    private static final String TAG = MainActivity.class.getSimpleName();

    private BottomNavigationView navigation;
    private FragmentAttractions fragmentAttractions;
    private FragmentMap fragmentMap;
    private FragmentReviews fragmentReviews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        fragmentAttractions = new FragmentAttractions();
        fragmentMap = new FragmentMap();
        fragmentReviews = new FragmentReviews();

        setFragment(fragmentAttractions);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Activity detail may call for a page change.
        if(resultCode == Activity.RESULT_OK && requestCode == AttractionDetail.ACTIVITY_DETAIL_REQ_CODE && data != null && data.hasExtra(EXTRA_ATTRACTION_ID)){
            if(data.getBooleanExtra(EXTRA_GOTO_MAP, false)) {
                if(data.hasExtra(EXTRA_ATTRACTION_ID)) {
                    String selectedAttractionId = data.getStringExtra(EXTRA_ATTRACTION_ID);
                    fragmentMap.setSelectedAttraction(selectedAttractionId);
                }
                setFragment(fragmentMap);
                navigation.setSelectedItemId(R.id.navigation_map);
            }else if(data.getBooleanExtra(EXTRA_GOTO_REVIEWS, false)){
                //We pass through an attraction ID, to tell the fragment what attraction to select.
                if(data.hasExtra(EXTRA_ATTRACTION_ID)) {
                    String selectedAttractionId = data.getStringExtra(EXTRA_ATTRACTION_ID);
                    fragmentReviews.setSelectedAttraction(selectedAttractionId);
                }
                setFragment(fragmentReviews);
                navigation.setSelectedItemId(R.id.navigation_reviews);
            }
        }
    }

    private void setFragment(Fragment frag) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.activity_main_container, frag, frag.getClass().getSimpleName());
        ft.commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_attractions:
                    setFragment(fragmentAttractions);
                    return true;
                case R.id.navigation_map:
                    setFragment(fragmentMap);
                    return true;
                case R.id.navigation_reviews:
                    setFragment(fragmentReviews);
                    return true;
            }
            return false;
        }
    };
}