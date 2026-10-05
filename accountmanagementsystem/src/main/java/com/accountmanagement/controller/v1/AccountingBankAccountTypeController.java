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
import com.accountmanagement.constants.message.AccountingBankAccountTypeMessage;
import com.accountmanagement.model.AccountingBankAccountType;
import com.accountmanagement.request.AccountingBankAccountTypeRequest;
import com.accountmanagement.response.ApiResponse;
import com.accountmanagement.service.AccountingBankAccountTypeService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/v1/accounting/bank/account/type")
@RequiredArgsConstructor
@Tag(name = "AccountingBankAccountTypeController")
public class AccountingBankAccountTypeController {

    private final AccountingBankAccountTypeService accountingBankAccountTypeService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createAccountingBankAccount(
            @Valid @RequestBody AccountingBankAccountTypeRequest accountingBankAccountTypeRequest) {
        accountingBankAccountTypeRequest.sanitizeInput();
        accountingBankAccountTypeService.createAccountingBankAccountType(accountingBankAccountTypeRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                AccountingBankAccountTypeMessage.CREATE_ACCOUNTING_BANK_ACCOUNT_TYPE, 201);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateAccountingBankAccountTypeById(@PathVariable Integer id,
            @Valid @RequestBody AccountingBankAccountTypeRequest accountingBankAccountTypeRequest) {
        accountingBankAccountTypeRequest.sanitizeInput();
        accountingBankAccountTypeService.updateAccountingBankAccountTypeById(id, accountingBankAccountTypeRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                AccountingBankAccountTypeMessage.UPDATE_ACCOUNTING_BANK_ACCOUNT_TYPE, 200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteAccountingBankAccountTypeById(@PathVariable Integer id) {
        accountingBankAccountTypeService.deleteAccountingBankAccounttypeById(id);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                AccountingBankAccountTypeMessage.DELETE_ACCOUNTING_BANK_ACCOUNT_TYPE, 200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> viewAllAccountingBankAccountTypes() {
        List<AccountingBankAccountType> account = accountingBankAccountTypeService.viewAllAccountingBankAccountType();
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                AccountingBankAccountTypeMessage.VIEW_ALL_ACCOUNTING_BANK_ACCOUNT_TYPES, 200);
        response.setData(account);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
