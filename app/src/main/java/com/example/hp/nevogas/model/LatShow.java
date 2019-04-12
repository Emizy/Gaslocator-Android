package com.example.hp.nevogas.model;

/**
 * Created by HP on 3/26/2019.
 */

public class LatShow {
    double latitude,longitude;

    public LatShow(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
