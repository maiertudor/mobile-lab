package com.tm.halfway.utils;

import android.widget.Toast;

import com.tm.halfway.HalfwayApplication;

/**
 * Created by vladnegrea on 13/01/2018.
 */

public class NotificationUtils {
    public static void showMessage(String message) {
        Toast.makeText(HalfwayApplication.getInstance(), message, Toast.LENGTH_SHORT).show();
    }
}
