package com.accountmanagement.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.accountmanagement.exceptions.RecordNotFoundException;
import com.accountmanagement.model.MasterCity;
import com.accountmanagement.repository.MasterCityRepository;
import com.accountmanagement.repository.MasterStateRepository;

@Service
public class MasterCityService {

    private final MasterCityRepository cityRepository;

    private final MasterStateRepository masterStateRepository;

    public MasterCityService(MasterCityRepository cityRepository, MasterStateRepository masterStateRepository) {
        this.cityRepository = cityRepository;
        this.masterStateRepository = masterStateRepository;
    }

    public List<MasterCity> getAllCities() {
        return cityRepository.findAll();
    }

    public List<MasterCity> getCitiesByStateId(Integer stateId) {
        masterStateRepository.findById(stateId)
                .orElseThrow(() -> new RecordNotFoundException("State not found"));
        List<MasterCity> cities = cityRepository.findByStateId(stateId);
        if (cities.isEmpty()) {
            throw new RecordNotFoundException("No cities found for this state");
        }
        return cities;
    }
}
