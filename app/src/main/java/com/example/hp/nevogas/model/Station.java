package com.example.hp.nevogas.model;

/**
 * Created by HP on 3/19/2019.
 */

public class Station {
    private int id;

    private String user_id;

    private String business_name;

    private String email;

    private String phone;

    private String state;

    private String address;

    private double latitude;

    private double longitude;

    private String about_us;

    private double distance;

    public Station(int id, String user_id, String business_name, String email, String phone, String state, String address, double latitude, double longitude, String about_us, double distance) {
        this.id = id;
        this.user_id = user_id;
        this.business_name = business_name;
        this.email = email;
        this.phone = phone;
        this.state = state;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.about_us = about_us;
        this.distance = distance;
    }

    public int getId() {
        return id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getBusiness_name() {
        return business_name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getState() {
        return state;
    }

    public String getAddress() {
        return address;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getAbout_us() {
        return about_us;
    }

    public double getDistance() {
        return distance;
    }
}
