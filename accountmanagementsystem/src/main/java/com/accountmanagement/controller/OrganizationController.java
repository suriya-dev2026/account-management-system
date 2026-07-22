package com.accountmanagement.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.constants.message.OrganizationMessage;
import com.accountmanagement.request.OrganizationRequest;
import com.accountmanagement.response.ApiResponse;
import com.accountmanagement.service.OrganizationService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "organization")
public class OrganizationController {

    private final OrganizationService organizationService;

    OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> createOrganization(@Valid @RequestBody OrganizationRequest organizationRequest) {
        organizationRequest.sanitizeInput();
        organizationService.addOrganization(organizationRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, OrganizationMessage.ADD_ORGANIZATION, 201);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/update/id/{id}")
    public ResponseEntity<ApiResponse> updateOrganizationById(@PathVariable UUID id,
            @Valid @RequestBody OrganizationRequest organizationRequest) {
        organizationRequest.sanitizeInput();
        organizationService.updateOrganization(id, organizationRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, OrganizationMessage.UPDATE_ORGANIZATION, 200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/id/{id}")
    public ResponseEntity<ApiResponse> deleteOrganizationById(@PathVariable UUID id) {
        organizationService.deleteOrganizationById(id);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, OrganizationMessage.DELETE_ORGANIZATION, 200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
