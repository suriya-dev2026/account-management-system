package com.accountmanagement.validators;

import com.accountmanagement.utility.Apputility;
import com.accountmanagement.validations.ValidPhone;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneValidator implements ConstraintValidator<ValidPhone, String> {

    @Override
    public boolean isValid(String phone, ConstraintValidatorContext arg1) {

        return Apputility.isValidPhone(phone);

    }

}
