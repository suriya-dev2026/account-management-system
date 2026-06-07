package com.accountmanagement.validators;

import org.springframework.beans.factory.annotation.Autowired;
import com.accountmanagement.repository.UserRepository;
import com.accountmanagement.validations.ValidUserEmail;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UserEmailIdValidator implements ConstraintValidator<ValidUserEmail, String> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {

        if (email == null) {
            return true;
        }
        return !userRepository.existsByEmail(email);

    }

}
