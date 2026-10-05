package com.accountmanagement.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.constants.message.AccountingBankAccountMessage;
import com.accountmanagement.constants.message.AccountingBankAccountTypeMessage;
import com.accountmanagement.exceptions.DuplicateRecordException;
import com.accountmanagement.exceptions.RecordNotFoundException;
import com.accountmanagement.mapper.AccountingBankAccountMapper;
import com.accountmanagement.model.AccountingBankAccount;
import com.accountmanagement.repository.AccountingBankAccountRepository;
import com.accountmanagement.repository.AccountingBankAccountTypeRepository;
import com.accountmanagement.request.AccountingBankAccountRequest;
import com.accountmanagement.request.AccountingBankAccountUpdateRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountingBankAccountService {

    private final AccountingBankAccountRepository accountingBankAccountRepository;

    private final AccountingBankAccountTypeRepository accountingBankAccountTypeRepository;

    private final AccountingBankAccountMapper accountingBankAccountMapper;

    public AccountingBankAccount createAccountingBankAccount(AccountingBankAccountRequest request) {
        validateBankAccount(request.getOrganizationId(), request.getAccountNumber());
        validateBankAccountTypeId(request.getBankAccountTypeId(), request.getOrganizationId());
        AccountingBankAccount account = accountingBankAccountMapper.toCreateAccountingBankAccount(request);
        return accountingBankAccountRepository.save(account);
    }

    public AccountingBankAccount updateBankAccountById(UUID id, AccountingBankAccountUpdateRequest request) {
        AccountingBankAccount accountingBankAccount = findAccountingBankAccount(id);
        AccountingBankAccount update = accountingBankAccountMapper.toUpdateAccountingBankAccount(accountingBankAccount,
                request);
        return accountingBankAccountRepository.save(update);
    }

    public void deleteBankAccountById(UUID id) {
        AccountingBankAccount account = findAccountingBankAccount(id);
        account.setStatus(AppConstants.INACTIVE);
        accountingBankAccountRepository.save(account);
    }

    public List<AccountingBankAccount> viewAllBankAccounts() {
        return accountingBankAccountRepository.findAll();
    }

    public AccountingBankAccount findAccountingBankAccount(UUID id) {
        return accountingBankAccountRepository.findById(id).orElseThrow(
                () -> new RecordNotFoundException(AccountingBankAccountMessage.ACCOUNTING_BANK_ACCOUNT_ID_NOT_FOUND));
    }

    public void validateBankAccount(UUID organizationId, String accountNumber) {
        boolean exists = accountingBankAccountRepository.existsByOrganizationIdAndAccountNumber(organizationId,
                accountNumber);
        if (exists) {
            throw new DuplicateRecordException(AccountingBankAccountMessage.ACCOUNTING_BANK_ACCOUNT_EXISTS);
        }
    }

    private void validateBankAccountTypeId(Integer bankAccountTypeId, UUID organizationId) {
        boolean exists = accountingBankAccountTypeRepository.existsByIdAndOrganizationId(bankAccountTypeId,
                organizationId);
        if (!exists) {
            throw new RecordNotFoundException(
                    AccountingBankAccountTypeMessage.ACCOUNTING_BANK_ACCOUNT_TYPE_ID_NOT_FOUND);
        }
    }

}
