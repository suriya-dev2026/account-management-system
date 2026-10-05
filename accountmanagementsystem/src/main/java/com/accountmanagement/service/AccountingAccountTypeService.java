package com.accountmanagement.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.accountmanagement.constants.message.AccountingAccountTypeMessage;
import com.accountmanagement.exceptions.DuplicateRecordException;
import com.accountmanagement.exceptions.RecordNotFoundException;
import com.accountmanagement.model.AccountingAccountType;
import com.accountmanagement.repository.AccountingAccountTypeRepository;
import com.accountmanagement.request.AccountingAccountTypeRequest;

@Service
public class AccountingAccountTypeService {

    private final AccountingAccountTypeRepository accountingAccountTypeRepository;

    public AccountingAccountTypeService(AccountingAccountTypeRepository accountingAccountTypeRepository) {
        this.accountingAccountTypeRepository = accountingAccountTypeRepository;
    }

    public AccountingAccountType createAccountingAccountType(AccountingAccountTypeRequest accountTypeRequest) {
        validateTypeName(accountTypeRequest.getOrganizationId(),
                accountTypeRequest.getTypeName());
        AccountingAccountType accountingAccountType = new AccountingAccountType();
        accountingAccountType.setOrganizationId(accountTypeRequest.getOrganizationId());
        accountingAccountType.setTypeName(accountTypeRequest.getTypeName());
        return accountingAccountTypeRepository.save(accountingAccountType);
    }

    public AccountingAccountType updateAccountingAccountType(Integer id,
            AccountingAccountTypeRequest accountTypeRequest) {
        AccountingAccountType accountingAccountType = findAccountTypeById(id);
        accountingAccountType.setOrganizationId(accountTypeRequest.getOrganizationId());
        accountingAccountType.setTypeName(accountTypeRequest.getTypeName());
        return accountingAccountTypeRepository.save(accountingAccountType);
    }

    public void deleteAccountTypeById(Integer id) {
        findAccountTypeById(id);
        accountingAccountTypeRepository.deleteById(id);
    }

    public List<AccountingAccountType> viewAllAccountingAccountTypes() {
        return accountingAccountTypeRepository.findAll();
    }

    public AccountingAccountType findAccountTypeById(Integer id) {
        return accountingAccountTypeRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(""));
    }

    public void validateTypeName(UUID organizationId, String typeName) {
        boolean exists = accountingAccountTypeRepository.existsByOrganizationIdAndTypeName(organizationId, typeName);
        if (exists) {
            throw new DuplicateRecordException(AccountingAccountTypeMessage.ACCOUNTING_ACCOUNT_TYPE_EXISTS);
        }
    }
}
