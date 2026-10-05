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
import com.accountmanagement.constants.message.OrganizationPaymentMethodMessage;
import com.accountmanagement.model.OrganizationPaymentMethod;
import com.accountmanagement.request.OrganizationPaymentMethodRequest;
import com.accountmanagement.response.ApiResponse;
import com.accountmanagement.service.OrganizationPaymentMethodService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/v1/organization/payment/method")
@Tag(name = "OrganizationPaymentMethodController")
public class OrganizationPaymentMethodController {

        private OrganizationPaymentMethodService organizationPaymentMethodService;

        public OrganizationPaymentMethodController(OrganizationPaymentMethodService organizationPaymentMethodService) {
                this.organizationPaymentMethodService = organizationPaymentMethodService;
        }

        @PostMapping("/create")
        public ResponseEntity<ApiResponse> createOrganizationPaymentMethod(
                        @Valid @RequestBody OrganizationPaymentMethodRequest organizationPaymentMethodRequest) {
                organizationPaymentMethodRequest.sanitizeInput();
                organizationPaymentMethodService
                                .createOrganizatonPaymentMethod(organizationPaymentMethodRequest);
                ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                                OrganizationPaymentMethodMessage.CREATE_ORGANIZATION_PAYMENT_METHOD, 201);
                return new ResponseEntity<>(response, HttpStatus.CREATED);
        }

        @PutMapping("/update/{id}")
        public ResponseEntity<ApiResponse> updateOrganizationPaymentMethodById(@PathVariable Integer id,
                        @Valid @RequestBody OrganizationPaymentMethodRequest organizationPaymentMethodRequest) {
                organizationPaymentMethodRequest.sanitizeInput();
                organizationPaymentMethodService.updateOrganizationPaymentMethodById(id,
                                organizationPaymentMethodRequest);
                ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                                OrganizationPaymentMethodMessage.UPDATE_ORGANIZATION_PAYMENT_METHOD, 200);
                return new ResponseEntity<>(response, HttpStatus.OK);
        }

        @DeleteMapping("/delete/{id}")
        public ResponseEntity<ApiResponse> deleteOrganizationPaymentMethodById(@PathVariable Integer id) {
                organizationPaymentMethodService.deleteOrganizationPaymentMethodById(id);
                ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                                OrganizationPaymentMethodMessage.DELETE_ORGANIZATION_PAYMENT_METHOD, 200);
                return new ResponseEntity<>(response, HttpStatus.OK);
        }

        @GetMapping
        public ResponseEntity<ApiResponse> viewAllOrganizationPaymentMethods() {
                List<OrganizationPaymentMethod> organizationPaymentMethod = organizationPaymentMethodService
                                .viewAllOrganizationPaymentMethods();
                System.out.println("View all " + organizationPaymentMethod);
                ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                                OrganizationPaymentMethodMessage.VIEW_ALL_ORGANIZATION_PAYMENT_METHODS, 200);
                response.setData(organizationPaymentMethod);
                return new ResponseEntity<>(response, HttpStatus.OK);
        }
}
