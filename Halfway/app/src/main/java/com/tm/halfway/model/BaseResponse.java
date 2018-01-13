package com.tm.halfway.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by FilipMarusca on 04/08/16.
 */

public class BaseResponse {
    @SerializedName("success")
    private boolean success;
    @SerializedName("error")
    private String error;
    @SerializedName("message")
    private String message;

    public boolean isSuccessful() {
        return success;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }
}

