package com.accountmanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accountmanagement.exceptions.RecordNotFoundException;
import com.accountmanagement.model.Location;
import com.accountmanagement.repository.LocationRepository;
import com.accountmanagement.request.LocationRequest;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    public Location addLocation(LocationRequest locationRequest) {
        System.out.println("Area" + locationRequest.getArea());
        Location location = new Location();
        location.setArea(locationRequest.getArea());
        location.setDescription(locationRequest.getDescription());
        return locationRepository.save(location);
    }

    public Location updateLocation(String id, LocationRequest locationRequest) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Location id not found"));
        location.setArea(locationRequest.getArea());
        location.setDescription(locationRequest.getDescription());
        return locationRepository.save(location);
    }

    public void deleteLocation(String id) {
        locationRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Location id not found"));
        locationRepository.deleteById(id);
    }

    public List<Location> getAllLocations() {
        return locationRepository.findAll();

    }

}
