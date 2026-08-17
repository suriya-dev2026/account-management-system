package com.accountmanagement.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.exceptions.DuplicateRecordException;
import com.accountmanagement.exceptions.RecordNotFoundException;
import com.accountmanagement.model.AccessControlRole;
import com.accountmanagement.repository.AccessControlRoleRepository;
import com.accountmanagement.request.AccessControlRoleRequest;

@Service
public class AccessControlRoleService {

    private final AccessControlRoleRepository accessControlRoleRepository;

    public AccessControlRoleService(AccessControlRoleRepository accessControlRoleRepository) {
        this.accessControlRoleRepository = accessControlRoleRepository;
    }

    public AccessControlRole createRole(AccessControlRoleRequest accessControlRoleRequest) {
        validateRoleName(accessControlRoleRequest.getRoleName());
        AccessControlRole accessControlRole = new AccessControlRole();
        accessControlRole.setRoleName(accessControlRoleRequest.getRoleName());
        accessControlRole.setDescription(accessControlRoleRequest.getDescription());
        return accessControlRoleRepository.save(accessControlRole);
    }

    public AccessControlRole updateRole(UUID id, AccessControlRoleRequest accessControlRoleRequest) {
        AccessControlRole accessControlRole = findByAccessControlRoleId(id);
        accessControlRole.setRoleName(accessControlRoleRequest.getRoleName());
        accessControlRole.setDescription(accessControlRoleRequest.getDescription());
        return accessControlRoleRepository.save(accessControlRole);
    }

    public AccessControlRole findByAccessControlRoleId(UUID id) {
        return accessControlRoleRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Access Control Role id not found"));
    }

    public List<AccessControlRole> viewAllAccessControlRole() {
        return accessControlRoleRepository.findAll();
    }

    public void deleteById(UUID id) {
        AccessControlRole accessControlRole = findByAccessControlRoleId(id);
        accessControlRole.setStatus(AppConstants.INACTIVE);
        accessControlRoleRepository.save(accessControlRole);
    }

    private void validateRoleName(String roleName) {
        if (accessControlRoleRepository
                .existsByRoleNameIgnoreCase(roleName.trim())) {
            throw new DuplicateRecordException(
                    "Role Name Already Exists");
        }
    }

}
