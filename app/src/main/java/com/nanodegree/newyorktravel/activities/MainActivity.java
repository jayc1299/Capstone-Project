package com.nanodegree.newyorktravel.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.nanodegree.newyorktravel.R;
import com.nanodegree.newyorktravel.async.NetworkAsync;
import com.nanodegree.newyorktravel.fragments.FragmentAttractions;
import com.nanodegree.newyorktravel.fragments.FragmentMap;
import com.nanodegree.newyorktravel.fragments.FragmentReviews;
import com.nanodegree.newyorktravel.utils.UserUtils;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_ATTRACTION_ID = "extra_attraction_id";
    public static final String EXTRA_GOTO_MAP = "extra_goto_map";
    public static final String EXTRA_GOTO_REVIEWS = "extra_goto_reviews";
    public static final int SETTINGS_REQUEST_CODE = 2;

    private BottomNavigationView navigation;
    private FragmentAttractions fragmentAttractions;
    private FragmentMap fragmentMap;
    private FragmentReviews fragmentReviews;
    private UserUtils userUtils;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NetworkAsync networkAsync = new NetworkAsync(networkListener);
        networkAsync.execute();

        userUtils = new UserUtils();
        createUserId();

        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        fragmentAttractions = new FragmentAttractions();
        fragmentMap = new FragmentMap();
        fragmentReviews = new FragmentReviews();

        if (savedInstanceState == null) {
            setFragment(fragmentAttractions);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(this, ActivitySettings.class);
                startActivityForResult(intent, SETTINGS_REQUEST_CODE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Activity detail may call for a page change.
        if (resultCode == Activity.RESULT_OK && requestCode == AttractionDetail.ACTIVITY_DETAIL_REQ_CODE && data != null && data.hasExtra(EXTRA_ATTRACTION_ID)) {
            if (data.getBooleanExtra(EXTRA_GOTO_MAP, false)) {
                if (data.hasExtra(EXTRA_ATTRACTION_ID)) {
                    String selectedAttractionId = data.getStringExtra(EXTRA_ATTRACTION_ID);
                    fragmentMap.setSelectedAttraction(selectedAttractionId);
                }
                setFragment(fragmentMap);
                navigation.setSelectedItemId(R.id.navigation_map);
            } else if (data.getBooleanExtra(EXTRA_GOTO_REVIEWS, false)) {
                //We pass through an attraction ID, to tell the fragment what attraction to select.
                if (data.hasExtra(EXTRA_ATTRACTION_ID)) {
                    String selectedAttractionId = data.getStringExtra(EXTRA_ATTRACTION_ID);
                    fragmentReviews.setSelectedAttraction(selectedAttractionId);
                }
                setFragment(fragmentReviews);
                navigation.setSelectedItemId(R.id.navigation_reviews);
            }
        } else if (resultCode == Activity.RESULT_OK && requestCode == SETTINGS_REQUEST_CODE) {
            fragmentAttractions.refreshData();
        }
    }

    /**
     * Set the currently displayed fragment
     *
     * @param frag fragment to display
     */
    private void setFragment(Fragment frag) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.activity_main_container, frag, frag.getClass().getSimpleName());
        ft.commit();
    }

    /**
     * This is only a temp userId as a full authentication session is outside the scope of this task.
     */
    private void createUserId() {
        if (TextUtils.isEmpty(userUtils.getUserId(this))) {
            userUtils.setUserId(this, UUID.randomUUID().toString());
        }
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

    private static final String TAG = MainActivity.class.getSimpleName();

    private NetworkAsync.NetworkAsyncListener networkListener = new NetworkAsync.NetworkAsyncListener() {
        @Override
        public void showProgressDialog(boolean show) {
            if (!MainActivity.this.isFinishing()) {
                if (show) {
                    dialog = new ProgressDialog(MainActivity.this);
                    dialog.setMessage(getString(R.string.loading));
                    dialog.setCancelable(false);
                    dialog.show();
                } else {
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            }
        }

        @Override
        public void isInternetOk(boolean isOk) {
            if (!isOk) {
                AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle(getString(R.string.no_internet_title))
                        .setMessage(getString(R.string.no_internet_msg))
                        .create();
                dialog.show();
            }
        }
    };
}