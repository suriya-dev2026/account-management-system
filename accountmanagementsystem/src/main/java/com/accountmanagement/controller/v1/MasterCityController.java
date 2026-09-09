package com.accountmanagement.controller.v1;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.model.MasterCity;
import com.accountmanagement.response.ApiResponse;
import com.accountmanagement.service.MasterCityService;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "/v1/city")
@Tag(name = "MasterCityController")
public class MasterCityController {

    private final MasterCityService cityService;

    public MasterCityController(MasterCityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse> getAllCities() {
        List<MasterCity> cities = cityService.getAllCities();
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, "Cities Fetched Successfully", 200);
        response.setData(cities);
        return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
    }

    @GetMapping("/{stateId}")
    public ResponseEntity<ApiResponse> getCitiesByStateId(@PathVariable Integer stateId) {
        List<MasterCity> cities = cityService.getCitiesByStateId(stateId);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, "cities Fetched Successfully", 200);
        response.setData(cities);
        return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
    }

}
