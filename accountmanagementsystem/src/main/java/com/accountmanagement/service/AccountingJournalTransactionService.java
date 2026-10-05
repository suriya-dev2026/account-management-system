package com.accountmanagement.service;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.accountmanagement.model.AccountingJournalTransaction;
import com.accountmanagement.repository.AccountingJournalTransactionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountingJournalTransactionService {

    private final AccountingJournalTransactionRepository accountingJournalTransactionRepository;

    @Transactional
    public AccountingJournalTransaction createTransaction(UUID organizationId, UUID journalEntryId,
            String journalEntryTrackingId, UUID accountId, BigDecimal debit, BigDecimal credit,
            String remarks) {

        AccountingJournalTransaction transaction = new AccountingJournalTransaction();
        transaction.setOrganizationId(organizationId);
        transaction.setJournalEntryId(journalEntryId);
        transaction.setJournalEntryTrackingId(journalEntryTrackingId);
        transaction.setAccountId(accountId);
        transaction.setDebit(debit);
        transaction.setCredit(credit);
        transaction.setRemarks(remarks);
        return accountingJournalTransactionRepository.save(transaction);
    }
}
