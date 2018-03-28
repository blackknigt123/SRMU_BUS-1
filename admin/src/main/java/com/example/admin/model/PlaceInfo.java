package com.example.admin.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Vaibhav on 3/10/2018.
 */

public class PlaceInfo implements Parcelable{

    private String name;
    private String address;
    private String id;
    private LatLng latLng;

    public PlaceInfo(String name, String address, String id, LatLng ltlng) {
        this.name = name;
        this.address = address;
        this.id = id;
        this.latLng = ltlng;
    }

    public PlaceInfo() {


    }

    protected PlaceInfo(Parcel in) {
        name = in.readString();
        address = in.readString();
        id = in.readString();
        latLng = in.readParcelable(LatLng.class.getClassLoader());
    }

    public static final Creator<PlaceInfo> CREATOR = new Creator<PlaceInfo>() {
        @Override
        public PlaceInfo createFromParcel(Parcel in) {
            return new PlaceInfo(in);
        }

        @Override
        public PlaceInfo[] newArray(int size) {
            return new PlaceInfo[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    @Override
    public String toString() {
        return address;

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(address);
        parcel.writeString(id);
        parcel.writeParcelable(latLng, i);
    }
}

