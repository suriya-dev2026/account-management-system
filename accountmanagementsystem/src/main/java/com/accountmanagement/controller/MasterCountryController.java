package com.accountmanagement.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.model.MasterCountry;
import com.accountmanagement.response.ApiResponse;
import com.accountmanagement.service.MasterCountryService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "country")
@Tag(name = "MasterCountryController")
public class MasterCountryController {

    private final MasterCountryService masterCountryService;

    public MasterCountryController(MasterCountryService masterCountryService) {
        this.masterCountryService = masterCountryService;
    }

    @GetMapping("/")
    public ResponseEntity<ApiResponse> getAllCountries() {
        List<MasterCountry> country = masterCountryService.getAllCountries();
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, "countries fetched successfully", 200);
        response.setData(country);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
