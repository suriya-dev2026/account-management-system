package com.accountmanagement.validators;

import com.accountmanagement.repository.LocationRepository;
import com.accountmanagement.validations.ValidLocationId;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class LocationIdValidator implements ConstraintValidator<ValidLocationId, Integer> {

    private final LocationRepository locationRepository;

    LocationIdValidator(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public boolean isValid(Integer location, ConstraintValidatorContext arg1) {

        if (location == null) {
            return false;
        }

        return locationRepository.existsById(location);
    }

}
