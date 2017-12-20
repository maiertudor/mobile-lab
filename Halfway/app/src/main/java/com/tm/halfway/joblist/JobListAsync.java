package com.tm.halfway.joblist;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Last edit by tudor.maier on 30/11/2017.
 */

public class JobListAsync extends AsyncTask<Object, Object, String> {

    final HttpClient httpClient = new DefaultHttpClient();
    HttpResponse response;
    private String URL = "https://halfway-staging-api.herokuapp.com/api/v1/jobs";

    @Override
    protected String doInBackground(Object... strings) {
        Log.d("JobListAsync", String.valueOf(strings[0]));
        String token = String.valueOf(strings[0]);

        JSONObject json = new JSONObject();
        StringBuilder result = null;
        try {
            HttpGet request = new HttpGet(URL);
            Header authHeader = new BasicHeader("Authorization", token);
            request.setHeader(authHeader);

            response = httpClient.execute(request);

                    /*Checking response */
            if (response != null && response.getStatusLine().getStatusCode() == 200) {
//                Log.d("JobListAsync", response.getEntity().getContent());
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(response.getEntity().getContent()));

                result = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    result.append(line);
                }
                Log.d("JobListAsync", result.toString());

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return String.valueOf(result);
    }
}

