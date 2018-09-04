package com.nanodegree.newyorktravel.holders;

public class Review {

	private String reviewTitle;
	private String reviewContent;
	private float reviewScore;

	public Review() {
	}

	public String getReviewTitle() {
		return reviewTitle;
	}

	public void setReviewTitle(String reviewTitle) {
		this.reviewTitle = reviewTitle;
	}

	public String getReviewContent() {
		return reviewContent;
	}

	public void setReviewContent(String reviewContent) {
		this.reviewContent = reviewContent;
	}

	public float getReviewScore() {
		return reviewScore;
	}

	public void setReviewScore(float reviewScore) {
		this.reviewScore = reviewScore;
	}
}