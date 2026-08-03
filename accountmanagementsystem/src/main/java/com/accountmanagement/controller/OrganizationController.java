package com.accountmanagement.controller;

import java.util.List;
import java.util.UUID;

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
import com.accountmanagement.constants.message.OrganizationMessage;
import com.accountmanagement.model.Organization;
import com.accountmanagement.request.OrganizationRegistrationRequest;
import com.accountmanagement.request.OrganizationUpdationRequest;
import com.accountmanagement.response.ApiResponse;
import com.accountmanagement.service.OrganizationService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "organization")
@Tag(name = "OrganizationController")
public class OrganizationController {

    private final OrganizationService organizationService;

    OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> createOrganization(@Valid @RequestBody OrganizationRegistrationRequest organizationRequest) {
        organizationRequest.sanitizeInput();
        organizationService.registerOrganization(organizationRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, OrganizationMessage.ADD_ORGANIZATION, 201);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateOrganizationById(@PathVariable UUID id,
            @Valid @RequestBody OrganizationUpdationRequest organizationRequest) {
        organizationRequest.sanitizeInput();
        organizationService.updateOrganization(id, organizationRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, OrganizationMessage.UPDATE_ORGANIZATION, 200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteOrganizationById(@PathVariable UUID id) {
        organizationService.deleteOrganizationById(id);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, OrganizationMessage.DELETE_ORGANIZATION, 200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse> viewAllOrganizations() {
        List<Organization> organizations = organizationService.viewAll();
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, OrganizationMessage.ORGANIZATIONS, 200);
        response.setData(organizations);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
