package com.accountmanagement.validators;

import java.util.UUID;

import com.accountmanagement.repository.SundaySchoolStudentRepository;
import com.accountmanagement.validations.ValidStudentId;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StudentIdValidator implements ConstraintValidator<ValidStudentId, UUID> {

    private final SundaySchoolStudentRepository sundaySchoolStudentRepository;

    public StudentIdValidator(SundaySchoolStudentRepository sundaySchoolStudentRepository) {
        this.sundaySchoolStudentRepository = sundaySchoolStudentRepository;
    }

    @Override
    public boolean isValid(UUID id, ConstraintValidatorContext arg1) {
        if (id == null) {
            return false;
        }
        return sundaySchoolStudentRepository.existsById(id);
    }

}
