package com.example.hp.nevogas.model;

/**
 * Created by HP on 3/17/2019.
 */

public class Agency {
    private String token;
    private int id;
    private String business_name;
    private String email;
    private String phone;
    private String state;
    private String address;
    private String latitude;
    private String longitude;
    private int user_id;
    private String about_us;
    private String account_status;

    public Agency(String token, int id, String business_name, String email, String phone, String state, String address, String latitude, String longitude, int user_id,String about_us, String account_status) {
        this.token = token;
        this.id = id;
        this.business_name = business_name;
        this.email = email;
        this.phone = phone;
        this.state = state;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.user_id = user_id;
        this.about_us = about_us;
        this.account_status = account_status;
    }

    public String getToken() {
        return token;
    }

    public int getId() {
        return id;
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

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getAbout_us() {
        return about_us;
    }

    public String getAccount_status() {
        return account_status;
    }
}
