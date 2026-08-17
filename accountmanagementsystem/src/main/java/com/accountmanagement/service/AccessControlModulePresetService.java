package com.accountmanagement.service;

import java.util.List;

import org.springframework.stereotype.Service;
import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.exceptions.RecordNotFoundException;
import com.accountmanagement.model.AccessControlModulePreset;
import com.accountmanagement.repository.AccessControlModulePresetRepository;
import com.accountmanagement.request.AccessControlModulePresetRequest;

@Service
public class AccessControlModulePresetService {

    private final AccessControlModulePresetRepository accessControlModulePresetRepository;

    public AccessControlModulePresetService(AccessControlModulePresetRepository accessControlModulePresetRepository) {
        this.accessControlModulePresetRepository = accessControlModulePresetRepository;
    }

    public AccessControlModulePreset addAccessControlModulePreset(
            AccessControlModulePresetRequest accessControlModulePresetRequest) {
        AccessControlModulePreset accessControlModulePreset = new AccessControlModulePreset();
        accessControlModulePreset.setModuleName(accessControlModulePresetRequest.getModuleName());
        accessControlModulePreset.setPresetName(accessControlModulePresetRequest.getPresetName());
        accessControlModulePreset.setDescription(accessControlModulePresetRequest.getDescription());
        return accessControlModulePresetRepository.save(accessControlModulePreset);
    }

    public AccessControlModulePreset updateAccessControlModulePreset(Integer id,
            AccessControlModulePresetRequest accessControlModulePresetRequest) {
        AccessControlModulePreset accessControlModulePreset = findByAccessControlModulePresetId(id);
        accessControlModulePreset.setModuleName(accessControlModulePresetRequest.getModuleName());
        accessControlModulePreset.setPresetName(accessControlModulePresetRequest.getPresetName());
        accessControlModulePreset.setDescription(accessControlModulePresetRequest.getDescription());
        return accessControlModulePresetRepository.save(accessControlModulePreset);
    }

    public void deleteAccessControlModulePresetById(Integer id) {
        AccessControlModulePreset accessControlModulePreset = findByAccessControlModulePresetId(id);
        accessControlModulePreset.setStatus(AppConstants.INACTIVE);
        accessControlModulePresetRepository.save(accessControlModulePreset);
    }

    public AccessControlModulePreset findByAccessControlModulePresetId(Integer id) {
        return accessControlModulePresetRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Access Control Module Preset Id Not Found"));
    }

    public List<AccessControlModulePreset> viewAllAccessControlModulePreset() {
        return accessControlModulePresetRepository.findAll();
    }

}
