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
import com.accountmanagement.model.MasterCity;
import com.accountmanagement.model.MasterCountry;
import com.accountmanagement.model.MasterState;
import com.accountmanagement.request.LocationRequest;
import com.accountmanagement.response.ApiResponse;
import com.accountmanagement.service.LocationService;
import com.accountmanagement.service.MasterCityService;
import com.accountmanagement.service.MasterCountryService;
import com.accountmanagement.service.MasterStateService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/v1")
@Tag(name = "MasterController")
@RequiredArgsConstructor
public class MasterController {

    private final LocationService locationService;

    private final MasterCountryService masterCountryService;

    private final MasterStateService masterStateService;

    private final MasterCityService masterCityService;

    @PostMapping("/location/create")
    public ResponseEntity<ApiResponse> createLocation(@Valid @RequestBody LocationRequest locationRequest) {
        locationRequest.sanitizeInput();
        locationService.addLocation(locationRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, LocationMessage.ADD_LOCATION, 201);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/location/update/{id}")
    public ResponseEntity<ApiResponse> updateLocation(@PathVariable Integer id,
            @Valid @RequestBody LocationRequest locationRequest) {
        locationRequest.sanitizeInput();
        locationService.updateLocation(id, locationRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, LocationMessage.UPDATE_LOCATION, 200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/location/delete/{id}")
    public ResponseEntity<ApiResponse> deleteLocationById(@PathVariable Integer id) {
        locationService.deleteLocation(id);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, LocationMessage.DELETE_LOCATION, 200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/location/view")
    public ResponseEntity<ApiResponse> getAllLocations() {
        List<Location> location = locationService.getAllLocations();
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, LocationMessage.LOCATIONS, 200);
        response.setData(location);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/country/view")
    public ResponseEntity<ApiResponse> getAllCountries() {
        List<MasterCountry> country = masterCountryService.getAllCountries();
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, "countries fetched successfully", 200);
        response.setData(country);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/state/view")
    public ResponseEntity<ApiResponse> getAllStates() {
        List<MasterState> states = masterStateService.getAllStates();
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, "States Fetched Successfully", 200);
        response.setData(states);
        return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
    }

    @GetMapping("/{countryId}")
    public ResponseEntity<ApiResponse> getStatesByCountryId(@PathVariable Integer countryId) {
        List<MasterState> states = masterStateService.getStatesByCountryId(countryId);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, "States Fetched Successfully", 200);
        response.setData(states);
        return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
    }

    @GetMapping("/city/view")
    public ResponseEntity<ApiResponse> getAllCities() {
        List<MasterCity> cities = masterCityService.getAllCities();
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, "Cities Fetched Successfully", 200);
        response.setData(cities);
        return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
    }

    @GetMapping("/{stateId}")
    public ResponseEntity<ApiResponse> getCitiesByStateId(@PathVariable Integer stateId) {
        List<MasterCity> cities = masterCityService.getCitiesByStateId(stateId);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, "cities Fetched Successfully", 200);
        response.setData(cities);
        return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
    }

}
