
package com.tm.halfway.model;

import com.google.gson.annotations.SerializedName;

public class Location {

    @SerializedName("name")
    private String Name;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

}
