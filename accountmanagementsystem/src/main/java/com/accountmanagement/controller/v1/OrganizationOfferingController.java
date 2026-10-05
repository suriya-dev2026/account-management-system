package com.accountmanagement.controller.v1;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.model.OrganizationOffering;
import com.accountmanagement.request.OrganizationOfferingRequest;
import com.accountmanagement.response.ApiResponse;
import com.accountmanagement.service.OrganizationOfferingService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/v1/organization/offering")
@RequiredArgsConstructor
@Tag(name = "OrganizationOfferingController")
public class OrganizationOfferingController {

    private final OrganizationOfferingService organizationOfferingService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createOffering(@Valid @RequestBody OrganizationOfferingRequest request) {
        OrganizationOffering offering = organizationOfferingService.createOffering(request);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, "Offering Created", 201);
        response.setRequestInfo(offering);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> viewAllOfferings() {
        List<OrganizationOffering> offering = organizationOfferingService.viewAllOfferings();
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, "Offering fetched", 200);
        response.setRequestInfo(offering);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
