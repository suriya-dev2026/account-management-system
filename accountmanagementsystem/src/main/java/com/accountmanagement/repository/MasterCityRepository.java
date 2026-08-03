package com.accountmanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.accountmanagement.model.MasterCity;

public interface MasterCityRepository extends JpaRepository<MasterCity, Integer> {

    List<MasterCity> findByStateId(Integer stateId);

}
