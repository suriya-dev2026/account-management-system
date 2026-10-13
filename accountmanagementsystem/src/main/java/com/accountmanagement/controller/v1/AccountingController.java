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
import com.accountmanagement.constants.message.AccountingAccountTypeMessage;
import com.accountmanagement.constants.message.AccountingBankAccountMessage;
import com.accountmanagement.constants.message.AccountingBankAccountTypeMessage;
import com.accountmanagement.constants.message.AccountingBankTransactionMessage;
import com.accountmanagement.constants.message.AccountingBankTransactionTypeMessage;
import com.accountmanagement.model.AccountingAccount;
import com.accountmanagement.model.AccountingAccountType;
import com.accountmanagement.model.AccountingBankAccount;
import com.accountmanagement.model.AccountingBankAccountType;
import com.accountmanagement.model.AccountingBankTransaction;
import com.accountmanagement.model.AccountingBankTransactionType;
import com.accountmanagement.request.AccountingAccountRequest;
import com.accountmanagement.request.AccountingAccountTypeRequest;
import com.accountmanagement.request.AccountingBankAccountRequest;
import com.accountmanagement.request.AccountingBankAccountTypeRequest;
import com.accountmanagement.request.AccountingBankAccountUpdateRequest;
import com.accountmanagement.request.AccountingBankTransactionRequest;
import com.accountmanagement.request.AccountingBankTransactionTypeRequest;
import com.accountmanagement.response.ApiResponse;
import com.accountmanagement.service.AccountingAccountService;
import com.accountmanagement.service.AccountingAccountTypeService;
import com.accountmanagement.service.AccountingBankAccountService;
import com.accountmanagement.service.AccountingBankAccountTypeService;
import com.accountmanagement.service.AccountingBankTransactionService;
import com.accountmanagement.service.AccountingBankTransactionTypeService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "v1/accounting")
@Tag(name = "AccountingController")
@RequiredArgsConstructor
public class AccountingController {

        private final AccountingAccountTypeService accountingAccountTypeService;

        private final AccountingAccountService accountingAccountService;

        private final AccountingBankAccountService accountingBankAccountService;

        private final AccountingBankAccountTypeService accountingBankAccountTypeService;

        private final AccountingBankTransactionService accountingBankTransactionService;

        private final AccountingBankTransactionTypeService accountingBankTransactionTypeService;

