package com.nanodegree.newyorktravel.holders;

import android.os.Parcel;
import android.os.Parcelable;

public class Attraction implements Parcelable {

    private String id;
    private String name;
    private String description;
    private String imageUrl;
    private float latitude;
    private float longitude;

    public Attraction() {
    }

    public Attraction(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    protected Attraction(Parcel in) {
        id = in.readString();
        name = in.readString();
        description = in.readString();
        imageUrl = in.readString();
        latitude = in.readFloat();
        longitude = in.readFloat();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(imageUrl);
        dest.writeFloat(latitude);
        dest.writeFloat(longitude);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Attraction> CREATOR = new Parcelable.Creator<Attraction>() {
        @Override
        public Attraction createFromParcel(Parcel in) {
            return new Attraction(in);
        }

        @Override
        public Attraction[] newArray(int size) {
            return new Attraction[size];
        }
    };
}