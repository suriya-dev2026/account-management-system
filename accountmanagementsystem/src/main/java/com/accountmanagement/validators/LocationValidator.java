package com.accountmanagement.validators;

import com.accountmanagement.repository.LocationRepository;
import com.accountmanagement.validations.ValidLocation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class LocationValidator implements ConstraintValidator<ValidLocation, String> {

    private final LocationRepository locationRepository;

    LocationValidator(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public boolean isValid(String location, ConstraintValidatorContext arg1) {

        if (location == null) {
            return false;
        }

        return locationRepository.existsByLocation(location);
    }

}
