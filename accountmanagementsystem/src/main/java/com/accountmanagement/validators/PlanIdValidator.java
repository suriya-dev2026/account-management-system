package com.accountmanagement.validators;

import java.util.UUID;
import com.accountmanagement.repository.SubscriptionPlanRepository;
import com.accountmanagement.validations.ValidPlanId;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PlanIdValidator implements ConstraintValidator<ValidPlanId, UUID> {

    private final SubscriptionPlanRepository subscriptionPlanRepository;

    public PlanIdValidator(SubscriptionPlanRepository subscriptionPlanRepository) {
        this.subscriptionPlanRepository = subscriptionPlanRepository;
    }

    @Override
    public boolean isValid(UUID id, ConstraintValidatorContext arg1) {
        if (id == null) {
            return false;
        }
        return subscriptionPlanRepository.existsById(id);
    }

}
