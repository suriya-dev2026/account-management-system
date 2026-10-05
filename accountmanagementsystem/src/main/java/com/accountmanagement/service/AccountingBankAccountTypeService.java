package com.accountmanagement.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.accountmanagement.constants.message.AccountingBankAccountTypeMessage;
import com.accountmanagement.exceptions.DuplicateRecordException;
import com.accountmanagement.exceptions.RecordNotFoundException;
import com.accountmanagement.model.AccountingBankAccountType;
import com.accountmanagement.repository.AccountingBankAccountTypeRepository;
import com.accountmanagement.request.AccountingBankAccountTypeRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountingBankAccountTypeService {

    private final AccountingBankAccountTypeRepository accountingBankAccountTypeRepository;

    public AccountingBankAccountType createAccountingBankAccountType(
            AccountingBankAccountTypeRequest accountingBankAccountTypeRequest) {
        validateAccountType(accountingBankAccountTypeRequest.getOrganizationId(),
                accountingBankAccountTypeRequest.getTypeName());
        AccountingBankAccountType accountingBankAccountType = new AccountingBankAccountType();
        accountingBankAccountType.setOrganizationId(accountingBankAccountTypeRequest.getOrganizationId());
        accountingBankAccountType.setTypeName(accountingBankAccountTypeRequest.getTypeName());
        return accountingBankAccountTypeRepository.save(accountingBankAccountType);
    }

    public AccountingBankAccountType updateAccountingBankAccountTypeById(Integer id,
            AccountingBankAccountTypeRequest accountingBankAccountTypeRequest) {
        AccountingBankAccountType accountType = findAccountingBankAccountTypeById(id);
        accountType.setTypeName(accountingBankAccountTypeRequest.getTypeName());
        return accountingBankAccountTypeRepository.save(accountType);
    }

    public void deleteAccountingBankAccounttypeById(Integer id) {
        findAccountingBankAccountTypeById(id);
        accountingBankAccountTypeRepository.deleteById(id);
    }

    public List<AccountingBankAccountType> viewAllAccountingBankAccountType() {
        return accountingBankAccountTypeRepository.findAll();
    }

    public AccountingBankAccountType findAccountingBankAccountTypeById(Integer id) {
        return accountingBankAccountTypeRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(
                AccountingBankAccountTypeMessage.ACCOUNTING_BANK_ACCOUNT_TYPE_ID_NOT_FOUND));
    }

    private void validateAccountType(UUID organizationId, String typeName) {
        boolean exists = accountingBankAccountTypeRepository
                .existsByOrganizationIdAndTypeName(organizationId, typeName);
        if (exists) {
            throw new DuplicateRecordException(AccountingBankAccountTypeMessage.ACCOUNTING_BANK_ACCOUNT_TYPE_EXISTS);
        }
    }
}
