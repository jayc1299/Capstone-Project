package com.nanodegree.newyorktravel.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.nanodegree.newyorktravel.R;
import com.nanodegree.newyorktravel.holders.Attraction;
import com.squareup.picasso.Picasso;

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
        ImageView img = findViewById(R.id.activity_detail_imageview);

        if(!TextUtils.isEmpty(attraction.getImageUrl())) {
            Picasso.get().load(attraction.getImageUrl()).into(img);
            img.setContentDescription(attraction.getName());
        }
    }

    private void setTitle(String title){
        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }
}