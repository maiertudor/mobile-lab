package com.tm.halfway.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Last edit by tudor.maier on 29/10/2017.
 */

public class Job implements Serializable {
    private String id;
    private String title;
    private String description;
    private Date created_at;
    private Date updated_at;
    private Date ends_at;
    private Float cost;
    private String owner;
    private String category;
    private String location;

    public Job(String id, String title, String description, Date created_at, Date updated_at, Date ends_at, Float cost, String owner, String category, String location) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.ends_at = ends_at;
        this.cost = cost;
        this.owner = owner;
        this.category = category;
        this.location = location;
    }

    public Job() {
        Date date = new Date();
        this.created_at = date;
        this.updated_at = date;
        this.cost = 10F;
        this.category = "Relatii publice";
        this.location = "Cluj-Napoca";
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public Date getEnds_at() {
        return ends_at;
    }

    public void setEnds_at(Date ends_at) {
        this.ends_at = ends_at;
    }

    public Float getCost() {
        return cost;
    }

    public void setCost(Float cost) {
        this.cost = cost;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Job{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                ", ends_at=" + ends_at +
                ", cost=" + cost +
                ", owner='" + owner + '\'' +
                ", category='" + category + '\'' +
                ", location='" + location + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
