package com.accountmanagement.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.accountmanagement.constants.message.OrganizationOfferingTypeMessage;
import com.accountmanagement.constants.message.OrganizationPaymentMethodMessage;
import com.accountmanagement.enums.GatewayStatus;
import com.accountmanagement.exceptions.InvalidSessionException;
import com.accountmanagement.exceptions.RecordNotFoundException;
import com.accountmanagement.model.AccountingAccount;
import com.accountmanagement.model.AccountingJournalEntry;
import com.accountmanagement.model.OrganizationOffering;
import com.accountmanagement.model.OrganizationOfferingType;
import com.accountmanagement.model.OrganizationPaymentMethod;
import com.accountmanagement.repository.OrganizationOfferingRepository;
import com.accountmanagement.repository.OrganizationOfferingTypeRepository;
import com.accountmanagement.repository.OrganizationPaymentMethodRepository;
import com.accountmanagement.request.OrganizationOfferingRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrganizationOfferingService {

        private final OrganizationOfferingRepository organizationOfferingRepository;

        private final OrganizationOfferingTypeRepository organizationOfferingTypeRepository;

        private final OrganizationPaymentMethodRepository organizationPaymentMethodRepository;

        private final AccountingAccountService accountingAccountService;

        private final AccountingJournalEntryService accountingJournalEntryService;

        private final AccountingJournalTransactionService accountingJournalTransactionService;

        @Transactional
        public OrganizationOffering createOffering(OrganizationOfferingRequest request) {
                OrganizationOfferingType offeringType = getOfferingType(request.getOfferingTypeId(),
                                request.getOrganizationId());
                OrganizationPaymentMethod paymentMethod = getPaymentMethod(request.getPaymentMethodId(),
                                request.getOrganizationId());
                validateAmount(request.getAmount());
                AccountingJournalEntry journalEntry = accountingJournalEntryService.createJournalEntry(
                                request.getOrganizationId(), request.getReceivedDate(), request.getRemarks());
                String trackingId = journalEntry.getTrackingId();
                createOfferingDebitTransaction(paymentMethod, request.getOrganizationId(), journalEntry.getId(),
                                trackingId, request.getAmount(), request.getRemarks());
                createOfferingCreditTransaction(offeringType, request.getOrganizationId(), journalEntry.getId(),
                                trackingId, request.getAmount(), request.getRemarks());
                return offering(request, journalEntry.getId(), trackingId, offeringType.getId(), paymentMethod.getId());
        }

        private OrganizationOfferingType getOfferingType(Integer offeringTypeId, UUID organizationId) {
                return organizationOfferingTypeRepository.findByIdAndOrganizationId(offeringTypeId, organizationId)
                                .orElseThrow(() -> new RecordNotFoundException(
                                                OrganizationOfferingTypeMessage.ORGANIZATION_OFFERING_TYPE_ID_NOT_FOUND));
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

        private void createOfferingDebitTransaction(OrganizationPaymentMethod paymentMethod, UUID organizationId,
                        UUID journalEntryId, String trackingId,
                        BigDecimal amount, String remarks) {
                AccountingAccount paymentAccount = accountingAccountService.getAccountByIdAndOrganization(
                                paymentMethod.getAccountId(),
                                organizationId);
                accountingJournalTransactionService.createTransaction(organizationId, journalEntryId, trackingId,
                                paymentAccount.getId(), amount, BigDecimal.ZERO, remarks);
        }

        private void createOfferingCreditTransaction(OrganizationOfferingType offeringType, UUID organizationId,
                        UUID journalEntryId, String trackingId,
                        BigDecimal amount, String remarks) {
                AccountingAccount offeringAccount = accountingAccountService
                                .getAccountByIdAndOrganization(offeringType.getAccountId(), organizationId);
                accountingJournalTransactionService.createTransaction(organizationId, journalEntryId, trackingId,
                                offeringAccount.getId(), BigDecimal.ZERO,
                                amount, remarks);
        }

        public OrganizationOffering offering(OrganizationOfferingRequest request, UUID journalEntryId,
                        String trackingId, Integer offeringTypeId, Integer paymentMethodId) {
                OrganizationOffering offering = new OrganizationOffering();
                offering.setOrganizationId(request.getOrganizationId());
                offering.setJournalEntryId(journalEntryId);
                offering.setJournalEntryTrackingId(trackingId);
                offering.setOfferingTypeId(offeringTypeId);
                offering.setMemberId(request.getMemberId());
                offering.setPaymentMethodId(paymentMethodId);
                offering.setAmount(request.getAmount());
                offering.setCurrency(request.getCurrency());
                offering.setBankAccountId(request.getBankAccountId());
                offering.setReceivedDate(request.getReceivedDate());
                offering.setRemarks(request.getRemarks());
                offering.setGatewayStatus(GatewayStatus.SUCCESS);
                return organizationOfferingRepository.save(offering);
        }

        public List<OrganizationOffering> viewAllOfferings() {
                return organizationOfferingRepository.findAll();
        }

}
