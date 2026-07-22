package com.accountmanagement.mapper;

import org.springframework.stereotype.Component;

import com.accountmanagement.model.Location;
import com.accountmanagement.request.LocationRequest;

@Component
public class LocationMapper {

    public Location addLocation(LocationRequest locationRequest) {
        Location location = new Location();
        location.setLocation(locationRequest.getLocation());
        location.setDescription(locationRequest.getDescription());
        return location;
    }

    public Location updateLocation(Location location, LocationRequest locationRequest) {
        location.setLocation(locationRequest.getLocation());
        location.setDescription(locationRequest.getDescription());
        return location;
    }
}
