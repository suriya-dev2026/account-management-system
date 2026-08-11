package com.accountmanagement.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.model.Pincode;
import com.accountmanagement.response.ApiResponse;
import com.accountmanagement.service.PincodeService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "pincode")
@Tag(name = "PincodeController")
public class PincodeController {

    private final PincodeService pincodeService;

    public PincodeController(PincodeService pincodeService) {
        this.pincodeService = pincodeService;
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse> getAllPincodes() {
        List<Pincode> pincodes = pincodeService.getAllPincodes();
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, "pincodes Fetched Successfully", 200);
        response.setData(pincodes);
        return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
    }

}
