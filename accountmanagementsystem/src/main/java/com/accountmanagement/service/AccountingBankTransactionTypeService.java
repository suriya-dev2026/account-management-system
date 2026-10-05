package com.accountmanagement.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.accountmanagement.constants.message.AccountingBankTransactionTypeMessage;
import com.accountmanagement.exceptions.DuplicateRecordException;
import com.accountmanagement.exceptions.RecordNotFoundException;
import com.accountmanagement.model.AccountingBankTransactionType;
import com.accountmanagement.repository.AccountingBankTransactionTypeRepository;
import com.accountmanagement.request.AccountingBankTransactionTypeRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountingBankTransactionTypeService {

    private final AccountingBankTransactionTypeRepository accountingBankTransactionTypeRepository;

    public AccountingBankTransactionType createAccountingBankTransactionType(
            AccountingBankTransactionTypeRequest request) {
        validateTypeName(request.getOrganizationId(), request.getTypeName());
        AccountingBankTransactionType transactionType = new AccountingBankTransactionType();
        transactionType.setOrganizationId(request.getOrganizationId());
        transactionType.setTypeName(request.getTypeName());
        return accountingBankTransactionTypeRepository.save(transactionType);
    }

    public AccountingBankTransactionType updateAccountingBankTransactionTypeById(Integer id,
            AccountingBankTransactionTypeRequest request) {
        AccountingBankTransactionType transactionType = findAccountingBankTransactionTypeById(id);
        transactionType.setTypeName(request.getTypeName());
        return accountingBankTransactionTypeRepository.save(transactionType);
    }

    public void deleteAccountingBankTransactionType(Integer id) {
        findAccountingBankTransactionTypeById(id);
        accountingBankTransactionTypeRepository.deleteById(id);
    }

    public List<AccountingBankTransactionType> viewAllAccountingBankTransactionType() {
        return accountingBankTransactionTypeRepository.findAll();
    }

    public void validateTypeName(UUID organizationId, String typeName) {
        boolean exists = accountingBankTransactionTypeRepository.existsByOrganizationIdAndTypeName(organizationId,
                typeName);
        if (exists) {
            throw new DuplicateRecordException(
                    AccountingBankTransactionTypeMessage.ACCOUNTING_BANK_TRANSACTION_TYPE_EXISTS);
        }
    }

    public AccountingBankTransactionType findAccountingBankTransactionTypeById(Integer id) {
        return accountingBankTransactionTypeRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(
                AccountingBankTransactionTypeMessage.ACCOUNTING_BANK_TRANSACTION_TYPE_ID_NOT_FOUND));
    }
}
