package com.example.fabia.doppelkopfnew;

import android.app.Activity;
import android.content.Context;
import android.location.LocationManager;
import android.os.Parcel;
import android.os.Parcelable;

public class Location implements Parcelable{

    String latitude;
    String longitude;
    String address;

    public Location(String latitude, String longitude, String address) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
    }

    protected Location(Parcel in) {
        latitude = in.readString();
        longitude = in.readString();
        address = in.readString();
    }

    public static final Creator<Location> CREATOR = new Creator<Location>() {
        @Override
        public Location createFromParcel(Parcel in) {
            return new Location(in);
        }

        @Override
        public Location[] newArray(int size) {
            return new Location[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(latitude);
        parcel.writeString(longitude);
        parcel.writeString(address);
    }
}
