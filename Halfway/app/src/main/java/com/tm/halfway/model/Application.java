
package com.tm.halfway.model;

import com.google.gson.annotations.SerializedName;

public class Application {

    @SerializedName("createdAt")
    private String CreatedAt;
    @SerializedName("id")
    private String Id;
    @SerializedName("job")
    private Job Job;
    @SerializedName("provider")
    private Provider Provider;

    public String getCreatedAt() {
        return CreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        CreatedAt = createdAt;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public Job getJob() {
        return Job;
    }

    public void setJob(Job job) {
        Job = job;
    }

    public Provider getProvider() {
        return Provider;
    }

    public void setProvider(Provider provider) {
        Provider = provider;
    }

    @Override
    public String toString() {
        return "Application{" +
                "CreatedAt='" + CreatedAt + '\'' +
                ", Id='" + Id + '\'' +
                ", Job=" + Job +
                ", Provider=" + Provider +
                '}';
    }
}
