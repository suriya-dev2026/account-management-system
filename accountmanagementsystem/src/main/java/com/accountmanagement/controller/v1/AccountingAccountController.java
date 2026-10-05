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
import com.accountmanagement.constants.message.AccountingAccountMessage;
import com.accountmanagement.model.AccountingAccount;
import com.accountmanagement.request.AccountingAccountRequest;
import com.accountmanagement.response.ApiResponse;
import com.accountmanagement.service.AccountingAccountService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/v1/accounting/account")
@Tag(name = "AccountingAccountController")
public class AccountingAccountController {

    private final AccountingAccountService accountingAccountService;

    public AccountingAccountController(AccountingAccountService accountingAccountService) {
        this.accountingAccountService = accountingAccountService;
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createAccountingAccount(
            @Valid @RequestBody AccountingAccountRequest accountingAccountRequest) {
        accountingAccountRequest.sanitizeInput();
        accountingAccountService.createAccountingAccount(accountingAccountRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, AccountingAccountMessage.CREATE_ACCOUNTING_ACCOUNT,
                201);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateAccountingAccountById(@PathVariable UUID id,
            @Valid @RequestBody AccountingAccountRequest accountingAccountRequest) {
        accountingAccountRequest.sanitizeInput();
        accountingAccountService.updateAccountingAccount(id, accountingAccountRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, AccountingAccountMessage.UPDATE_ACCOUNTING_ACCOUNT,
                200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteAccountingAccountById(@PathVariable UUID id) {
        accountingAccountService.deleteAccountingAccount(id);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, AccountingAccountMessage.DELETE_ACCOUNTING_ACCOUNT,
                200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> viewAllAccountingAccount() {
        List<AccountingAccount> accountingAccount = accountingAccountService.viewAllAccountingAccounts();
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                AccountingAccountMessage.VIEW_ALL_ACCOUNTING_ACCOUNTS, 200);
        response.setData(accountingAccount);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
