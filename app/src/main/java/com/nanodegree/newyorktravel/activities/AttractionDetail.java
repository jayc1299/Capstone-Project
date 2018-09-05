package com.nanodegree.newyorktravel.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nanodegree.newyorktravel.R;
import com.nanodegree.newyorktravel.holders.Attraction;
import com.squareup.picasso.Picasso;

public class AttractionDetail extends AppCompatActivity {

    public static final int ACTIVITY_DETAIL_REQ_CODE = 1;
    public static final String TAG_ATTRACTION = "tag_attraction";

    private Attraction attraction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        if(getIntent() != null && getIntent().getExtras() != null){
            Bundle bundle = getIntent().getExtras();

            attraction = bundle.getParcelable(TAG_ATTRACTION);
            setupAttraction(attraction);
        }

        //Map button
        findViewById(R.id.activity_detail_btn_mapview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra(MainActivity.EXTRA_ATTRACTION_ID, attraction.getId());
                intent.putExtra(MainActivity.EXTRA_GOTO_MAP, true);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

        //Reviews
        findViewById(R.id.activity_detail_btn_reviews).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra(MainActivity.EXTRA_ATTRACTION_ID, attraction.getId());
                intent.putExtra(MainActivity.EXTRA_GOTO_REVIEWS, true);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

        //Favourites
        findViewById(R.id.activity_detail_btn_favourites).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AttractionDetail.this, "Add to Favs", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.activity_detail_star_rating).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra(MainActivity.EXTRA_ATTRACTION_ID, attraction.getId());
                intent.putExtra(MainActivity.EXTRA_GOTO_REVIEWS, true);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
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
        CollapsingToolbarLayout collapsingToolbarLayout =  findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(title);
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
    }
}