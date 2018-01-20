
package com.tm.halfway.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetLocationsResponse extends BaseResponse {

    @SerializedName("locations")
    private List<Location> Locations;

    public List<Location> getLocations() {
        return Locations;
    }

    public void setLocations(List<Location> locations) {
        Locations = locations;
    }

}
