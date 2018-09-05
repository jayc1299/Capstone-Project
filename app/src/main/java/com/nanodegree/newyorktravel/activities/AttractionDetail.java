package com.nanodegree.newyorktravel.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.nanodegree.newyorktravel.R;
import com.nanodegree.newyorktravel.holders.Attraction;

public class AttractionDetail extends AppCompatActivity {

    public static final String TAG_ATTRACTION = "tag_attraction";
    private static final String TAG = AttractionDetail.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if(getIntent() != null && getIntent().getExtras() != null){
            Bundle bundle = getIntent().getExtras();

            Attraction attraction = bundle.getParcelable(TAG_ATTRACTION);
            setupAttraction(attraction);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setupAttraction(Attraction attraction){
        setTitle(attraction.getName());

        TextView desc = findViewById(R.id.attraction_detail_description);
        desc.setText(attraction.getDescription());
    }

    private void setTitle(String title){
        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }
}