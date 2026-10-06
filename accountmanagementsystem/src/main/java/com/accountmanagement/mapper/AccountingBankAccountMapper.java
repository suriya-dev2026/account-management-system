package com.accountmanagement.mapper;

import org.springframework.stereotype.Component;

import com.accountmanagement.model.AccountingBankAccount;
import com.accountmanagement.request.AccountingBankAccountRequest;
import com.accountmanagement.request.AccountingBankAccountUpdateRequest;

@Component
public class AccountingBankAccountMapper {

    public AccountingBankAccount toCreateAccountingBankAccount(AccountingBankAccountRequest request) {
        AccountingBankAccount accountingBankAccount = new AccountingBankAccount();
        accountingBankAccount.setOrganizationId(request.getOrganizationId());
        accountingBankAccount.setBankAccountTypeId(request.getBankAccountTypeId());
        accountingBankAccount.setAccountId(request.getAccountId());
        accountingBankAccount.setBankName(request.getBankName());
        accountingBankAccount.setBranch(request.getBranch());
        accountingBankAccount.setAccountNumber(request.getAccountNumber());
        accountingBankAccount.setIfsc(request.getIfsc());
        accountingBankAccount.setAccountHolderName(request.getAccountHolderName());
        accountingBankAccount.setOpeningBalance(request.getOpeningBalance());
        accountingBankAccount.setCurrentBalanace(request.getOpeningBalance());
        return accountingBankAccount;
    }

    public AccountingBankAccount toUpdateAccountingBankAccount(AccountingBankAccount accountingBankAccount,
            AccountingBankAccountUpdateRequest request) {
        accountingBankAccount.setBankAccountTypeId(request.getBankAccountTypeId());
        accountingBankAccount.setAccountId(request.getAccountId());
        accountingBankAccount.setBankName(request.getBankName());
        accountingBankAccount.setBranch(request.getBranch());
        accountingBankAccount.setAccountNumber(request.getAccountNumber());
        accountingBankAccount.setIfsc(request.getIfsc());
        accountingBankAccount.setAccountHolderName(request.getAccountHolderName());
        accountingBankAccount.setOpeningBalance(request.getOpeningBalance());
        accountingBankAccount.setCurrentBalanace(request.getCurrentBalance());
        return accountingBankAccount;
    }
}
