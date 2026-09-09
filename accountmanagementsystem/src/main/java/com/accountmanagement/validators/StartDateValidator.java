package com.accountmanagement.validators;

import java.time.LocalDate;

import com.accountmanagement.validations.ValidStartDate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StartDateValidator implements ConstraintValidator<ValidStartDate, LocalDate> {

    @Override
    public boolean isValid(LocalDate date, ConstraintValidatorContext arg1) {
        if (date == null) {
            return true;
        }

        return !date.isBefore(LocalDate.of(1900, 1, 1));
    }

}
