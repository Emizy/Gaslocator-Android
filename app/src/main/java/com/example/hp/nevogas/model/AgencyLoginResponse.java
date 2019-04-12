package com.example.hp.nevogas.model;

/**
 * Created by HP on 3/17/2019.
 */

public class AgencyLoginResponse {

    private boolean success;
    private String message;
    private Agency data;

    public AgencyLoginResponse(boolean success, String message, Agency data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public Agency getData() {
        return data;
    }
}
