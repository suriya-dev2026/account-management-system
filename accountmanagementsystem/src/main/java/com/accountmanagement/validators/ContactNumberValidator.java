package com.accountmanagement.validators;

import com.accountmanagement.utility.Apputility;
import com.accountmanagement.validations.ValidContactNumber;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ContactNumberValidator implements ConstraintValidator<ValidContactNumber, String> {

    @Override
    public boolean isValid(String contactNumber, ConstraintValidatorContext context) {
        return Apputility.isValidContactNumber(contactNumber);
    }
}
