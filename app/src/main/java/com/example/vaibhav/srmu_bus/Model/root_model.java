package com.example.vaibhav.srmu_bus.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class root_model implements Parcelable {

    public String bus_stop;
    public String stop_no;

    public void setBus_stop(String bus_stop) {
        this.bus_stop = bus_stop;
    }

    public void setStop_no(String stop_no) {
        this.stop_no = stop_no;
    }

    public String getBus_stop() {
        return bus_stop;
    }

    public String getStop_no() {
        return stop_no;
    }

    public static Creator<root_model> getCREATOR() {
        return CREATOR;
    }

    public root_model(String bus_stop, String stop_no) {
        this.bus_stop = bus_stop;

        this.stop_no = stop_no;
    }

    public root_model(Parcel in) {
    }

    public static final Creator<root_model> CREATOR = new Creator<root_model>() {
        @Override
        public root_model createFromParcel(Parcel in) {
            return new root_model(in);
        }

        @Override
        public root_model[] newArray(int size) {
            return new root_model[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }
}
