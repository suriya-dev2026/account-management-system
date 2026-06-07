package com.accountmanagement.validators;

import org.springframework.beans.factory.annotation.Autowired;

import com.accountmanagement.repository.UserRepository;
import com.accountmanagement.validations.ValidUserPhoneNumber;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UserPhoneNumberValidator implements ConstraintValidator<ValidUserPhoneNumber, String> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext arg1) {
        if (value == null) {
            return true;
        }
        return !userRepository.existsByPhone(value);
    }

}
