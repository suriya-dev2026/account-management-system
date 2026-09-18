package com.accountmanagement.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.constants.message.AccessControlRolePresetAccessMessage;
import com.accountmanagement.exceptions.RecordNotFoundException;
import com.accountmanagement.model.AccessControlRolePresetAccess;
import com.accountmanagement.repository.AccessControlRolePresetAccessRepository;
import com.accountmanagement.request.AccessControlRolePresetAccessRequest;

@Service
public class AccessControlRolePresetAccessService {

    private final AccessControlRolePresetAccessRepository accessControlRolePresetAccessRepository;

    public AccessControlRolePresetAccessService(
            AccessControlRolePresetAccessRepository accessControlRolePresetAccessRepository) {
        this.accessControlRolePresetAccessRepository = accessControlRolePresetAccessRepository;
    }

    public AccessControlRolePresetAccess createAccessControlRolePresetAccess(
            AccessControlRolePresetAccessRequest accessControlRolePresetAccessRequest) {
        AccessControlRolePresetAccess accessControlRolePresetAccess = new AccessControlRolePresetAccess();
        accessControlRolePresetAccess.setRoleId(accessControlRolePresetAccessRequest.getRoleId());
        accessControlRolePresetAccess.setModulePresetId(accessControlRolePresetAccessRequest.getModulePresetId());
        return accessControlRolePresetAccessRepository.save(accessControlRolePresetAccess);
    }

    public AccessControlRolePresetAccess updateAccessControlRolePresetAccess(Integer id,
            AccessControlRolePresetAccessRequest accessControlRolePresetAccessRequest) {
        AccessControlRolePresetAccess accessControlRolePresetAccess = findByAccessControlRolePresetAccessId(id);
        accessControlRolePresetAccess.setRoleId(accessControlRolePresetAccessRequest.getRoleId());
        accessControlRolePresetAccess.setModulePresetId(accessControlRolePresetAccessRequest.getModulePresetId());
        return accessControlRolePresetAccessRepository.save(accessControlRolePresetAccess);
    }

    public void deleteAccessControlRolePresetAccessById(Integer id) {
        AccessControlRolePresetAccess accessControlRolePresetAccess = findByAccessControlRolePresetAccessId(id);
        accessControlRolePresetAccess.setStatus(AppConstants.INACTIVE);
        accessControlRolePresetAccessRepository.save(accessControlRolePresetAccess);
    }

    public AccessControlRolePresetAccess findByAccessControlRolePresetAccessId(Integer id) {
        return accessControlRolePresetAccessRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(
                        AccessControlRolePresetAccessMessage.ACCESS_CONTROL_ROLE_PRESET_ACCESS_ID_NOT_FOUND));
    }

    public List<AccessControlRolePresetAccess> viewAllAccessControlRolePresetAccess() {
        return accessControlRolePresetAccessRepository.findAll();
    }
}
