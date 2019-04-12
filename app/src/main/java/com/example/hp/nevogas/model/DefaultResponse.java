package com.example.hp.nevogas.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by HP on 3/14/2019.
 */

public class DefaultResponse {
    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String msg;

    public DefaultResponse(boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMsg() {
        return msg;
    }
}
