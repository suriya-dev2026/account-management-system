package com.accountmanagement.validators;

import java.util.UUID;
import com.accountmanagement.repository.SubscriptionFeatureRepository;
import com.accountmanagement.validations.ValidFeatureId;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FeatureIdValidator implements ConstraintValidator<ValidFeatureId, UUID> {

    private final SubscriptionFeatureRepository subscriptionFeatureRepository;

    public FeatureIdValidator(SubscriptionFeatureRepository subscriptionFeatureRepository) {
        this.subscriptionFeatureRepository = subscriptionFeatureRepository;
    }

    @Override
    public boolean isValid(UUID id, ConstraintValidatorContext arg1) {

        if (id == null) {
            return false;
        }
        return subscriptionFeatureRepository.existsById(id);
    }

}
