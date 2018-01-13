package com.tm.halfway.model;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tm.halfway.utils.NotificationUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by FilipMarusca on 03/08/16.
 */

public abstract class ApiCallback<T extends BaseResponse> implements Callback<T> {
    private static final String UNKNOWN_ERROR = "unknown error";
    private static Gson mGsonParser = new GsonBuilder().create();

    private static Handler sUiThread;

    protected static Handler getHandler() {
        if (sUiThread == null) {
            sUiThread = new Handler(Looper.getMainLooper());
        }
        return sUiThread;
    }

    @Override
    public void onResponse(final Call<T> call, final Response<T> response) {
        T result = response.body();

        if (result != null) {
            if (result.isSuccessful() && TextUtils.isEmpty(result.getError())) {
                success(response.body());
            } else {
                if (!TextUtils.isEmpty(result.getError())) {
                    error(result.getError());
                } else {
//                    we are suppressing Unexpected errors
//                    error(UIUtils.getString(R.string.unexpected_error));
                }
            }
        } else {
//            we are suppressing Unexpected errors
//            error(UIUtils.getString(R.string.unexpected_error));
        }

    }

    @Override
    public void onFailure(Call<T> call, final Throwable throwable) {
        getHandler().post(new Runnable() {
            @Override
            public void run() {
                if (throwable != null && "canceled".equalsIgnoreCase(throwable.getMessage()))
                    return;

                failure(new Exception(throwable != null ? throwable.getMessage() : UNKNOWN_ERROR));
            }
        });
    }

    public abstract void success(T response);

    public abstract void failure(Exception exception);

    public void error(String message) {
        NotificationUtils.showMessage(message);
    }

}