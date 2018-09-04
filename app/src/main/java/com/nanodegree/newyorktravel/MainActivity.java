package com.nanodegree.newyorktravel;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.nanodegree.newyorktravel.fragments.FragmentAttractions;
import com.nanodegree.newyorktravel.fragments.FragmentMap;
import com.nanodegree.newyorktravel.fragments.FragmentReviews;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Bundle bundle;

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    FragmentAttractions frag = new FragmentAttractions();
                    setFragment(frag);
                    return true;
                case R.id.navigation_dashboard:
                    FragmentMap fragMap = new FragmentMap();
                    bundle = new Bundle();
                    bundle.putString(FragmentMap.TAG_NAME, getString(R.string.tab_map));
                    fragMap.setArguments(bundle);
                    setFragment(fragMap);
                    return true;
                case R.id.navigation_notifications:
                    FragmentReviews fragReviews = new FragmentReviews();
                    bundle = new Bundle();
                    bundle.putString(FragmentMap.TAG_NAME, getString(R.string.tab_reviews));
                    fragReviews.setArguments(bundle);
                    setFragment(fragReviews);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        setFragment(new FragmentAttractions());
    }

    private void setFragment(Fragment frag) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.activity_main_container, frag, frag.getClass().getSimpleName());
        ft.commit();
    }
}