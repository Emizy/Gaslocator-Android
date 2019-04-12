package com.example.hp.nevogas.model;

/**
 * Created by HP on 3/25/2019.
 */

public class ShowOrderGas {

    private int id;
    private int user_id;
    private int order_id;
    private String qty;
    private int price;
    private String user_name;
    private String phone;
    private String email;
    private String address;
    private double latitude;
    private double longitude;
    private int gas_id;
    private String order_status;
    private String created_at;
    private String upddate_at;
    private String business_name;
    private double agency_latitude;
    private double agency_longitude;
    private String agency_transaction_status;
    private String agency_phone;
    private String user_transaction_status;
    private int agency_id;
    private String agency_address;

    public ShowOrderGas(int id, int user_id, int order_id, String qty, int price, String user_name, String phone, String email, String address, double latitude, double longitude, int gas_id, String order_status, String created_at, String upddate_at, String business_name, double agency_latitude, double agency_longitude, String agency_transaction_status, String agency_phone, String user_transaction_status, int agency_id, String agency_address) {
        this.id = id;
        this.user_id = user_id;
        this.order_id = order_id;
        this.qty = qty;
        this.price = price;
        this.user_name = user_name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.gas_id = gas_id;
        this.order_status = order_status;
        this.created_at = created_at;
        this.upddate_at = upddate_at;
        this.business_name = business_name;
        this.agency_latitude = agency_latitude;
        this.agency_longitude = agency_longitude;
        this.agency_transaction_status = agency_transaction_status;
        this.agency_phone = agency_phone;
        this.user_transaction_status = user_transaction_status;
        this.agency_id = agency_id;
        this.agency_address = agency_address;
    }

    public int getId() {
        return id;
    }

    public int getUser_id() {
        return user_id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public String getQty() {
        return qty;
    }

    public int getPrice() {
        return price;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
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

    public int getGas_id() {
        return gas_id;
    }

    public String getOrder_status() {
        return order_status;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpddate_at() {
        return upddate_at;
    }

    public String getBusiness_name() {
        return business_name;
    }

    public double getAgency_latitude() {
        return agency_latitude;
    }

    public double getAgency_longitude() {
        return agency_longitude;
    }

    public String getAgency_transaction_status() {
        return agency_transaction_status;
    }

    public String getAgency_phone() {
        return agency_phone;
    }

    public String getUser_transaction_status() {
        return user_transaction_status;
    }

    public int getAgency_id() {
        return agency_id;
    }

    public String getAgency_address() {
        return agency_address;
    }
}
