
package com.tm.halfway.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Provider {

    @SerializedName("abilities")
    private List<Object> Abilities;
    @SerializedName("email")
    private String Email;
    @SerializedName("firstName")
    private String FirstName;
    @SerializedName("id")
    private String Id;
    @SerializedName("lastName")
    private String LastName;

    public List<Object> getAbilities() {
        return Abilities;
    }

    public void setAbilities(List<Object> abilities) {
        Abilities = abilities;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

}
