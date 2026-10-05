package com.accountmanagement.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.accountmanagement.model.AccountingJournalTransaction;

public interface AccountingJournalTransactionRepository extends JpaRepository<AccountingJournalTransaction, UUID> {

}
