package com.tm.halfway;

import android.app.Application;

import com.google.firebase.messaging.FirebaseMessaging;
import com.squareup.leakcanary.LeakCanary;

/**
 * Last edit by tudor.maier on 14/11/2017.
 */

public class HalfwayApplication extends Application {
    @Override public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        // Normal app init code...

        FirebaseMessaging.getInstance().subscribeToTopic("jobs");
    }
}
