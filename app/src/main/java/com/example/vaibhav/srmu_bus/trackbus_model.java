package com.example.vaibhav.srmu_bus;

import android.os.Parcel;
import android.os.Parcelable;

public class trackbus_model implements Parcelable {

    public String number;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public static Creator<trackbus_model> getCREATOR() {
        return CREATOR;
    }

    public trackbus_model(String number) {
        this.number = number;
    }

    protected trackbus_model(Parcel in) {
    }

    public static final Creator<trackbus_model> CREATOR = new Creator<trackbus_model>() {
        @Override
        public trackbus_model createFromParcel(Parcel in) {
            return new trackbus_model(in);
        }

        @Override
        public trackbus_model[] newArray(int size) {
            return new trackbus_model[size];
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
