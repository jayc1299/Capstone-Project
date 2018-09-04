package com.nanodegree.newyorktravel;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.nanodegree.newyorktravel.fragments.FragmentAttractions;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentAttractions frag = new FragmentAttractions();
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    setFragment(frag, getString(R.string.tab_attractions));
                    return true;
                case R.id.navigation_dashboard:
                    setFragment(frag, getString(R.string.tab_map));
                    return true;
                case R.id.navigation_notifications:
                    setFragment(frag, getString(R.string.tab_reviews));
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

        setFragment(new FragmentAttractions(), getString(R.string.tab_attractions));
    }

    private void setFragment(Fragment frag, String name) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString(FragmentAttractions.TAG_NAME, name);
        frag.setArguments(bundle);
        ft.replace(R.id.activity_main_container, frag, frag.getClass().getSimpleName());
        ft.commit();
    }
}