package com.accountmanagement.validators;

import com.accountmanagement.validations.ValidYear;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class YearValidator implements ConstraintValidator<ValidYear, Integer> {

    @Override
    public boolean isValid(Integer year, ConstraintValidatorContext arg1) {

        if (year == null) {
            return false;
        }
        return year >= 1900 && year <= 2100;
    }

}
