package com.accountmanagement.validators;

import java.util.UUID;

import com.accountmanagement.repository.AccessControlRoleRepository;
import com.accountmanagement.validations.ValidRoleId;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RoleIdValidator implements ConstraintValidator<ValidRoleId, UUID> {

    private final AccessControlRoleRepository aaccessControlRoleRepository;

    public RoleIdValidator(AccessControlRoleRepository accessControlRoleRepository) {
        this.aaccessControlRoleRepository = accessControlRoleRepository;
    }

    @Override
    public boolean isValid(UUID id, ConstraintValidatorContext arg1) {

        if (id == null) {
            return false;
        }
        return aaccessControlRoleRepository.existsById(id);
    }

}
