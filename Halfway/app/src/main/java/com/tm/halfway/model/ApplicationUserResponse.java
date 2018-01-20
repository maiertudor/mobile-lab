
package com.tm.halfway.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@SuppressWarnings("unused")
public class ApplicationUserResponse extends BaseResponse {

    @SerializedName("applications")
    private List<Application> Applications;

    public List<Application> getApplications() {
        return Applications;
    }

    public void setApplications(List<Application> applications) {
        Applications = applications;
    }

    @Override
    public String toString() {
        return "ApplicationUserResponse{" +
                "Applications=" + Applications +
                '}';
    }
}
