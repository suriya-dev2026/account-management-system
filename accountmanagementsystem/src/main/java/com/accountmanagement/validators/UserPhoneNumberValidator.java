package com.accountmanagement.validators;

import com.accountmanagement.repository.UserRepository;
import com.accountmanagement.validations.ValidUserPhoneNumber;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UserPhoneNumberValidator implements ConstraintValidator<ValidUserPhoneNumber, String> {

    private final UserRepository userRepository;

    UserPhoneNumberValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext arg1) {
        if (value == null) {
            return true;
        }
        return !userRepository.existsByContactNumber(value);
    }

}
