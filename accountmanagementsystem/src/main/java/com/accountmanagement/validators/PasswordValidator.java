package com.accountmanagement.validators;

import com.accountmanagement.utility.Apputility;
import com.accountmanagement.validations.ValidPassword;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return Apputility.isValidPassword(value);
    }

}
