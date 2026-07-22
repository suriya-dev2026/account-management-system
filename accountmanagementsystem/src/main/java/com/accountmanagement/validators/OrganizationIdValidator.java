package com.accountmanagement.validators;

import java.util.UUID;
import com.accountmanagement.repository.OrganizationRepository;
import com.accountmanagement.validations.ValidOrganizationId;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class OrganizationIdValidator implements ConstraintValidator<ValidOrganizationId, UUID> {

    private final OrganizationRepository organizationRepository;

    OrganizationIdValidator(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    @Override
    public boolean isValid(UUID id, ConstraintValidatorContext arg1) {
        if (id == null) {
            return false;
        }
        return organizationRepository.existsById(id);
    }

}
