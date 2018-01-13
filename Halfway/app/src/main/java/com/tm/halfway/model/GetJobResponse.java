package com.tm.halfway.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by vladnegrea on 13/01/2018.
 */

public class GetJobResponse extends BaseResponse {
    @SerializedName("jobs")
    List<Job> jobs;

    public List<Job> getJobs() {
        return jobs;
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }
}
