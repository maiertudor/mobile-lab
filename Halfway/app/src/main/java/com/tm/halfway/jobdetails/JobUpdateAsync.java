package com.tm.halfway.jobdetails;

import android.os.AsyncTask;
import android.util.Log;

import com.tm.halfway.model.Job;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Last edit by tudor.maier on 30/11/2017.
 */

public class JobUpdateAsync extends AsyncTask<Object, Object, String> {

    final HttpClient httpClient = new DefaultHttpClient();
    HttpResponse response;
    private String URL = "https://halfway-staging-api.herokuapp.com/api/v1/jobs";

    @Override
    protected String doInBackground(Object... strings) {
        Log.d("JobListAsync", String.valueOf(strings[0]));
        String token = String.valueOf(strings[0]);

        Job job = (Job) strings[2];
        JSONObject json = new JSONObject();
        StringBuilder result = null;
        try {
            HttpPut request = new HttpPut(URL + "/" + String.valueOf(strings[1]));
            Header authHeader = new BasicHeader("Authorization", token);
            request.setHeader(authHeader);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", job.getId());
            jsonObject.put("title", job.getTitle());
            jsonObject.put("description", job.getDescription());
            jsonObject.put("ends_at", job.getEnds_at());
            jsonObject.put("cost", job.getCost());
            jsonObject.put("category", job.getCategory());
            jsonObject.put("location", job.getLocation());
            json.put("job", jsonObject);

            StringEntity entity = new StringEntity(json.toString());
            System.out.println(entity.toString());
            request.setEntity(entity);

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
                Log.d("JobUpdateAsync", result.toString());

            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return String.valueOf(result);
    }
}

