package com.example.hp.nevogas.model;

/**
 * Created by HP on 3/15/2019.
 */

public class LoginResponse {
    private boolean success;
    private String message;
    private User data;

    public LoginResponse(boolean success, User data, String message) {
        this.success = success;
        this.data = data;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public User getData() {
        return data;
    }
}
