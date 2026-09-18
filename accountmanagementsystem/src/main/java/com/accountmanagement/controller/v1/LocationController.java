package com.accountmanagement.controller.v1;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.constants.message.LocationMessage;
import com.accountmanagement.model.Location;
import com.accountmanagement.request.LocationRequest;
import com.accountmanagement.response.ApiResponse;
import com.accountmanagement.service.LocationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/v1/location")
@Tag(name = "LocationController")
public class LocationController {

    private final LocationService locationService;

    LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createLocation(@Valid @RequestBody LocationRequest locationRequest) {
        locationRequest.sanitizeInput();
        locationService.addLocation(locationRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, LocationMessage.ADD_LOCATION, 201);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateLocation(@PathVariable Integer id,
            @Valid @RequestBody LocationRequest locationRequest) {
        locationRequest.sanitizeInput();
        locationService.updateLocation(id, locationRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, LocationMessage.UPDATE_LOCATION, 200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteLocationById(@PathVariable Integer id) {
        locationService.deleteLocation(id);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, LocationMessage.DELETE_LOCATION, 200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse> getAllLocations() {
        List<Location> location = locationService.getAllLocations();
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, LocationMessage.LOCATIONS, 200);
        response.setData(location);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
