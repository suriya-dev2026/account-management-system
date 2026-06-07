package com.accountmanagement.validators;

import com.accountmanagement.utility.Apputility;
import com.accountmanagement.validations.ValidPhone;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneValidator implements ConstraintValidator<ValidPhone, String> {

    @Override
    public boolean isValid(String phoneNo, ConstraintValidatorContext context) {
        return Apputility.isValidPhone(phoneNo);
    }
}
