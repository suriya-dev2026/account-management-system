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
import com.accountmanagement.constants.message.AccountingBankTransactionTypeMessage;
import com.accountmanagement.model.AccountingBankTransactionType;
import com.accountmanagement.request.AccountingBankTransactionTypeRequest;
import com.accountmanagement.response.ApiResponse;
import com.accountmanagement.service.AccountingBankTransactionTypeService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/v1/accounting/bank/transaction/type")
@RequiredArgsConstructor
@Tag(name = "AccountingBankTransactionTypeController")
public class AccountingBankTransactionTypeController {

        private final AccountingBankTransactionTypeService accountingBankTransactionTypeService;

        @PostMapping("/create")
        public ResponseEntity<ApiResponse> createAccountingBankTransactionType(
                        @Valid @RequestBody AccountingBankTransactionTypeRequest request) {
                request.sanitizeInput();
                accountingBankTransactionTypeService.createAccountingBankTransactionType(request);
                ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                                AccountingBankTransactionTypeMessage.CREATE_ACCOUNTING_BANK_TRANSACTION_TYPE, 201);
                return new ResponseEntity<>(response, HttpStatus.CREATED);
        }

        @PutMapping("/update/{id}")
        public ResponseEntity<ApiResponse> updateAccountingBankTransactionType(@PathVariable Integer id,
                        @Valid @RequestBody AccountingBankTransactionTypeRequest request) {
                request.sanitizeInput();
                accountingBankTransactionTypeService.updateAccountingBankTransactionTypeById(id, request);
                ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                                AccountingBankTransactionTypeMessage.UPDATE_ACCOUNTING_BANK_TRANSACTION_TYPE, 200);
                return new ResponseEntity<>(response, HttpStatus.OK);
        }

        @DeleteMapping("/delete/{id}")
        public ResponseEntity<ApiResponse> deleteAccountingBankTransactionTypeById(@PathVariable Integer id) {
                accountingBankTransactionTypeService.deleteAccountingBankTransactionType(id);
                ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                                AccountingBankTransactionTypeMessage.DELETE_ACCOUNTING_BANK_TRANSACTION_TYPE, 200);
                return new ResponseEntity<>(response, HttpStatus.OK);
        }

        @GetMapping
        public ResponseEntity<ApiResponse> viewAllAccountingBankTransactionTypes() {
                List<AccountingBankTransactionType> transactionType = accountingBankTransactionTypeService
                                .viewAllAccountingBankTransactionType();
                ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                                AccountingBankTransactionTypeMessage.VIEW_ALL_ACCOUNTING_BANK_TRANSACTION_TYPES, 200);
                response.setData(transactionType);
                return new ResponseEntity<>(response, HttpStatus.OK);
        }
}
