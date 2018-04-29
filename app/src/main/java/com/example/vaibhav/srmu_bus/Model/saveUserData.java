package com.example.vaibhav.srmu_bus.Model;

/**
 * Created by Vaibhav on 2/17/2018.
 */

public class saveUserData {

    public String displayName;
    public String rollNo;
    public String phoneNo;
    public String stuemail;

    public saveUserData()
    {

    }

    public String getDisplayName() {
        return displayName;
    }

    public String getRollNo() {
        return rollNo;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public String getStuemail() {
        return stuemail;
    }

    public saveUserData(String displayName, String rollNo, String phoneNo, String stuemail) {
        this.displayName = displayName;
        this.rollNo = rollNo;
        this.phoneNo = phoneNo;
        this.stuemail = stuemail;

    }
}
