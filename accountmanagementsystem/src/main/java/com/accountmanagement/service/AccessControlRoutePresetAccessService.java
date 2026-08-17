package com.accountmanagement.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.exceptions.RecordNotFoundException;
import com.accountmanagement.model.AccessControlRoutePresetAccess;
import com.accountmanagement.repository.AccessControlRoutePresetAccessRepository;
import com.accountmanagement.request.AccessControlRoutePresetAccessRequest;

@Service
public class AccessControlRoutePresetAccessService {

    private final AccessControlRoutePresetAccessRepository accessControlRoutePresetAccessRepository;

    public AccessControlRoutePresetAccessService(
            AccessControlRoutePresetAccessRepository accessControlRoutePresetAccessRepository) {
        this.accessControlRoutePresetAccessRepository = accessControlRoutePresetAccessRepository;
    }

    public AccessControlRoutePresetAccess addAccessControlRoutePresetAccess(
            AccessControlRoutePresetAccessRequest accessControlRoutePresetAccessRequest) {
        AccessControlRoutePresetAccess accessControlRoutePresetAccess = new AccessControlRoutePresetAccess();
        accessControlRoutePresetAccess.setModulePresetId(accessControlRoutePresetAccessRequest.getModulePresetId());
        accessControlRoutePresetAccess.setRouteId(accessControlRoutePresetAccessRequest.getRouteId());
        return accessControlRoutePresetAccessRepository.save(accessControlRoutePresetAccess);
    }

    public AccessControlRoutePresetAccess updateAccessControlRoutePresetAccess(Integer id,
            AccessControlRoutePresetAccessRequest accessControlRoutePresetAccessRequest) {
        AccessControlRoutePresetAccess accessControlRoutePresetAccess = findByAccessControlRoutePresetAccessById(id);
        accessControlRoutePresetAccess.setModulePresetId(accessControlRoutePresetAccessRequest.getModulePresetId());
        accessControlRoutePresetAccess.setRouteId(accessControlRoutePresetAccessRequest.getRouteId());
        return accessControlRoutePresetAccessRepository.save(accessControlRoutePresetAccess);
    }

    public void deleteAccessControlRoutePresetAccessById(Integer id) {
        AccessControlRoutePresetAccess accessControlRoutePresetAccess = findByAccessControlRoutePresetAccessById(id);
        accessControlRoutePresetAccess.setStatus(AppConstants.INACTIVE);
        accessControlRoutePresetAccessRepository.save(accessControlRoutePresetAccess);
    }

    public List<AccessControlRoutePresetAccess> viewAllAccessControlRoutePresetAccess() {
        return accessControlRoutePresetAccessRepository.findAll();
    }

    public AccessControlRoutePresetAccess findByAccessControlRoutePresetAccessById(Integer id) {
        return accessControlRoutePresetAccessRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Access Control Route Preset Access Id Not Found"));
    }

}
