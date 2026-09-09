package com.accountmanagement.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;

import com.accountmanagement.model.VbsYear;

public interface VbsYearRepository extends JpaRepository<VbsYear, Integer> {

    boolean existsByYearAndStartDateAndEndDate(Integer year, LocalDate startDate, LocalDate endDate);

}
