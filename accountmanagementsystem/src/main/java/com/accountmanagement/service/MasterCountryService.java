package com.accountmanagement.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.accountmanagement.model.MasterCountry;
import com.accountmanagement.repository.MasterCountryRepository;

@Service
public class MasterCountryService {

    private final MasterCountryRepository masterCountryRepository;

    public MasterCountryService(MasterCountryRepository masterCountryRepository) {
        this.masterCountryRepository = masterCountryRepository;
    }

    public List<MasterCountry> getAllCountries() {
        return masterCountryRepository.findAll();
    }
}
