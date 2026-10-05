package com.accountmanagement.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.accountmanagement.constants.message.OrganizationExpenseTypeMessage;
import com.accountmanagement.constants.message.OrganizationPaymentMethodMessage;
import com.accountmanagement.exceptions.InvalidSessionException;
import com.accountmanagement.exceptions.RecordNotFoundException;
import com.accountmanagement.model.AccountingAccount;
import com.accountmanagement.model.AccountingJournalEntry;
import com.accountmanagement.model.OrganizationExpense;
import com.accountmanagement.model.OrganizationExpenseType;
import com.accountmanagement.model.OrganizationPaymentMethod;
import com.accountmanagement.repository.OrganizationExpenseRepository;
import com.accountmanagement.repository.OrganizationExpenseTypeRepository;
import com.accountmanagement.repository.OrganizationPaymentMethodRepository;
import com.accountmanagement.request.OrganizationExpenseRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrganizationExpenseService {

        private final OrganizationExpenseRepository organizationExpenseRepository;

        private final OrganizationExpenseTypeRepository organizationExpenseTypeRepository;

        private final OrganizationPaymentMethodRepository organizationPaymentMethodRepository;

        private final AccountingAccountService accountingAccountService;

        private final AccountingJournalTransactionService accountingJournalTransactionService;

        private final AccountingJournalEntryService accountingJournalEntryService;

        @Transactional
        public OrganizationExpense createOrganizationExpense(OrganizationExpenseRequest request) {
                OrganizationExpenseType expenseType = getExpenseType(request.getExpenseTypeId(),
                                request.getOrganizationId());
                OrganizationPaymentMethod paymentMethod = getPaymentMethod(request.getPaymentMethodId(),
                                request.getOrganizationId());
                validateAmount(request.getAmount());
                String trackingId = accountingJournalEntryService.generateJournalTrackingId(
                                request.getOrganizationId(), request.getExpenseDate());
                AccountingJournalEntry journalEntry = accountingJournalEntryService
                                .createJournalEntry(request.getOrganizationId(), request.getExpenseDate(),
                                                request.getRemarks());
                createExpenseDebitTransaction(expenseType, request.getOrganizationId(), journalEntry.getId(),
                                trackingId, request.getAmount(), request.getRemarks());
                createExpenseCreditTransaction(paymentMethod, request.getOrganizationId(), journalEntry.getId(),
                                trackingId, request.getAmount(), request.getRemarks());
                return expense(request, journalEntry.getId(), trackingId, paymentMethod.getId());
        }

        private OrganizationExpenseType getExpenseType(Integer expenseTypeId, UUID organizationId) {
                return organizationExpenseTypeRepository.findByIdAndOrganizationId(expenseTypeId, organizationId)
                                .orElseThrow(() -> new RecordNotFoundException(
                                                OrganizationExpenseTypeMessage.ORGANIZATION_EXPENSE_TYPE_ID_NOT_FOUND));
        }

        private OrganizationPaymentMethod getPaymentMethod(Integer paymentMethodId, UUID organizationId) {
                return organizationPaymentMethodRepository.findByIdAndOrganizationId(paymentMethodId, organizationId)
                                .orElseThrow(() -> new RecordNotFoundException(
                                                OrganizationPaymentMethodMessage.ORGANIZATION_PAYMENT_METHOD_ID_NOT_FOUND));
        }

        private void validateAmount(BigDecimal amount) {
                if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
                        throw new InvalidSessionException("Amount must be greater than zero.");
                }
        }

        private void createExpenseDebitTransaction(OrganizationExpenseType expenseType, UUID organizationId,
                        UUID journalEntryId, String trackingId, BigDecimal amount, String remarks) {
                AccountingAccount expenseAccount = accountingAccountService
                                .getAccountByIdAndOrganization(expenseType.getAccountId(), organizationId);
                accountingJournalTransactionService.createTransaction(organizationId, journalEntryId, trackingId,
                                expenseAccount.getId(), amount, BigDecimal.ZERO, remarks);
        }

        private void createExpenseCreditTransaction(OrganizationPaymentMethod paymentMethod, UUID organizationId,
                        UUID journalEntryId, String trackingId, BigDecimal amount, String remarks) {
                AccountingAccount paymentAccount = accountingAccountService.getAccountByIdAndOrganization(
                                paymentMethod.getAccountId(),
                                organizationId);
                accountingJournalTransactionService.createTransaction(organizationId, journalEntryId, trackingId,
                                paymentAccount.getId(), BigDecimal.ZERO, amount, remarks);
        }

        private OrganizationExpense expense(OrganizationExpenseRequest request, UUID journalEntryId,
                        String trackingId, Integer paymentMethodId) {
                OrganizationExpense expense = new OrganizationExpense();
                expense.setOrganizationId(request.getOrganizationId());
                expense.setJournalEntryId(journalEntryId);
                expense.setJournalEntryTrackingId(trackingId);
                expense.setExpenseTypeId(request.getExpenseTypeId());
                expense.setPaymentMethodId(paymentMethodId);
                expense.setAmount(request.getAmount());
                expense.setExpenseDate(request.getExpenseDate());
                expense.setRemarks(request.getRemarks());
                return organizationExpenseRepository.save(expense);
        }

        public List<OrganizationExpense> viewAllOrganizationExpenses() {
                return organizationExpenseRepository.findAll();
        }

}
