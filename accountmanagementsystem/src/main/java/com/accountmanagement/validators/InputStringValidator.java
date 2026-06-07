package com.accountmanagement.validators;

import org.apache.commons.lang3.StringUtils;

import com.accountmanagement.validations.ValidInputString;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class InputStringValidator implements ConstraintValidator<ValidInputString, String> {

    private boolean allowNull;
    private boolean alphaOnly;

    @Override
    public void initialize(ValidInputString constraint) {
        this.allowNull = constraint.allowNull();
        this.alphaOnly = constraint.alphaOnly();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (allowNull && StringUtils.isBlank(value)) {
            return true;
        }

        if (StringUtils.isBlank(value)) {
            return false;
        }

        value = StringUtils.trim(value);

        if (alphaOnly) {
            return StringUtils.isAlpha(value);
        }

        return value.matches("[a-z0-9]+");
    }

}