        @PostMapping("/type/create")
        public ResponseEntity<ApiResponse> createAccountingAccountType(
                        @Valid @RequestBody AccountingAccountTypeRequest accountingAccountTypeRequest) {
                accountingAccountTypeRequest.sanitizeInput();
                accountingAccountTypeService.createAccountingAccountType(accountingAccountTypeRequest);
                ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                                AccountingAccountTypeMessage.CREATE_ACCOUNTING_ACCOUNT_TYPE, 201);
                return new ResponseEntity<>(response, HttpStatus.CREATED);
        }

        @PutMapping("/type/update/{id}")
        public ResponseEntity<ApiResponse> updateAccountingAccountType(@PathVariable Integer id,
                        @Valid @RequestBody AccountingAccountTypeRequest accountingAccountTypeRequest) {
                accountingAccountTypeRequest.sanitizeInput();
                accountingAccountTypeService.updateAccountingAccountType(id, accountingAccountTypeRequest);
                ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                                AccountingAccountTypeMessage.UPDATE_ACCOUNTING_ACCOUNT_TYPE, 200);
                return new ResponseEntity<>(response, HttpStatus.OK);
        }

        @DeleteMapping("/type/delete/{id}")
        public ResponseEntity<ApiResponse> deleteAccountingAccountTypeById(@PathVariable Integer id) {
                accountingAccountTypeService.deleteAccountTypeById(id);
                ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                                AccountingAccountTypeMessage.DELETE_ACCOUNTING_ACCOUNT_TYPE, 200);
                return new ResponseEntity<>(response, HttpStatus.OK);
        }

        @GetMapping("/type/view")
        public ResponseEntity<ApiResponse> viewAllAccountingAccountTypes() {
                List<AccountingAccountType> accountingAccountType = accountingAccountTypeService
                                .viewAllAccountingAccountTypes();
                ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                                AccountingAccountTypeMessage.VIEW_ALL_ACCOUNTING_ACCOUNT_TYPES, 200);
                response.setData(accountingAccountType);
                return new ResponseEntity<>(response, HttpStatus.OK);
        }

        @PostMapping("/account/create")
        public ResponseEntity<ApiResponse> createAccountingAccount(
                        @Valid @RequestBody AccountingAccountRequest accountingAccountRequest) {
                accountingAccountRequest.sanitizeInput();
                accountingAccountService.createAccountingAccount(accountingAccountRequest);
                ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                                AccountingAccountMessage.CREATE_ACCOUNTING_ACCOUNT,
                                201);
                return new ResponseEntity<>(response, HttpStatus.CREATED);
        }

        @PutMapping("/account/update/{id}")
        public ResponseEntity<ApiResponse> updateAccountingAccountById(@PathVariable UUID id,
                        @Valid @RequestBody AccountingAccountRequest accountingAccountRequest) {
                accountingAccountRequest.sanitizeInput();
                accountingAccountService.updateAccountingAccount(id, accountingAccountRequest);
                ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                                AccountingAccountMessage.UPDATE_ACCOUNTING_ACCOUNT,
                                200);
                return new ResponseEntity<>(response, HttpStatus.OK);
        }

        @DeleteMapping("/account/delete/{id}")
        public ResponseEntity<ApiResponse> deleteAccountingAccountById(@PathVariable UUID id) {
                accountingAccountService.deleteAccountingAccount(id);
                ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                                AccountingAccountMessage.DELETE_ACCOUNTING_ACCOUNT,
                                200);
                return new ResponseEntity<>(response, HttpStatus.OK);
        }

        @GetMapping("/account/view")
        public ResponseEntity<ApiResponse> viewAllAccountingAccount() {
                List<AccountingAccount> accountingAccount = accountingAccountService.viewAllAccountingAccounts();
                ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                                AccountingAccountMessage.VIEW_ALL_ACCOUNTING_ACCOUNTS, 200);
                response.setData(accountingAccount);
                return new ResponseEntity<>(response, HttpStatus.OK);
        }

        @PostMapping("/bank/account/create")
        public ResponseEntity<ApiResponse> createAccountingBankAccount(
                        @Valid @RequestBody AccountingBankAccountRequest accountingBankAccountRequest) {
                accountingBankAccountRequest.sanitizeInput();
                accountingBankAccountService.createAccountingBankAccount(accountingBankAccountRequest);
                ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                                AccountingBankAccountMessage.CREATE_ACCOUNTING_BANK_ACCOUNT, 201);
                return new ResponseEntity<>(response, HttpStatus.CREATED);
        }

        @PutMapping("/bank/account/update/{id}")
        public ResponseEntity<ApiResponse> updateAccountingBankAccount(@PathVariable UUID id,
                        @Valid @RequestBody AccountingBankAccountUpdateRequest request) {
                request.sanitizeInput();
                accountingBankAccountService.updateBankAccountById(id, request);
                ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                                AccountingBankAccountMessage.UPDATE_ACCOUNTING_BANK_ACCOUNT, 200);
                return new ResponseEntity<>(response, HttpStatus.OK);
        }

        @DeleteMapping("/bank/account/delete/{id}")
        public ResponseEntity<ApiResponse> deleteAccountingBankAccount(@PathVariable UUID id) {
                accountingBankAccountService.deleteBankAccountById(id);
                ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                                AccountingBankAccountMessage.DELETE_ACCOUNTING_BANK_ACCOUNT, 200);
                return new ResponseEntity<>(response, HttpStatus.OK);
        }

        @GetMapping("/bank/account/view")
        public ResponseEntity<ApiResponse> viewAllAccountingBankAccount() {
                List<AccountingBankAccount> account = accountingBankAccountService.viewAllBankAccounts();
                ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                                AccountingBankAccountMessage.VIEW_ALL_ACCOUNTING_BANK_ACCOUNT, 200);
                response.setData(account);
                return new ResponseEntity<>(response, HttpStatus.OK);
        }

        @PostMapping("/bank/account/type/create")
        public ResponseEntity<ApiResponse> createAccountingBankAccount(
                        @Valid @RequestBody AccountingBankAccountTypeRequest accountingBankAccountTypeRequest) {
                accountingBankAccountTypeRequest.sanitizeInput();
                accountingBankAccountTypeService.createAccountingBankAccountType(accountingBankAccountTypeRequest);
                ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                                AccountingBankAccountTypeMessage.CREATE_ACCOUNTING_BANK_ACCOUNT_TYPE, 201);
                return new ResponseEntity<>(response, HttpStatus.CREATED);
        }

        @PutMapping("/bank/account/type/update/{id}")
        public ResponseEntity<ApiResponse> updateAccountingBankAccountTypeById(@PathVariable Integer id,
                        @Valid @RequestBody AccountingBankAccountTypeRequest accountingBankAccountTypeRequest) {
                accountingBankAccountTypeRequest.sanitizeInput();
                accountingBankAccountTypeService.updateAccountingBankAccountTypeById(id,
                                accountingBankAccountTypeRequest);
                ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                                AccountingBankAccountTypeMessage.UPDATE_ACCOUNTING_BANK_ACCOUNT_TYPE, 200);
                return new ResponseEntity<>(response, HttpStatus.OK);
        }

        @DeleteMapping("/bank/account/type/delete/{id}")
        public ResponseEntity<ApiResponse> deleteAccountingBankAccountTypeById(@PathVariable Integer id) {
                accountingBankAccountTypeService.deleteAccountingBankAccounttypeById(id);
                ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                                AccountingBankAccountTypeMessage.DELETE_ACCOUNTING_BANK_ACCOUNT_TYPE, 200);
                return new ResponseEntity<>(response, HttpStatus.OK);
        }

        @GetMapping("/bank/account/type/view")
        public ResponseEntity<ApiResponse> viewAllAccountingBankAccountTypes() {
                List<AccountingBankAccountType> account = accountingBankAccountTypeService
                                .viewAllAccountingBankAccountType();
                ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                                AccountingBankAccountTypeMessage.VIEW_ALL_ACCOUNTING_BANK_ACCOUNT_TYPES, 200);
                response.setData(account);
                return new ResponseEntity<>(response, HttpStatus.OK);
        }

        @PostMapping("/bank/transaction/create")
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

        @GetMapping("/bank/transaction/view")
        public ResponseEntity<ApiResponse> viewAllAccountingBankTransactions() {
                List<AccountingBankTransaction> transaction = accountingBankTransactionService.viewAllTransactions();
                ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                                AccountingBankTransactionMessage.VIEW_ALL_ACCOUNTING_BANK_TRANSACTION, 200);
                response.setData(transaction);
                return new ResponseEntity<>(response, HttpStatus.OK);
        }

        @PostMapping("/bank/transaction/type/create")
        public ResponseEntity<ApiResponse> createAccountingBankTransactionType(
                        @Valid @RequestBody AccountingBankTransactionTypeRequest request) {
                request.sanitizeInput();
                accountingBankTransactionTypeService.createAccountingBankTransactionType(request);
                ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                                AccountingBankTransactionTypeMessage.CREATE_ACCOUNTING_BANK_TRANSACTION_TYPE, 201);
                return new ResponseEntity<>(response, HttpStatus.CREATED);
        }

        @PutMapping("/bank/transaction/type/update/{id}")
        public ResponseEntity<ApiResponse> updateAccountingBankTransactionType(@PathVariable Integer id,
                        @Valid @RequestBody AccountingBankTransactionTypeRequest request) {
                request.sanitizeInput();
                accountingBankTransactionTypeService.updateAccountingBankTransactionTypeById(id, request);
                ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                                AccountingBankTransactionTypeMessage.UPDATE_ACCOUNTING_BANK_TRANSACTION_TYPE, 200);
                return new ResponseEntity<>(response, HttpStatus.OK);
        }

        @DeleteMapping("/bank/transaction/type/delete/{id}")
        public ResponseEntity<ApiResponse> deleteAccountingBankTransactionTypeById(@PathVariable Integer id) {
                accountingBankTransactionTypeService.deleteAccountingBankTransactionType(id);
                ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                                AccountingBankTransactionTypeMessage.DELETE_ACCOUNTING_BANK_TRANSACTION_TYPE, 200);
                return new ResponseEntity<>(response, HttpStatus.OK);
        }

        @GetMapping("/bank/transaction/type/view")
        public ResponseEntity<ApiResponse> viewAllAccountingBankTransactionTypes() {
                List<AccountingBankTransactionType> transactionType = accountingBankTransactionTypeService
                                .viewAllAccountingBankTransactionType();
                ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                                AccountingBankTransactionTypeMessage.VIEW_ALL_ACCOUNTING_BANK_TRANSACTION_TYPES, 200);
                response.setData(transactionType);
                return new ResponseEntity<>(response, HttpStatus.OK);
        }
}
