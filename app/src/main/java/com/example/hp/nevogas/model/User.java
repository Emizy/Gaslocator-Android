package com.example.hp.nevogas.model;

/**
 * Created by HP on 3/15/2019.
 */

public class User {

    private int id;
    private String token;
    private String name;
    private String email;
    private String type;
    private String state;
    private String address;
    private String latitude;
    private String longitude;
    private String phone;
    private String user_status;


    public User(int id, String token, String name, String email, String type, String state, String address, String latitude, String longitude, String phone, String user_status) {
        this.id = id;
        this.token = token;
        this.name = name;
        this.email = email;
        this.type = type;
        this.state = state;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.phone = phone;
        this.user_status = user_status;
    }

    public int getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }


    public String getType() {
        return type;
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


    public String getPhone() {
        return phone;
    }

    public String getUser_status() {
        return user_status;
    }
}
