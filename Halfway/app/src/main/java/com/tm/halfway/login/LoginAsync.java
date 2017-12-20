package com.tm.halfway.login;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Last edit by tudor.maier on 28/11/2017.
 */

public class LoginAsync extends AsyncTask<Object, Object, String> {

    final HttpClient httpClient = new DefaultHttpClient();
    HttpResponse response;
    private String URL = "https://halfway-staging-api.herokuapp.com/api/v1/sign-in";

    @Override
    protected String doInBackground(Object... strings) {
        Log.d("LoginAsync", String.valueOf(strings[0]));
        String token = null;

        JSONObject json = new JSONObject();
        try {
            json.put("username", strings[0]);
            json.put("password", strings[1]);
            HttpPost request = new HttpPost(URL);
            StringEntity stringEntity = new StringEntity(json.toString());
            stringEntity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            request.setEntity(stringEntity);

            response = httpClient.execute(request);

                    /*Checking response */
            if (response != null && 200 == response.getStatusLine().getStatusCode()) {
                Header[] headers = response.getAllHeaders();
                for (Header header : headers) {
                    if ("Authorization".equals(header.getName())) {
                        token = header.getValue();
                    }
                }
                Log.d("LoginAsync", token);
            }
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }

        return token;
    }
}
