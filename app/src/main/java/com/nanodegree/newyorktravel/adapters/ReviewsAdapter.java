package com.nanodegree.newyorktravel.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nanodegree.newyorktravel.R;
import com.nanodegree.newyorktravel.holders.Review;

import java.util.ArrayList;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.AttractionViewHolder> {

	private ArrayList<Review> reviews;

	public ReviewsAdapter(ArrayList<Review> reviews) {
		this.reviews = reviews;
	}

	@NonNull
	@Override
	public AttractionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		return new AttractionViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review, parent, false));
	}

	@Override
	public void onBindViewHolder(@NonNull AttractionViewHolder holder, int position) {
		Review review = reviews.get(position);

		holder.itemTitle.setText(review.getReviewTitle());
		holder.itemContent.setText(review.getReviewContent());
	}

	@Override
	public int getItemCount() {
		return reviews.size();
	}

	class AttractionViewHolder extends RecyclerView.ViewHolder {

		TextView itemTitle;
		TextView itemContent;

		public AttractionViewHolder(View itemView) {
			super(itemView);
			itemTitle = itemView.findViewById(R.id.item_review_title);
			itemContent = itemView.findViewById(R.id.item_review_content);
		}
	}
}