package com.nanodegree.newyorktravel.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.nanodegree.newyorktravel.R;
import com.nanodegree.newyorktravel.holders.Review;

import java.util.ArrayList;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.AttractionViewHolder> {

	public interface ReviewsListener{
		void onReviewClicked(Review review);
	}

	private ArrayList<Review> reviews;
	private ReviewsListener reviewsListener;

	public ReviewsAdapter(ReviewsListener listener, ArrayList<Review> reviews) {
		this.reviews = reviews;
		this.reviewsListener = listener;
	}

	@NonNull
	@Override
	public AttractionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		return new AttractionViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review, parent, false));
	}

	@Override
	public void onBindViewHolder(@NonNull AttractionViewHolder holder, int position) {
		final Review review = reviews.get(position);

		holder.itemTitle.setText(review.getTitle());
		holder.itemContent.setText(review.getContent());
		holder.stars.setRating(review.getReviewScore());

		holder.itemView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (reviewsListener != null) {
					reviewsListener.onReviewClicked(review);
				}
			}
		});
	}

	@Override
	public int getItemCount() {
		return reviews.size();
	}

	class AttractionViewHolder extends RecyclerView.ViewHolder {

		TextView itemTitle;
		TextView itemContent;
		RatingBar stars;

		public AttractionViewHolder(View itemView) {
			super(itemView);
			itemTitle = itemView.findViewById(R.id.item_review_title);
			itemContent = itemView.findViewById(R.id.item_review_content);
			stars = itemView.findViewById(R.id.item_review_stars);
		}
	}
}