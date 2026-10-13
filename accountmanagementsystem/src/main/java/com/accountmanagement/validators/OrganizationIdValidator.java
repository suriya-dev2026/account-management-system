package com.accountmanagement.validators;

import java.util.UUID;

import org.springframework.security.access.AccessDeniedException;

import com.accountmanagement.repository.OrganizationRepository;
import com.accountmanagement.service.OrganizationAccessService;
import com.accountmanagement.validations.ValidOrganizationId;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrganizationIdValidator implements ConstraintValidator<ValidOrganizationId, UUID> {

    private final OrganizationRepository organizationRepository;

    private final OrganizationAccessService organizationAccessService;

    @Override
    public boolean isValid(UUID id, ConstraintValidatorContext context) {
        if (id == null) {
            return false;
        }
        if (!organizationRepository.existsById(id)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "Organization ID does not exist")
                    .addConstraintViolation();
            return false;
        }
        try {
            UUID currentOrganizationId = organizationAccessService.getCurrentOrganizationId();
            if (!currentOrganizationId.equals(id)) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(
                        "User is not associated with an organization")
                        .addConstraintViolation();
                return false;
            }
        } catch (AccessDeniedException ex) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "You do not have permission to access this organization")
                    .addConstraintViolation();

            return false;
        }
        return true;
    }

}
