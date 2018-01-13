package com.tm.halfway.api;

import com.tm.halfway.model.GetJobResponse;
import com.tm.halfway.model.Job;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by FilipMarusca on 03/08/16.
 */

public interface IHalfwayAPI {

    @GET("v1/jobs")
    Call<GetJobResponse> getJobs();
    @GET("v1/jobs/created")
    Call<GetJobResponse> getJobsCreated();
    @GET("v1/jobs/updated")
    Call<GetJobResponse> getJobsUpdated();
    @GET("v1/jobs/ended")
    Call<GetJobResponse> getJobsEnded();
    @GET("v1/jobs/cost/asc")
    Call<GetJobResponse> getJobsCostAscending();
    @GET("v1/jobs/cost/desc")
    Call<GetJobResponse> getJobsCostDescending();
}
