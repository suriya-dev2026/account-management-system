package com.accountmanagement.validators;

import com.accountmanagement.utility.Apputility;
import com.accountmanagement.validations.ValidOtp;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class OtpValidator implements ConstraintValidator<ValidOtp, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return Apputility.isValidOtp(value);
    }

}
