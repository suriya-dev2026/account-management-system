package com.accountmanagement.validators;

import java.util.regex.Pattern;

import com.accountmanagement.validations.ValidInput;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class InputValidator implements ConstraintValidator<ValidInput, String> {

    private static final Pattern INVALID_CHARACTERS = Pattern.compile("[<>\"'`;?{}()\\[\\]]");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.trim().isEmpty()) {
            return true;
        }

        if (INVALID_CHARACTERS.matcher(value).find()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "Input contains invalid characters.").addConstraintViolation();

            return false;
        }
        return true;
    }

}
