package com.accountmanagement.controller.v1;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.model.OrganizationPaymentTransaction;
import com.accountmanagement.request.OrganizationPaymentTransactionRequest;
import com.accountmanagement.request.PaymentGatewayRequest;
import com.accountmanagement.response.ApiResponse;
import com.accountmanagement.service.OrganizationPaymentTransactionService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/v1/organization/payment/transaction")
@RequiredArgsConstructor
@Tag(name = "OrganizationPaymentTransactionController")
public class OrganizationPaymentTransactionController {

    private final OrganizationPaymentTransactionService organizationPaymentTransactionService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createPaymentTransaction(
            @Valid @RequestBody OrganizationPaymentTransactionRequest request) {
        request.sanitizeInput();
        OrganizationPaymentTransaction transaction = organizationPaymentTransactionService
                .createPaymentTransaction(request);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, "created", 201);
        response.setRequestInfo(transaction);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/update/{transactionId}")
    public ResponseEntity<ApiResponse> updatePaymentResponse(@PathVariable String transactionId,
            @RequestBody PaymentGatewayRequest request) {
        OrganizationPaymentTransaction transaction = organizationPaymentTransactionService
                .updatePaymentResponse(transactionId, request);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, "updated", 200);
        response.setRequestInfo(transaction);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
