package com.tm.halfway.jobdetails;

import android.os.AsyncTask;
import android.util.Log;

import com.tm.halfway.model.Job;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Last edit by tudor.maier on 16/12/2017.
 */

public class JobAddAsync extends AsyncTask<Object, Object, String> {

    final HttpClient httpClient = new DefaultHttpClient();
    HttpResponse response;
    private String URL = "https://halfway-staging-api.herokuapp.com/api/v1/jobs";

    @Override
    protected String doInBackground(Object... strings) {
        Log.d("JobAddAsync", String.valueOf(strings[0]));
        String token = String.valueOf(strings[0]);

        StringBuilder result = new StringBuilder();

        Job job = (Job) strings[1];
        JSONObject json = new JSONObject();
        try {
            HttpPost request = new HttpPost(URL);
            Header authHeader = new BasicHeader("Authorization", token);
            request.setHeader(authHeader);
            request.setHeader(HTTP.CONTENT_TYPE, "application/json");

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("title", job.getTitle());
            jsonObject.put("description", job.getDescription());
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
            Date date = new Date();
            jsonObject.put("createdAt", df.format(date));
            jsonObject.put("updatedAt", df.format(date));
            jsonObject.put("endsAt", df.format(job.getEnds_at()));
            jsonObject.put("cost", job.getCost());
            jsonObject.put("owner", job.getOwner());
            jsonObject.put("category", job.getCategory());
            jsonObject.put("location", job.getLocation());
            json.put("job", jsonObject);

            StringEntity entity = new StringEntity(json.toString());
            System.out.println(entity.toString());
            request.setEntity(entity);

            response = httpClient.execute(request);

            /*Checking response */
            if (response != null && response.getStatusLine().getStatusCode() == HttpStatus.SC_CREATED) {
//                Log.d("JobListAsync", response.getEntity().getContent());
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(response.getEntity().getContent()));

                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    result.append(line);
                }
                Log.d("JobAddAsync", result.toString());
            }
        } catch (IOException | JSONException e) {
            result.append("noconnection");
//            e.printStackTrace();
        }

        return String.valueOf(result);
    }
}

