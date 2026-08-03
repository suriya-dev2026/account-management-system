package com.accountmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.accountmanagement.model.MasterCountry;

public interface MasterCountryRepository extends JpaRepository<MasterCountry, Integer> {

}
