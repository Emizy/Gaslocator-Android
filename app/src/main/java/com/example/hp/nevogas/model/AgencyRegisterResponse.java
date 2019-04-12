package com.example.hp.nevogas.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by HP on 3/17/2019.
 */

public class AgencyRegisterResponse {
    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String msg;

    public AgencyRegisterResponse(boolean success, String msg) {
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
