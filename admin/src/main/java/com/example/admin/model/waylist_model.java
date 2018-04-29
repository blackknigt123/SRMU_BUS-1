package com.example.admin.model;

import android.os.Parcel;
import android.os.Parcelable;

public class waylist_model implements Parcelable {
    public String bus_stop;
    public String stop_no;
    public String busNumber;

    public waylist_model(String bus_stop, String stop_no, String busNumber) {
        this.bus_stop = bus_stop;
        this.stop_no = stop_no;
        this.busNumber=busNumber;
    }

    public void setBus_stop(String bus_stop) {
        this.bus_stop = bus_stop;
    }

    public void setStop_no(String stop_no) {
        this.stop_no = stop_no;
    }
    public void setBusNumber(String busNumber){
        this.busNumber=busNumber;
    }

    public String getBus_stop() {

        return bus_stop;
    }

    public String getStop_no() {
        return stop_no;
    }
    public String getBusNumber()
    {
        return busNumber;
    }

    public static Creator<waylist_model> getCREATOR() {
        return CREATOR;
    }

    protected waylist_model(Parcel in) {
        bus_stop = in.readString();
        stop_no = String.valueOf(in.readInt());
        busNumber=String.valueOf(in.readInt());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(bus_stop);
        dest.writeInt(Integer.parseInt(stop_no));
        dest.writeInt(Integer.parseInt(busNumber));
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
