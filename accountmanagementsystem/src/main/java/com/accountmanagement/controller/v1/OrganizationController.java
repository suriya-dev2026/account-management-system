package com.accountmanagement.controller.v1;

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
import com.accountmanagement.constants.message.OrganizationExpenseTypeMessage;
import com.accountmanagement.constants.message.OrganizationMessage;
import com.accountmanagement.constants.message.OrganizationOfferingTypeMessage;
import com.accountmanagement.constants.message.OrganizationPaymentMethodMessage;
import com.accountmanagement.dto.OrganizationAuditLogDto;
import com.accountmanagement.model.Organization;
import com.accountmanagement.model.OrganizationExpense;
import com.accountmanagement.model.OrganizationExpenseType;
import com.accountmanagement.model.OrganizationOffering;
import com.accountmanagement.model.OrganizationOfferingType;
import com.accountmanagement.model.OrganizationPaymentMethod;
import com.accountmanagement.model.OrganizationPaymentTransaction;
import com.accountmanagement.request.OrganizationExpenseRequest;
import com.accountmanagement.request.OrganizationExpenseTypeRequest;
import com.accountmanagement.request.OrganizationOfferingRequest;
import com.accountmanagement.request.OrganizationOfferingTypeRequest;
import com.accountmanagement.request.OrganizationPaymentMethodRequest;
import com.accountmanagement.request.OrganizationPaymentTransactionRequest;
import com.accountmanagement.request.OrganizationUpdationRequest;
import com.accountmanagement.request.PaymentGatewayRequest;
import com.accountmanagement.response.ApiResponse;
import com.accountmanagement.service.OrganizationAuditLogService;
import com.accountmanagement.service.OrganizationExpenseService;
import com.accountmanagement.service.OrganizationExpenseTypeService;
import com.accountmanagement.service.OrganizationOfferingService;
import com.accountmanagement.service.OrganizationOfferingTypeService;
import com.accountmanagement.service.OrganizationPaymentMethodService;
import com.accountmanagement.service.OrganizationPaymentTransactionService;
import com.accountmanagement.service.OrganizationService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/v1/organization")
@Tag(name = "OrganizationController")
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationService organizationService;

    private final OrganizationAuditLogService organizationAuditLogService;

    private final OrganizationOfferingTypeService organizationOfferingTypeService;

    private final OrganizationOfferingService organizationOfferingService;

    private final OrganizationExpenseTypeService organizationExpenseTypeService;

    private final OrganizationExpenseService organizationExpenseService;

    private final OrganizationPaymentMethodService organizationPaymentMethodService;

    private final OrganizationPaymentTransactionService organizationPaymentTransactionService;

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

    @GetMapping("/audit/log")
    public ResponseEntity<ApiResponse> getAllOrganizationAuditLogs() {
        List<OrganizationAuditLogDto> log = organizationAuditLogService.getAllOrganizationLogs();
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, "Logs Fetched Successfully", 200);
        response.setData(log);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/offering/type/create")
    public ResponseEntity<ApiResponse> createOrganizationOfferingType(
            @Valid @RequestBody OrganizationOfferingTypeRequest organizationOfferingTypeRequest) {
        organizationOfferingTypeRequest.sanitizeInput();
        organizationOfferingTypeService.createOrganizationOfferingType(organizationOfferingTypeRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                OrganizationOfferingTypeMessage.CREATE_ORGANIZATION_OFFERING_TYPE, 201);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/offering/type/update/{id}")
    public ResponseEntity<ApiResponse> updateOrganizationOfferingType(@PathVariable Integer id,
            @Valid @RequestBody OrganizationOfferingTypeRequest organizationOfferingTypeRequest) {
        organizationOfferingTypeRequest.sanitizeInput();
        organizationOfferingTypeService.updateOrganizationOfferingType(id, organizationOfferingTypeRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                OrganizationOfferingTypeMessage.UPDATE_ORGANIZATION_OFFERING_TYPE, 200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/offering/type/delete/{id}")
    public ResponseEntity<ApiResponse> deleteOrganizationOfferingTypeById(@PathVariable Integer id) {
        organizationOfferingTypeService.deleteOrganizationOfferingTypeById(id);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                OrganizationOfferingTypeMessage.DELETE_ORGANIZATION_OFFERING_TYPE, 200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/offering/type")
    public ResponseEntity<ApiResponse> viewAllOrganizationOfferingTypes() {
        List<OrganizationOfferingType> organizationOfferingTypes = organizationOfferingTypeService
                .viewAllOrganizationOfferingTypes();
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                OrganizationOfferingTypeMessage.VIEW_ALL_ORGANIZATION_OFFERING_TYPES, 200);
        response.setData(organizationOfferingTypes);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/offering/create")
    public ResponseEntity<ApiResponse> createOffering(@Valid @RequestBody OrganizationOfferingRequest request) {
        OrganizationOffering offering = organizationOfferingService.createOffering(request);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, "Offering Created", 201);
        response.setRequestInfo(offering);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/offering")
    public ResponseEntity<ApiResponse> viewAllOfferings() {
        List<OrganizationOffering> offering = organizationOfferingService.viewAllOfferings();
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, "Offering fetched", 200);
        response.setRequestInfo(offering);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/expense/type/create")
    public ResponseEntity<ApiResponse> createOrganizationExpenseType(
            @Valid @RequestBody OrganizationExpenseTypeRequest organizationExpenseTypeRequest) {
        organizationExpenseTypeRequest.sanitizeInput();
        organizationExpenseTypeService.createOrganizationExpenseType(organizationExpenseTypeRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                OrganizationExpenseTypeMessage.CREATE_ORGANIZATION_EXPENSE_TYPE, 201);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/expense/type/update/{id}")
    public ResponseEntity<ApiResponse> updateOrganizationExpenseTypeById(@PathVariable Integer id,
            @Valid @RequestBody OrganizationExpenseTypeRequest organizationExpenseTypeRequest) {
        organizationExpenseTypeRequest.sanitizeInput();
        organizationExpenseTypeService.updateOrganizationExpenseType(id, organizationExpenseTypeRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                OrganizationExpenseTypeMessage.UPDATE_ORGANIZATION_EXPENSE_TYPE, 200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/expense/type/delete/{id}")
    public ResponseEntity<ApiResponse> deleteOrganizationExpenseTypeById(@PathVariable Integer id) {
        organizationExpenseTypeService.deleteOrganizationExpenseTypeById(id);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                OrganizationExpenseTypeMessage.DELETE_ORGANIZATION_EXPENSE_TYPE, 200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/expense/type")
    public ResponseEntity<ApiResponse> viewAllOrganizationExpenseTypes() {
        List<OrganizationExpenseType> organizationExpenseTypes = organizationExpenseTypeService
                .viewAllOrganizationExpenseTypes();
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                OrganizationExpenseTypeMessage.VIEW_ALL_ORGANIZATION_EXPENSE_TYPES, 200);
        response.setData(organizationExpenseTypes);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/expense/create")
    public ResponseEntity<ApiResponse> createOrganizationExpense(
            @Valid @RequestBody OrganizationExpenseRequest organizationExpenseRequest) {
        organizationExpenseRequest.sanitizeInput();
        System.out.println("Organization Payment Method" + organizationExpenseRequest.getPaymentMethodId());
        OrganizationExpense expense = organizationExpenseService.createOrganizationExpense(organizationExpenseRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, "created", 201);
        response.setRequestInfo(expense);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/expense")
    public ResponseEntity<ApiResponse> viewAllOrganizationExpenses() {
        List<OrganizationExpense> expense = organizationExpenseService.viewAllOrganizationExpenses();
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, "created", 200);
        response.setData(expense);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/payment/method/create")
    public ResponseEntity<ApiResponse> createOrganizationPaymentMethod(
            @Valid @RequestBody OrganizationPaymentMethodRequest organizationPaymentMethodRequest) {
        organizationPaymentMethodRequest.sanitizeInput();
        organizationPaymentMethodService
                .createOrganizatonPaymentMethod(organizationPaymentMethodRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                OrganizationPaymentMethodMessage.CREATE_ORGANIZATION_PAYMENT_METHOD, 201);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/payment/method/update/{id}")
    public ResponseEntity<ApiResponse> updateOrganizationPaymentMethodById(@PathVariable Integer id,
            @Valid @RequestBody OrganizationPaymentMethodRequest organizationPaymentMethodRequest) {
        organizationPaymentMethodRequest.sanitizeInput();
        organizationPaymentMethodService.updateOrganizationPaymentMethodById(id,
                organizationPaymentMethodRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                OrganizationPaymentMethodMessage.UPDATE_ORGANIZATION_PAYMENT_METHOD, 200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/payment/method/delete/{id}")
    public ResponseEntity<ApiResponse> deleteOrganizationPaymentMethodById(@PathVariable Integer id) {
        organizationPaymentMethodService.deleteOrganizationPaymentMethodById(id);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                OrganizationPaymentMethodMessage.DELETE_ORGANIZATION_PAYMENT_METHOD, 200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/payment/method")
    public ResponseEntity<ApiResponse> viewAllOrganizationPaymentMethods() {
        List<OrganizationPaymentMethod> organizationPaymentMethod = organizationPaymentMethodService
                .viewAllOrganizationPaymentMethods();
        System.out.println("View all " + organizationPaymentMethod);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                OrganizationPaymentMethodMessage.VIEW_ALL_ORGANIZATION_PAYMENT_METHODS, 200);
        response.setData(organizationPaymentMethod);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/payment/transaction/create")
    public ResponseEntity<ApiResponse> createPaymentTransaction(
            @Valid @RequestBody OrganizationPaymentTransactionRequest request) {
        request.sanitizeInput();
        OrganizationPaymentTransaction transaction = organizationPaymentTransactionService
                .createPaymentTransaction(request);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, "created", 201);
        response.setRequestInfo(transaction);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/payment/transaction/update/{transactionId}")
    public ResponseEntity<ApiResponse> updatePaymentResponse(@PathVariable String transactionId,
            @RequestBody PaymentGatewayRequest request) {
        OrganizationPaymentTransaction transaction = organizationPaymentTransactionService
                .updatePaymentResponse(transactionId, request);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, "updated", 200);
        response.setRequestInfo(transaction);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
