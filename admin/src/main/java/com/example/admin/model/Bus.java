package com.example.admin.model;

import java.util.ArrayList;

/**
 * Created by Vaibhav on 3/14/2018.
 */

public class Bus {
    private String busNumber;
    private String driverName;
    private ArrayList<PlaceInfo> Bus_Stop;
    private String status;

    public void setStatus(String status) {
        this.status = status;
    }

    public void setBusNumber(String busNumber) {
        this.busNumber = busNumber;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public void setBus_Stop(ArrayList<PlaceInfo> Bus_Stop) {
        this.Bus_Stop = Bus_Stop;
    }

    public Bus() {
        this.busNumber = busNumber;
        this.driverName = driverName;
        this.Bus_Stop = Bus_Stop;
        this.status = status;

    }

    public Bus(String busNumber, String driverName, ArrayList<PlaceInfo> Bus_Stop,String status)
    {

    }

    public String getBusNumber() {
        return busNumber;
    }



    public String getDriverName() {
        return driverName;
    }


    public ArrayList<PlaceInfo> getBus_Stop() {
        return Bus_Stop;
    }


    public String getStatus() {
        return status;
    }


}
