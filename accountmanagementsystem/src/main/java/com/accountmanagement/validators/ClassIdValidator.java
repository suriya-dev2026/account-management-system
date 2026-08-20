package com.accountmanagement.validators;

import java.util.UUID;

import com.accountmanagement.repository.SundaySchoolClassRepository;
import com.accountmanagement.validations.ValidClassId;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ClassIdValidator implements ConstraintValidator<ValidClassId, UUID> {

    private final SundaySchoolClassRepository sundaySchoolClassRepository;

    public ClassIdValidator(SundaySchoolClassRepository sundaySchoolClassRepository) {
        this.sundaySchoolClassRepository = sundaySchoolClassRepository;
    }

    @Override
    public boolean isValid(UUID id, ConstraintValidatorContext arg1) {
        if (id == null) {
            return false;
        }
        return sundaySchoolClassRepository.existsById(id);
    }

}
