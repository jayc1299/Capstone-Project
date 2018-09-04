package com.nanodegree.newyorktravel.holders;

public class Attraction {

    private String attractionName;
    private String attractionDesc;
    private String attractionImg;

    public Attraction() {
    }

    public Attraction(String attractionName) {
        this.attractionName = attractionName;
    }

    public String getAttractionName() {
        return attractionName;
    }

    public void setAttractionName(String attractionName) {
        this.attractionName = attractionName;
    }

    public String getAttractionDesc() {
        return attractionDesc;
    }

    public void setAttractionDesc(String attractionDesc) {
        this.attractionDesc = attractionDesc;
    }

    public String getAttractionImg() {
        return attractionImg;
    }

    public void setAttractionImg(String attractionImg) {
        this.attractionImg = attractionImg;
    }
}