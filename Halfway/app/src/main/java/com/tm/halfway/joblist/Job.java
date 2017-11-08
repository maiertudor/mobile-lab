package com.tm.halfway.joblist;

import java.util.Date;

/**
 * Last edit by tudor.maier on 29/10/2017.
 */

public class Job {
    private String name;
    private String description;
    private Date expirationDate;
    private String employer;

    public Job(String name, String description, Date expirationDate, String employer) {
        this.name = name;
        this.description = description;
        this.expirationDate = expirationDate;
        this.employer = employer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getEmployer() {
        return employer;
    }

    public void setEmployer(String employer) {
        this.employer = employer;
    }

    @Override
    public String toString() {
        return "Job{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", expirationDate=" + expirationDate +
                ", employer='" + employer + '\'' +
                '}';
    }
}
