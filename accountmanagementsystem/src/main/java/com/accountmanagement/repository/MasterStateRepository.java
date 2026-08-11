package com.accountmanagement.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.accountmanagement.model.MasterState;

public interface MasterStateRepository extends JpaRepository<MasterState, Integer> {

    List<MasterState> findByCountryId(Integer countryId);
}
