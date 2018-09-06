package com.nanodegree.newyorktravel.holders;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class FavouriteAttraction extends Attraction {

    private String attractionId; //Note that favouriteAttraction will have it's own dbKey. This is the ATTRACTION ID.
    private String userId;
    private boolean isFavourite;

    public FavouriteAttraction() {}

    public FavouriteAttraction(Attraction attraction) {
        setId(attraction.getId());
        setName(attraction.getName());
        setDescription(attraction.getDescription());
        setImageUrl(attraction.getImageUrl());
        setLatitude(attraction.getLatitude());
        setLongitude(attraction.getLongitude());
    }

    public String getAttractionId() {
        return attractionId;
    }

    public void setAttractionId(String attractionId) {
        this.attractionId = attractionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean getIsFavourite() {
        return isFavourite;
    }

    public void setIsFavourite(boolean favourite) {
        isFavourite = favourite;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", getName());
        result.put("description", getDescription());
        result.put("imageUrl", getImageUrl());
        result.put("latitude", getLatitude());
        result.put("longitude", getLongitude());
        result.put("attractionId", attractionId);
        result.put("userId", userId);
        result.put("isFavourite", isFavourite);

        return result;
    }
}