package com.accountmanagement.validators;

import com.accountmanagement.validations.ValidCurrency;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CurrencyValidator implements ConstraintValidator<ValidCurrency, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext arg1) {
        return value == null || value.matches("^[A-Z]{3}$");
    }
}
