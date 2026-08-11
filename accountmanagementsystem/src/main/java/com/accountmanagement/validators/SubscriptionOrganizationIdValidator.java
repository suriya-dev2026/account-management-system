package com.accountmanagement.validators;

import java.util.UUID;

import com.accountmanagement.repository.SubscriptionOrganizationRepository;
import com.accountmanagement.validations.ValidSubscriptionOrganizationId;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SubscriptionOrganizationIdValidator implements ConstraintValidator<ValidSubscriptionOrganizationId, UUID> {

    private final SubscriptionOrganizationRepository subscriptionOrganizationRepository;

    public SubscriptionOrganizationIdValidator(SubscriptionOrganizationRepository subscriptionOrganizationRepository) {
        this.subscriptionOrganizationRepository = subscriptionOrganizationRepository;
    }

    @Override
    public boolean isValid(UUID id, ConstraintValidatorContext arg1) {
        if (id == null) {
            return true;
        }
        return subscriptionOrganizationRepository.existsById(id);
    }

}
