package com.accountmanagement.service;

import java.util.List;
import org.springframework.stereotype.Service;

import com.accountmanagement.exceptions.RecordNotFoundException;
import com.accountmanagement.model.MasterState;
import com.accountmanagement.repository.MasterCountryRepository;
import com.accountmanagement.repository.MasterStateRepository;

@Service
public class MasterStateService {

    private final MasterStateRepository stateRepository;

    private final MasterCountryRepository masterCountryRepository;

    public MasterStateService(MasterStateRepository stateRepository, MasterCountryRepository masterCountryRepository) {
        this.stateRepository = stateRepository;
        this.masterCountryRepository = masterCountryRepository;
    }

    public List<MasterState> getAllStates() {
        return stateRepository.findAll();
    }

    public List<MasterState> getStatesByCountryId(Integer countryId) {
        masterCountryRepository.findById(countryId)
                .orElseThrow(() -> new RecordNotFoundException("Country not found"));

        List<MasterState> states = stateRepository.findByCountryId(countryId);

        if (states.isEmpty()) {
            throw new RecordNotFoundException("No states found for this country");
        }
        return states;
    }

}
