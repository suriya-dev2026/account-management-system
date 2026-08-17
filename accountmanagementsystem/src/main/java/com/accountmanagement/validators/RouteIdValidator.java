package com.accountmanagement.validators;

import com.accountmanagement.repository.AccessControlRouteRepository;
import com.accountmanagement.validations.ValidRouteId;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RouteIdValidator implements ConstraintValidator<ValidRouteId, Integer> {

    private final AccessControlRouteRepository accessControlRouteRepository;

    public RouteIdValidator(AccessControlRouteRepository accessControlRouteRepository) {
        this.accessControlRouteRepository = accessControlRouteRepository;
    }

    @Override
    public boolean isValid(Integer id, ConstraintValidatorContext arg1) {

        if (id == null) {
            return false;
        }
        return accessControlRouteRepository.existsById(id);
    }

}
