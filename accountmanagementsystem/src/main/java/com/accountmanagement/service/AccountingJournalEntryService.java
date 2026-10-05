package com.accountmanagement.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.accountmanagement.exceptions.RecordNotFoundException;
import com.accountmanagement.model.AccountingJournalEntry;
import com.accountmanagement.model.Organization;
import com.accountmanagement.repository.AccountingJournalEntryRepository;
import com.accountmanagement.repository.OrganizationRepository;
import com.accountmanagement.utility.Apputility;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountingJournalEntryService {

    private final AccountingJournalEntryRepository accountingJournalEntryRepository;

    private final OrganizationRepository organizationRepository;

    @Transactional
    public AccountingJournalEntry createJournalEntry(UUID organizationId, LocalDate receivedDate, String description) {
        String trackingId = generateJournalTrackingId(organizationId, receivedDate);
        AccountingJournalEntry journalEntry = new AccountingJournalEntry();
        journalEntry.setTrackingId(trackingId);
        journalEntry.setOrganizationId(organizationId);
        journalEntry.setEntryDate(receivedDate);
        journalEntry.setDescription(description);
        journalEntry.setCreatedBy(Apputility.getLoggedUser().getId());
        return accountingJournalEntryRepository.save(journalEntry);
    }

    public String generateJournalTrackingId(UUID id, LocalDate entryDate) {
        Organization organization = organizationRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(
                "Organization not found."));
        Long sequence = accountingJournalEntryRepository
                .getNextJournalTrackingSequence();
        return String.format("JRN-%s-%s-%06d", organization.getCode(),
                entryDate.format(DateTimeFormatter.ofPattern("yyMMdd")), sequence);
    }
}
