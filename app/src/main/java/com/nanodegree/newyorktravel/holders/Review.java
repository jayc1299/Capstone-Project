package com.nanodegree.newyorktravel.holders;

import android.os.Parcel;
import android.os.Parcelable;

public class Review implements Parcelable {

	private String reviewId;
	private String title;
	private String content;
	private float reviewScore;
	private String attractionId;

	public Review() {
	}

	public String getReviewId() {
		return reviewId;
	}

	public void setReviewId(String reviewId) {
		this.reviewId = reviewId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public float getReviewScore() {
		return reviewScore;
	}

	public void setReviewScore(float reviewScore) {
		this.reviewScore = reviewScore;
	}

	public String getAttractionId() {
		return attractionId;
	}

	public void setAttractionId(String attractionId) {
		this.attractionId = attractionId;
	}

	protected Review(Parcel in) {
		reviewId = in.readString();
		title = in.readString();
		content = in.readString();
		reviewScore = in.readFloat();
		attractionId = in.readString();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(reviewId);
		dest.writeString(title);
		dest.writeString(content);
		dest.writeFloat(reviewScore);
		dest.writeString(attractionId);
	}

	@SuppressWarnings("unused")
	public static final Parcelable.Creator<Review> CREATOR = new Parcelable.Creator<Review>() {
		@Override
		public Review createFromParcel(Parcel in) {
			return new Review(in);
		}

		@Override
		public Review[] newArray(int size) {
			return new Review[size];
		}
	};
}