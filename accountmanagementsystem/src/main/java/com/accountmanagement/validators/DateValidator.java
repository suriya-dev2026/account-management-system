package com.accountmanagement.validators;

import java.time.LocalDate;

import com.accountmanagement.validations.ValidDate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DateValidator implements ConstraintValidator<ValidDate, LocalDate> {

    @Override
    public boolean isValid(LocalDate date, ConstraintValidatorContext arg1) {
        if (date == null) {
            return true;
        }
        if (date.isAfter(LocalDate.now())) {
            return false;
        }
        if (date.isBefore(LocalDate.of(1900, 1, 1))) {
            return false;
        }
        return true;
    }

}
