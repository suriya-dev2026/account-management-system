package com.accountmanagement.validators;

import com.accountmanagement.repository.OrganizationRepository;
import com.accountmanagement.validations.ValidOrganizationCode;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class OrganizationCodeValidator implements ConstraintValidator<ValidOrganizationCode, String> {

    private final OrganizationRepository organizationRepository;

    OrganizationCodeValidator(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    @Override
    public boolean isValid(String organizationCode, ConstraintValidatorContext arg1) {
        return organizationRepository.existsByCode(organizationCode);
    }

}
