package com.accountmanagement.validators;

import java.util.UUID;
import com.accountmanagement.repository.UserRepository;
import com.accountmanagement.validations.ValidUserId;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UserIdValidator implements ConstraintValidator<ValidUserId, UUID> {

    private final UserRepository userRepository;

    public UserIdValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(UUID id, ConstraintValidatorContext arg1) {

        if (id == null) {
            return false;
        }
        return userRepository.existsById(id);
    }

}
