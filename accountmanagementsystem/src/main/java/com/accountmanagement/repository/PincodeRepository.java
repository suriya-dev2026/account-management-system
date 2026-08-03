package com.accountmanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.accountmanagement.model.MasterCity;
import com.accountmanagement.model.Pincode;

public interface PincodeRepository extends JpaRepository<Pincode, Integer>{

    List<Pincode> findByCityId(Integer cityId);

    boolean existsByPincodeAndCity(String pincodeValue, MasterCity city);

}
