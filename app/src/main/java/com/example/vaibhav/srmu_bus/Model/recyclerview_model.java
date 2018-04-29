package com.example.vaibhav.srmu_bus.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class recyclerview_model implements Parcelable {

    public String bus_no,startpoint_details;
    public int imageView;


    public String getBus_no() {
        return bus_no;
    }

    public String getStartpoint_details() {
        return startpoint_details;
    }

    public int getImageView() {
        return imageView;
    }

    public void setBus_no(String bus_no) {
        this.bus_no = bus_no;
    }

    public void setStartpoint_details(String startpoint_details) {
        this.startpoint_details = startpoint_details;
    }

    public void setImageView(int imageView) {
        this.imageView = imageView;
    }

    public static Creator<recyclerview_model> getCREATOR() {
        return CREATOR;
    }

    protected recyclerview_model(Parcel in) {
        bus_no = in.readString();

        startpoint_details = in.readString();
        imageView = in.readInt();
    }

    public recyclerview_model(String bus_no, String startpoint_details, int imageView) {
        this.bus_no = bus_no;
        this.startpoint_details = startpoint_details;
        this.imageView = imageView;
    }

    public static final Creator<recyclerview_model> CREATOR = new Creator<recyclerview_model>() {
        @Override
        public recyclerview_model createFromParcel(Parcel in) {
            return new recyclerview_model(in);
        }

        @Override
        public recyclerview_model[] newArray(int size) {
            return new recyclerview_model[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(bus_no);
        parcel.writeString(startpoint_details);
        parcel.writeInt(imageView);
    }
}
