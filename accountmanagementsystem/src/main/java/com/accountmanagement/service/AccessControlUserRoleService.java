package com.accountmanagement.service;

import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.constants.message.AccessControlUserRoleMessage;
import com.accountmanagement.constants.message.UserMessage;
import com.accountmanagement.exceptions.DuplicateRecordException;
import com.accountmanagement.exceptions.RecordNotFoundException;
import com.accountmanagement.model.AccessControlUserRole;
import com.accountmanagement.repository.AccessControlUserRoleRepository;
import com.accountmanagement.request.AccessControlUserRoleRequest;

@Service
public class AccessControlUserRoleService {

    private final AccessControlUserRoleRepository accessControlUserRoleRepository;

    public AccessControlUserRoleService(AccessControlUserRoleRepository accessControlUserRoleRepository) {
        this.accessControlUserRoleRepository = accessControlUserRoleRepository;
    }

    public AccessControlUserRole createAccessControlUserRole(
            AccessControlUserRoleRequest accessControlUserRoleRequest) {
        validateUserId(accessControlUserRoleRequest.getUserId());
        AccessControlUserRole accessControlUserRole = new AccessControlUserRole();
        accessControlUserRole.setUserId(accessControlUserRoleRequest.getUserId());
        accessControlUserRole.setRoleId(accessControlUserRoleRequest.getRoleId());
        return accessControlUserRoleRepository.save(accessControlUserRole);
    }

    public AccessControlUserRole updateAccessControlUserRole(Integer id,
            AccessControlUserRoleRequest accessControlUserRoleRequest) {
        AccessControlUserRole accessControlUserRole = findAccessControlUserRoleById(id);
        accessControlUserRole.setUserId(accessControlUserRoleRequest.getUserId());
        accessControlUserRole.setRoleId(accessControlUserRoleRequest.getRoleId());
        return accessControlUserRoleRepository.save(accessControlUserRole);
    }

    public void deleteAccessControlUserRoleById(Integer id) {
        AccessControlUserRole accessControlUserRole = findAccessControlUserRoleById(id);
        accessControlUserRole.setStatus(AppConstants.INACTIVE);
        accessControlUserRoleRepository.save(accessControlUserRole);
    }

    public AccessControlUserRole findAccessControlUserRoleById(Integer id) {
        return accessControlUserRoleRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(AccessControlUserRoleMessage.USER_ROLE_ID_NOT_FOUND));
    }

    public List<AccessControlUserRole> viewAllAccessControlRole() {
        return accessControlUserRoleRepository.findAll();
    }

    private void validateUserId(UUID id) {
        if (accessControlUserRoleRepository
                .existsByUserId(id)) {
            throw new DuplicateRecordException(
                    UserMessage.USER_ID_EXISTS);
        }
    }

}
