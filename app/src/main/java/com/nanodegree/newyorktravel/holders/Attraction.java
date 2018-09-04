package com.nanodegree.newyorktravel.holders;

public class Attraction {

    public Attraction() {
    }

    public Attraction(String attractionName) {
        this.attractionName = attractionName;
    }

    private String attractionName;

    public String getAttractionName() {
        return attractionName;
    }

    public void setAttractionName(String attractionName) {
        this.attractionName = attractionName;
    }
}