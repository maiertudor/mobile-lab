package com.tm.halfway.utils;

import com.tm.halfway.model.GetJobResponse;
import com.tm.halfway.model.Job;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by vladnegrea on 13/01/2018.
 */

public class ConvertionUtils {
    public static void setDataToJobsResponse(GetJobResponse body) {
        for (Job job : body.getJobs()) {
            job.setCreated_at(getDateFromString(job.getCreatedAtString()));
            job.setUpdated_at(getDateFromString(job.getUpdatedAtString()));
            job.setEnds_at(getDateFromString(job.getEndsAtString()));
        }
    }

    private static Date getDateFromString(String dateString) {
        Date date = null;

        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
            date = df.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    public static String getStringForDate(Date date) {
        String dateString = null;

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        dateString = df.format(date);

        return dateString;
    }
}
