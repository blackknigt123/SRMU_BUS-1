package com.example.vaibhav.srmu_bus;

public class saveFeedback {

    public String fedback;
    public String name;
    public String email;

    public String getFedback() {

        return fedback;
    }

    public void setFedback(String fedback) {
        this.fedback = fedback;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public saveFeedback(String fedback, String nm, String em)
    {

        this.email=em;
        this.name=nm;
        this.fedback=fedback;

    }


}
