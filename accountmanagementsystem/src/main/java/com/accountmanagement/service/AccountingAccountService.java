package com.accountmanagement.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.constants.message.AccountingAccountMessage;
import com.accountmanagement.constants.message.AccountingAccountTypeMessage;
import com.accountmanagement.exceptions.DuplicateRecordException;
import com.accountmanagement.exceptions.InvalidSessionException;
import com.accountmanagement.exceptions.RecordNotFoundException;
import com.accountmanagement.model.AccountingAccount;
import com.accountmanagement.repository.AccountingAccountRepository;
import com.accountmanagement.repository.AccountingAccountTypeRepository;
import com.accountmanagement.request.AccountingAccountRequest;

@Service
public class AccountingAccountService {

    private final AccountingAccountRepository accountingAccountRepository;

    private final AccountingAccountTypeRepository accountingAccountTypeRepository;

    public AccountingAccountService(AccountingAccountRepository accountingAccountRepository,
            AccountingAccountTypeRepository accountingAccountTypeRepository) {
        this.accountingAccountRepository = accountingAccountRepository;
        this.accountingAccountTypeRepository = accountingAccountTypeRepository;
    }

    public AccountingAccount createAccountingAccount(AccountingAccountRequest accountingAccountRequest) {
        validateAccountType(accountingAccountRequest.getAccountTypeId());
        validateAccountName(accountingAccountRequest);
        AccountingAccount accountingAccount = new AccountingAccount();
        String accountCode = generateAccountCode(accountingAccountRequest.getOrganizationId(),
                accountingAccountRequest.getAccountTypeId());
        accountingAccount.setOrganizationId(accountingAccountRequest.getOrganizationId());
        accountingAccount.setAccountCode(accountCode);
        accountingAccount.setAccountName(accountingAccountRequest.getAccountName());
        accountingAccount.setAccountTypeId(accountingAccountRequest.getAccountTypeId());
        accountingAccount.setParentAccountId(accountingAccountRequest.getParentAccountId());
        return accountingAccountRepository.save(accountingAccount);
    }

    public AccountingAccount updateAccountingAccount(UUID id, AccountingAccountRequest accountingAccountRequest) {
        AccountingAccount accountingAccount = findAccountingAccountById(id);
        accountingAccount.setOrganizationId(accountingAccountRequest.getOrganizationId());
        accountingAccount.setAccountName(accountingAccountRequest.getAccountName());
        accountingAccount.setAccountTypeId(accountingAccountRequest.getAccountTypeId());
        accountingAccount.setParentAccountId(accountingAccountRequest.getParentAccountId());
        return accountingAccountRepository.save(accountingAccount);
    }

    public void deleteAccountingAccount(UUID id) {
        AccountingAccount accountingAccount = findAccountingAccountById(id);
        accountingAccount.setStatus(AppConstants.INACTIVE);
        accountingAccountRepository.save(accountingAccount);
    }

    public List<AccountingAccount> viewAllAccountingAccounts() {
        return accountingAccountRepository.findAll();
    }

    public AccountingAccount findAccountingAccountById(UUID id) {
        return accountingAccountRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("null"));
    }

    public Boolean validateAccountType(Integer accountTypeId) {
        boolean exists = accountingAccountTypeRepository.existsById(accountTypeId);
        if (!exists) {
            throw new RecordNotFoundException(AccountingAccountTypeMessage.ACCOUNTING_ACCOUNT_TYPE_ID_NOT_FOUND);
        }
        return exists;
    }

    public void validateAccountName(AccountingAccountRequest accountingAccountRequest) {
        boolean exists = accountingAccountRepository.existsByOrganizationIdAndAccountName(
                accountingAccountRequest.getOrganizationId(), accountingAccountRequest.getAccountName());
        if (exists) {
            throw new DuplicateRecordException(AccountingAccountMessage.ACCOUNTING_ACCOUNT_EXISTS);
        }
    }

    private String generateAccountCode(UUID organizationId, Integer accountTypeId) {
        int baseCode;
        switch (accountTypeId) {
            case 1 -> baseCode = 1000;
            case 2 -> baseCode = 2000;
            case 3 -> baseCode = 3000;
            case 4 -> baseCode = 4000;
            case 5 -> baseCode = 5000;
            default -> throw new InvalidSessionException(
                    "Invalid account type.");
        }
        Optional<AccountingAccount> account = accountingAccountRepository
                .findTopByOrganizationIdAndAccountTypeIdOrderByAccountCodeDesc(
                        organizationId,
                        accountTypeId);
        if (account.isEmpty()) {
            return String.valueOf(baseCode + 1);
        }
        int lastCode = Integer.parseInt(account.get().getAccountCode());
        return String.valueOf(lastCode + 1);
    }

    public AccountingAccount getAccountByIdAndOrganization(UUID accountId, UUID organizationId) {
        return accountingAccountRepository.findByIdAndOrganizationId(accountId, organizationId)
                .orElseThrow(() -> new RecordNotFoundException("Accounting account not found."));
    }
}
