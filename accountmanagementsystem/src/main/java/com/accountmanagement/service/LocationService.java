package com.accountmanagement.service;

import java.util.List;
import org.springframework.stereotype.Service;

import com.accountmanagement.constants.message.LocationMessage;
import com.accountmanagement.exceptions.DuplicateRecordException;
import com.accountmanagement.exceptions.RecordNotFoundException;
import com.accountmanagement.mapper.LocationMapper;
import com.accountmanagement.model.Location;
import com.accountmanagement.model.User;
import com.accountmanagement.repository.LocationRepository;
import com.accountmanagement.request.LocationRequest;
import com.accountmanagement.utility.Apputility;

@Service
public class LocationService {

    private final UserLoginAuditLogService userLoginAuditLogService;

    private final LocationRepository locationRepository;

    private final LocationMapper locationMapper;

    LocationService(LocationRepository locationRepository, LocationMapper locationMapper,
            UserLoginAuditLogService userLoginAuditLogService) {
        this.locationRepository = locationRepository;
        this.locationMapper = locationMapper;
        this.userLoginAuditLogService = userLoginAuditLogService;
    }

    public Location addLocation(LocationRequest locationRequest) {
        validateLocation(locationRequest.getLocation());
        User user = getLoggedUser();
        Location location = locationMapper.addLocation(locationRequest);
        Location savedLocation = locationRepository.save(location);
        userLoginAuditLogService.createUserLog(user.getOrganizationId(), user.getId(), "Location Added", "Success");
        return savedLocation;
    }

    public Location updateLocation(Integer id, LocationRequest locationRequest) {
        User user = getLoggedUser();
        Location location = findById(id);
        Location updatedLocation = locationMapper.updateLocation(location, locationRequest);
        Location savedUpdatedLocation = locationRepository.save(updatedLocation);
        userLoginAuditLogService.createUserLog(user.getOrganizationId(), user.getId(), "Update Location", "Success");
        return savedUpdatedLocation;
    }

    public void deleteLocation(Integer id) {
        User user = getLoggedUser();
        findById(id);
        locationRepository.deleteById(id);
        userLoginAuditLogService.createUserLog(user.getOrganizationId(), user.getId(), "Delete Location", "Success");
    }

    public List<Location> getAllLocations() {
        User user = getLoggedUser();
        List<Location> location = locationRepository.findAll();
        if (location.isEmpty() || location == null) {
            throw new RecordNotFoundException(LocationMessage.LOCATIONS_NOT_FOUND);
        }
        userLoginAuditLogService.createUserLog(user.getOrganizationId(), user.getId(), "Get All Location", "Success");
        return location;
    }

    public Location findById(Integer id) {
        return locationRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(LocationMessage.LOCATIONS_ID_NOT_FOUND));
    }

    private User getLoggedUser() {
        return Apputility.getLoggedUser();
    }

    private void validateLocation(String location) {
        if (locationRepository
                .existsByLocationIgnoreCase(location.trim())) {
            throw new DuplicateRecordException(
                    "location already exists");
        }
    }

}
