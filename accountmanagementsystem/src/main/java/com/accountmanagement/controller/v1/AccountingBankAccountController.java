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
import com.accountmanagement.constants.message.AccountingBankAccountMessage;
import com.accountmanagement.model.AccountingBankAccount;
import com.accountmanagement.request.AccountingBankAccountRequest;
import com.accountmanagement.request.AccountingBankAccountUpdateRequest;
import com.accountmanagement.response.ApiResponse;
import com.accountmanagement.service.AccountingBankAccountService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/v1/accounting/bank/account")
@RequiredArgsConstructor
@Tag(name = "AccountingBankAccountController")
public class AccountingBankAccountController {

    private final AccountingBankAccountService accountingBankAccountService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createAccountingBankAccount(
            @Valid @RequestBody AccountingBankAccountRequest accountingBankAccountRequest) {
        accountingBankAccountRequest.sanitizeInput();
        accountingBankAccountService.createAccountingBankAccount(accountingBankAccountRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                AccountingBankAccountMessage.CREATE_ACCOUNTING_BANK_ACCOUNT, 201);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateAccountingBankAccount(@PathVariable UUID id,
            @Valid @RequestBody AccountingBankAccountUpdateRequest request) {
        request.sanitizeInput();
        accountingBankAccountService.updateBankAccountById(id, request);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                AccountingBankAccountMessage.UPDATE_ACCOUNTING_BANK_ACCOUNT, 200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteAccountingBankAccount(@PathVariable UUID id) {
        accountingBankAccountService.deleteBankAccountById(id);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                AccountingBankAccountMessage.DELETE_ACCOUNTING_BANK_ACCOUNT, 200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> viewAllAccountingBankAccount() {
        List<AccountingBankAccount> account = accountingBankAccountService.viewAllBankAccounts();
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                AccountingBankAccountMessage.VIEW_ALL_ACCOUNTING_BANK_ACCOUNT, 200);
        response.setData(account);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
