package com.accountmanagement.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.accountmanagement.model.OrganizationExpense;

public interface OrganizationExpenseRepository extends JpaRepository<OrganizationExpense, UUID> {

}
