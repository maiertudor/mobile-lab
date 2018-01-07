package com.tm.halfway.notifications;

import android.os.AsyncTask;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.util.HashMap;
import java.util.Map;

/**
 * Last edit by tudor.maier on 07/01/2018.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "FCMTokenService";
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // TODO: Implement this method to send any registration to your app's servers.
        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(final String refreshedToken) {
        String key = mRootRef.child("users").push().getKey();

        new AsyncTask(){

            @Override
            protected void onPostExecute(Object message) {
                super.onPostExecute(message);
            }

            @Override
            protected Object doInBackground(Object[] params) {

                Map<String, Object> role = new HashMap<String, Object>();
                role.put("role", "admin");

                Map<String, Object> childUpdates = new HashMap<>();
                childUpdates.put("/users/" + refreshedToken, role);

                mRootRef.updateChildren(childUpdates);

                return "ok";
            }
        }.execute();
    }
}
