package com.tm.halfway.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by FilipMarusca on 04/08/16.
 */

public class BaseResponse<T> {
    @SerializedName("data")
    private T data;
    @SerializedName("success")
    private boolean success;
    @SerializedName("error")
    private String error;
    @SerializedName("message")
    private String message;

    public T getData() {
        return data;
    }

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

