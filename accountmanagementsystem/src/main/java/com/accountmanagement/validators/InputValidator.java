package com.accountmanagement.validators;

import java.util.regex.Pattern;
import com.accountmanagement.validations.ValidInput;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class InputValidator implements ConstraintValidator<ValidInput, String> {

    private static final Pattern INVALID_CHARACTERS = Pattern.compile("[<>\"'`;?{}()\\[\\]]");

    private static final Pattern DATE_PATTERN = Pattern.compile(
            "^\\d{4}-\\d{2}-\\d{2}$|^\\d{2}-\\d{2}-\\d{4}$|^\\d{2}/\\d{2}/\\d{4}$");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.trim().isEmpty()) {
            return true;
        }
        if (INVALID_CHARACTERS.matcher(value).find()) {
            return false;
        }
        if (DATE_PATTERN.matcher(value.trim()).matches()) {
            return false;
        }
        return true;
    }

}
