package com.example.vaibhav.srmu_bus;

import android.os.Parcel;
import android.os.Parcelable;

public class waylist_model implements Parcelable {
    public String bus_stop;
    public int stop_no;

    public waylist_model(String bus_stop, int stop_no) {
        this.bus_stop = bus_stop;
        this.stop_no = stop_no;
    }

    public void setBus_stop(String bus_stop) {
        this.bus_stop = bus_stop;
    }

    public void setStop_no(int stop_no) {
        this.stop_no = stop_no;
    }

    public String getBus_stop() {

        return bus_stop;
    }

    public int getStop_no() {
        return stop_no;
    }

    public static Creator<waylist_model> getCREATOR() {
        return CREATOR;
    }

    protected waylist_model(Parcel in) {
        bus_stop = in.readString();
        stop_no = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(bus_stop);
        dest.writeInt(stop_no);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<waylist_model> CREATOR = new Creator<waylist_model>() {
        @Override
        public waylist_model createFromParcel(Parcel in) {
            return new waylist_model(in);
        }

        @Override
        public waylist_model[] newArray(int size) {
            return new waylist_model[size];
        }
    };
}
