package com.nanodegree.newyorktravel.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.RatingBar;
import android.widget.TextView;

import com.nanodegree.newyorktravel.R;
import com.nanodegree.newyorktravel.holders.Review;

public class ActivityReviewDetail extends AppCompatActivity {

	public static final String TAG_REVIEW = "tag_review";

	private Review review;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_review_detail);

		if(getIntent() != null && getIntent().getExtras() != null){
			Bundle bundle = getIntent().getExtras();

			review = bundle.getParcelable(TAG_REVIEW);
			setupReview();
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

	private void setupReview(){
		TextView reviewTitle = findViewById(R.id.review_detail_title);
		TextView reviewContent = findViewById(R.id.review_detail_content);
		RatingBar ratingBar = findViewById(R.id.review_detail_star_rating);

		reviewTitle.setText(review.getTitle());
		reviewContent.setText(review.getContent());
		setTitle(review.getTitle());
		ratingBar.setRating(review.getReviewScore());
	}

	private void setTitle(String title){
		if (getSupportActionBar() != null) {
			getSupportActionBar().setTitle(title);
		}
	}
}