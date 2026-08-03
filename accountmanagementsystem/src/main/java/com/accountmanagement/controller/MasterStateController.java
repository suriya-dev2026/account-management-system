package com.accountmanagement.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.model.MasterState;
import com.accountmanagement.response.ApiResponse;
import com.accountmanagement.service.MasterStateService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "states")
@Tag(name = "StateController")
public class MasterStateController {

    private final MasterStateService stateService;

    public MasterStateController(MasterStateService stateService) {
        this.stateService = stateService;
    }

    @GetMapping("/")
    public ResponseEntity<ApiResponse> getAllStates() {
        List<MasterState> states = stateService.getAllStates();
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, "States Fetched Successfully", 200);
        response.setData(states);
        return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
    }

    @GetMapping("/{countryId}")
    public ResponseEntity<ApiResponse> getStatesByCountryId(@PathVariable Integer countryId) {
        List<MasterState> states = stateService.getStatesByCountryId(countryId);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, "States Fetched Successfully", 200);
        response.setData(states);
        return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
    }
}
