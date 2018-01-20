package com.tm.halfway.api;

import com.tm.halfway.model.ApplicationUserResponse;
import com.tm.halfway.model.BaseResponse;
import com.tm.halfway.model.GetJobResponse;
import com.tm.halfway.model.Job;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

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
    @GET("v1/applications/user")
    Call<ApplicationUserResponse> getApplicationsForUser();
    @POST("v1//apply/job/{jobId}")
    Call<String> applyToJob(@Path("jobId") String jobId);
}
