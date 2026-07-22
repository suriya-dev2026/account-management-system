package com.accountmanagement.validators;

import com.accountmanagement.validations.ValidLogin;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class LoginValidator implements ConstraintValidator<ValidLogin, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext arg1) {

        if (value == null || value.trim().isEmpty()) {
            return false;
        }
        if (value.contains("@")) {
            return value.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
        }
        if (value.matches("\\d+")) {
            return value.matches("\\d{10}");
        }
        return value.matches("^[A-Za-z0-9_]{3,20}$");
    }

}
