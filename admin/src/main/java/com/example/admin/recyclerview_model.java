package com.example.admin;

import android.os.Parcel;
import android.os.Parcelable;

public class recyclerview_model implements Parcelable {

    public String bus_no,startpoint_details,current_details,nextstop_details;
    public int nextstoptime_detail;


    public recyclerview_model(String bus_no, String startpoint_details, String current_details, String nextstop_details, int nextstoptime_detail) {
        this.bus_no = bus_no;
        this.startpoint_details = startpoint_details;
        this.current_details = current_details;
        this.nextstop_details = nextstop_details;
        this.nextstoptime_detail = nextstoptime_detail;
    }

    protected recyclerview_model(Parcel in) {
        bus_no = in.readString();
        startpoint_details = in.readString();
        current_details = in.readString();
        nextstop_details = in.readString();
        nextstoptime_detail = in.readInt();
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

    public String getBus_no() {
        return bus_no;
    }

    public String getStartpoint_details() {
        return startpoint_details;
    }

    public String getCurrent_details() {
        return current_details;
    }

    public String getNextstop_details() {
        return nextstop_details;
    }

    public int getNextstoptime_detail() {
        return nextstoptime_detail;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(bus_no);
        parcel.writeString(startpoint_details);
        parcel.writeString(current_details);
        parcel.writeString(nextstop_details);
        parcel.writeInt(nextstoptime_detail);
    }
}
