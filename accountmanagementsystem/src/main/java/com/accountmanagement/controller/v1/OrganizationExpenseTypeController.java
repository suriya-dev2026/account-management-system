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
import com.accountmanagement.constants.message.OrganizationExpenseTypeMessage;
import com.accountmanagement.model.OrganizationExpenseType;
import com.accountmanagement.request.OrganizationExpenseTypeRequest;
import com.accountmanagement.response.ApiResponse;
import com.accountmanagement.service.OrganizationExpenseTypeService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/v1/organization/expense/type")
@RequiredArgsConstructor
@Tag(name = "OrganizationExpenseTypeController")
public class OrganizationExpenseTypeController {

        private final OrganizationExpenseTypeService organizationExpenseTypeService;

        @PostMapping("/create")
        public ResponseEntity<ApiResponse> createOrganizationExpenseType(
                        @Valid @RequestBody OrganizationExpenseTypeRequest organizationExpenseTypeRequest) {
                organizationExpenseTypeRequest.sanitizeInput();
                organizationExpenseTypeService.createOrganizationExpenseType(organizationExpenseTypeRequest);
                ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                                OrganizationExpenseTypeMessage.CREATE_ORGANIZATION_EXPENSE_TYPE, 201);
                return new ResponseEntity<>(response, HttpStatus.CREATED);
        }

        @PutMapping("/update/{id}")
        public ResponseEntity<ApiResponse> updateOrganizationExpenseTypeById(@PathVariable Integer id,
                        @Valid @RequestBody OrganizationExpenseTypeRequest organizationExpenseTypeRequest) {
                organizationExpenseTypeRequest.sanitizeInput();
                organizationExpenseTypeService.updateOrganizationExpenseType(id, organizationExpenseTypeRequest);
                ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                                OrganizationExpenseTypeMessage.UPDATE_ORGANIZATION_EXPENSE_TYPE, 200);
                return new ResponseEntity<>(response, HttpStatus.OK);
        }

        @DeleteMapping("/delete/{id}")
        public ResponseEntity<ApiResponse> deleteOrganizationExpenseTypeById(@PathVariable Integer id) {
                organizationExpenseTypeService.deleteOrganizationExpenseTypeById(id);
                ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                                OrganizationExpenseTypeMessage.DELETE_ORGANIZATION_EXPENSE_TYPE, 200);
                return new ResponseEntity<>(response, HttpStatus.OK);
        }

        @GetMapping
        public ResponseEntity<ApiResponse> viewAllOrganizationExpenseTypes() {
                List<OrganizationExpenseType> organizationExpenseTypes = organizationExpenseTypeService
                                .viewAllOrganizationExpenseTypes();
                ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                                OrganizationExpenseTypeMessage.VIEW_ALL_ORGANIZATION_EXPENSE_TYPES, 200);
                response.setData(organizationExpenseTypes);
                return new ResponseEntity<>(response, HttpStatus.OK);
        }

}
