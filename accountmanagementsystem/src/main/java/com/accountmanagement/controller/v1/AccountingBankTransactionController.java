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
import com.accountmanagement.constants.message.AccountingBankTransactionMessage;
import com.accountmanagement.model.AccountingBankTransaction;
import com.accountmanagement.request.AccountingBankTransactionRequest;
import com.accountmanagement.response.ApiResponse;
import com.accountmanagement.service.AccountingBankTransactionService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/v1/accounting/bank/transaction")
@RequiredArgsConstructor
@Tag(name = "AccountingBankTransactionController")
public class AccountingBankTransactionController {

        private final AccountingBankTransactionService accountingBankTransactionService;

        @PostMapping("/create")
        public ResponseEntity<ApiResponse> createAccountingBankTransaction(
                        @Valid @RequestBody AccountingBankTransactionRequest request) {
                request.sanitizeInput();
                AccountingBankTransaction transaction = accountingBankTransactionService
                                .createAccountingBankTransaction(request);
                ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                                AccountingBankTransactionMessage.CREATE_ACCOUNTING_BANK_TRANSACTION, 201);
                response.setRequestInfo(transaction);
                return new ResponseEntity<>(response, HttpStatus.CREATED);
        }

        @GetMapping
        public ResponseEntity<ApiResponse> viewAllAccountingBankTransactions() {
                List<AccountingBankTransaction> transaction = accountingBankTransactionService.viewAllTransactions();
                ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                                AccountingBankTransactionMessage.VIEW_ALL_ACCOUNTING_BANK_TRANSACTION, 200);
                response.setData(transaction);
                return new ResponseEntity<>(response, HttpStatus.OK);
        }
}
