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
import com.accountmanagement.constants.message.AccountingAccountTypeMessage;
import com.accountmanagement.model.AccountingAccountType;
import com.accountmanagement.request.AccountingAccountTypeRequest;
import com.accountmanagement.response.ApiResponse;
import com.accountmanagement.service.AccountingAccountTypeService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "v1/accounting/account/type")
@Tag(name = "AccountingAccountTypeController")
public class AccountingAccountTypeController {

    private final AccountingAccountTypeService accountingAccountTypeService;

    public AccountingAccountTypeController(AccountingAccountTypeService accountingAccountTypeService) {
        this.accountingAccountTypeService = accountingAccountTypeService;
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createAccountingAccountType(
            @Valid @RequestBody AccountingAccountTypeRequest accountingAccountTypeRequest) {
        accountingAccountTypeRequest.sanitizeInput();
        accountingAccountTypeService.createAccountingAccountType(accountingAccountTypeRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                AccountingAccountTypeMessage.CREATE_ACCOUNTING_ACCOUNT_TYPE, 201);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateAccountingAccountType(@PathVariable Integer id,
            @Valid @RequestBody AccountingAccountTypeRequest accountingAccountTypeRequest) {
        accountingAccountTypeRequest.sanitizeInput();
        accountingAccountTypeService.updateAccountingAccountType(id, accountingAccountTypeRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                AccountingAccountTypeMessage.UPDATE_ACCOUNTING_ACCOUNT_TYPE, 200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteAccountingAccountTypeById(@PathVariable Integer id) {
        accountingAccountTypeService.deleteAccountTypeById(id);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                AccountingAccountTypeMessage.DELETE_ACCOUNTING_ACCOUNT_TYPE, 200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> viewAllAccountingAccountTypes() {
        List<AccountingAccountType> accountingAccountType = accountingAccountTypeService
                .viewAllAccountingAccountTypes();
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                AccountingAccountTypeMessage.VIEW_ALL_ACCOUNTING_ACCOUNT_TYPES, 200);
        response.setData(accountingAccountType);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
