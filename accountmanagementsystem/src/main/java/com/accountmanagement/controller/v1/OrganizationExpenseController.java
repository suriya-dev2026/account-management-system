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
import com.accountmanagement.model.OrganizationExpense;
import com.accountmanagement.request.OrganizationExpenseRequest;
import com.accountmanagement.response.ApiResponse;
import com.accountmanagement.service.OrganizationExpenseService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/v1/organization/expense")
@RequiredArgsConstructor
@Tag(name = "OrganizationExpenseController")
public class OrganizationExpenseController {

    private final OrganizationExpenseService organizationExpenseService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createOrganizationExpense(
            @Valid @RequestBody OrganizationExpenseRequest organizationExpenseRequest) {
        organizationExpenseRequest.sanitizeInput();
        System.out.println("Organization Payment Method" + organizationExpenseRequest.getPaymentMethodId());
        OrganizationExpense expense = organizationExpenseService.createOrganizationExpense(organizationExpenseRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, "created", 201);
        response.setRequestInfo(expense);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> viewAllOrganizationExpenses() {
        List<OrganizationExpense> expense = organizationExpenseService.viewAllOrganizationExpenses();
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, "created", 200);
        response.setData(expense);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
