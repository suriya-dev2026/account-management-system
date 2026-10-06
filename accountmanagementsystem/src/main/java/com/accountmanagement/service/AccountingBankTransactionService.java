package com.accountmanagement.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.accountmanagement.constants.message.AccountingBankAccountMessage;
import com.accountmanagement.constants.message.AccountingBankTransactionTypeMessage;
import com.accountmanagement.enums.Direction;
import com.accountmanagement.exceptions.InvalidSessionException;
import com.accountmanagement.exceptions.RecordNotFoundException;
import com.accountmanagement.model.AccountingAccount;
import com.accountmanagement.model.AccountingBankAccount;
import com.accountmanagement.model.AccountingBankTransaction;
import com.accountmanagement.model.AccountingBankTransactionType;
import com.accountmanagement.model.AccountingJournalEntry;
import com.accountmanagement.repository.AccountingBankAccountRepository;
import com.accountmanagement.repository.AccountingBankTransactionRepository;
import com.accountmanagement.repository.AccountingBankTransactionTypeRepository;
import com.accountmanagement.request.AccountingBankTransactionRequest;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountingBankTransactionService {

    private final AccountingBankTransactionRepository accountingBankTransactionRepository;

    private final AccountingBankAccountRepository accountingBankAccountRepository;

    private final AccountingBankTransactionTypeRepository accountingBankTransactionTypeRepository;

    private final AccountingJournalEntryService accountingJournalEntryService;

    private final AccountingJournalTransactionService accountingJournalTransactionService;

    private final AccountingAccountService accountingAccountService;

    @Transactional
    public AccountingBankTransaction createAccountingBankTransaction(AccountingBankTransactionRequest request) {
        AccountingBankAccount bankAccount = validateBankAccountId(request.getBankAccountId(),
                request.getOrganizationId());
        AccountingBankTransactionType transactionType = validateTransactionTypeId(
                request.getTransactionTypeId(), request.getOrganizationId());
        validateAmount(request.getAmount());
        AccountingJournalEntry journalEntry = accountingJournalEntryService
                .createJournalEntry(request.getOrganizationId(), request.getTransactionDate(), request.getRemarks());
        String trackingId = journalEntry.getTrackingId();
        createBankTransactions(bankAccount, transactionType, request.getOrganizationId(), journalEntry.getId(),
                trackingId, request.getAmount(), request.getRemarks());
        return bankTransaction(request, bankAccount.getId(), transactionType.getId(), journalEntry.getId(), trackingId);
    }

    private void createBankTransactions(AccountingBankAccount bankAccount,
            AccountingBankTransactionType transactionType, UUID organizationId,
            UUID journalEntryId, String trackingId, BigDecimal amount, String remarks) {
        AccountingAccount bankAccountingAccount = accountingAccountService.getAccountByIdAndOrganization(
                bankAccount.getAccountId(),
                organizationId);
        AccountingAccount transactionAccount = accountingAccountService.getAccountByIdAndOrganization(
                transactionType.getAccountId(),
                organizationId);
        if (Direction.DEBIT.equals(transactionType.getDirection())) {
            accountingJournalTransactionService.createTransaction(organizationId, journalEntryId, trackingId,
                    bankAccountingAccount.getId(), amount,
                    BigDecimal.ZERO, remarks);
            accountingJournalTransactionService.createTransaction(organizationId, journalEntryId, trackingId,
                    transactionAccount.getId(),
                    BigDecimal.ZERO, amount, remarks);
        } else if (Direction.CREDIT.equals(transactionType.getDirection())) {
            accountingJournalTransactionService.createTransaction(organizationId, journalEntryId, trackingId,
                    transactionAccount.getId(),
                    amount, BigDecimal.ZERO, remarks);
            accountingJournalTransactionService.createTransaction(organizationId, journalEntryId, trackingId,
                    bankAccountingAccount.getId(), BigDecimal.ZERO,
                    amount, remarks);
        }
    }

    private void validateAmount(BigDecimal amount) {
        if (amount == null ||
                amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidSessionException(
                    "Amount must be greater than zero.");
        }
    }

    private AccountingBankAccount validateBankAccountId(UUID bankAccountId, UUID organizationId) {
        return accountingBankAccountRepository.findByIdAndOrganizationId(bankAccountId, organizationId).orElseThrow(
                () -> new RecordNotFoundException(AccountingBankAccountMessage.ACCOUNTING_BANK_ACCOUNT_ID_NOT_FOUND));
    }

    private AccountingBankTransactionType validateTransactionTypeId(Integer transactionTypeId, UUID organizationId) {
        return accountingBankTransactionTypeRepository.findByIdAndOrganizationId(transactionTypeId, organizationId)
                .orElseThrow(
                        () -> new RecordNotFoundException(
                                AccountingBankTransactionTypeMessage.ACCOUNTING_BANK_TRANSACTION_TYPE_ID_NOT_FOUND));
    }

    private AccountingBankTransaction bankTransaction(AccountingBankTransactionRequest request, UUID bankAccountId,
            Integer transactionTypeId,
            UUID journalEntryId, String trackingId) {
        AccountingBankTransaction bankTransaction = new AccountingBankTransaction();
        bankTransaction.setOrganizationId(request.getOrganizationId());
        bankTransaction.setBankAccountId(bankAccountId);
        bankTransaction.setJournalEntryId(journalEntryId);
        bankTransaction.setJournalEntryTrackingId(trackingId);
        bankTransaction.setTransactionTypeId(transactionTypeId);
        bankTransaction.setReference(request.getReference());
        bankTransaction.setAmount(request.getAmount());
        bankTransaction.setTransactionDate(request.getTransactionDate());
        bankTransaction.setRemarks(request.getRemarks());
        return accountingBankTransactionRepository.save(bankTransaction);
    }

    public List<AccountingBankTransaction> viewAllTransactions() {
        return accountingBankTransactionRepository.findAll();
    }
}
