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
import com.accountmanagement.constants.message.OrganizationOfferingTypeMessage;
import com.accountmanagement.model.OrganizationOfferingType;
import com.accountmanagement.request.OrganizationOfferingTypeRequest;
import com.accountmanagement.response.ApiResponse;
import com.accountmanagement.service.OrganizationOfferingTypeService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/v1/organization/offering/type")
@Tag(name = "OrganizationOfferingTypeController")
public class OrganizationOfferingTypeController {

    private final OrganizationOfferingTypeService organizationOfferingTypeService;

    public OrganizationOfferingTypeController(OrganizationOfferingTypeService organizationOfferingTypeService) {
        this.organizationOfferingTypeService = organizationOfferingTypeService;
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createOrganizationOfferingType(
            @Valid @RequestBody OrganizationOfferingTypeRequest organizationOfferingTypeRequest) {
        organizationOfferingTypeRequest.sanitizeInput();
        organizationOfferingTypeService.createOrganizationOfferingType(organizationOfferingTypeRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                OrganizationOfferingTypeMessage.CREATE_ORGANIZATION_OFFERING_TYPE, 201);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateOrganizationOfferingType(@PathVariable Integer id,
            @Valid @RequestBody OrganizationOfferingTypeRequest organizationOfferingTypeRequest) {
        organizationOfferingTypeRequest.sanitizeInput();
        organizationOfferingTypeService.updateOrganizationOfferingType(id, organizationOfferingTypeRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                OrganizationOfferingTypeMessage.UPDATE_ORGANIZATION_OFFERING_TYPE, 200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteOrganizationOfferingTypeById(@PathVariable Integer id) {
        organizationOfferingTypeService.deleteOrganizationOfferingTypeById(id);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                OrganizationOfferingTypeMessage.DELETE_ORGANIZATION_OFFERING_TYPE, 200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> viewAllOrganizationOfferingTypes() {
        List<OrganizationOfferingType> organizationOfferingTypes = organizationOfferingTypeService
                .viewAllOrganizationOfferingTypes();
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                OrganizationOfferingTypeMessage.VIEW_ALL_ORGANIZATION_OFFERING_TYPES, 200);
        response.setData(organizationOfferingTypes);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
