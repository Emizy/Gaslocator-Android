package com.example.hp.nevogas.model;

/**
 * Created by HP on 3/21/2019.
 */

public class Locate {

    double user_latitude ,user_longitude;

    public Locate(double user_latitude, double user_longitude) {
        this.user_latitude = user_latitude;
        this.user_longitude = user_longitude;
    }

    public double getUser_latitude() {
        return user_latitude;
    }

    public void setUser_latitude(double user_latitude) {
        this.user_latitude = user_latitude;
    }

    public double getUser_longitude() {
        return user_longitude;
    }

    public void setUser_longitude(double user_longitude) {
        this.user_longitude = user_longitude;
    }
}
