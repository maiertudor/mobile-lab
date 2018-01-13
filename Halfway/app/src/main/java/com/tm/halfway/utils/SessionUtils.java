package com.tm.halfway.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.tm.halfway.HalfwayApplication;

/**
 * Created by filipmarusca on 6/19/17.
 */

public class SessionUtils {

    private static final String SESSION_PREFERENCES = "first_agenda_session_preferences";
    private static final String TOKEN = "TOKEN";
    private static final String USERNAME = "USERNAME";
    private static final String USER_TYPE = "USER_TYPE";

    private static SharedPreferences getSessionPreferences() {
        return HalfwayApplication.getInstance().getApplicationContext().getSharedPreferences(SESSION_PREFERENCES, Context.MODE_PRIVATE);
    }

    public static String getToken() {
        return getSessionPreferences().getString(TOKEN, "");
    }

    public static void setToken(String token) {
        getSessionPreferences().edit().putString(TOKEN, token).apply();
    }

    public static void clearSession() {
        getSessionPreferences()
                .edit()
                .clear()
                .apply();
    }

    public static void setUserName(String userName) {
        getSessionPreferences().edit().putString(USERNAME, userName).apply();
    }

    public static String getUsername() {
        return getSessionPreferences().getString(USERNAME, "");
    }

    public static void setUserType(int admin) {
        getSessionPreferences().edit().putInt(USER_TYPE, admin).apply();
    }
}
