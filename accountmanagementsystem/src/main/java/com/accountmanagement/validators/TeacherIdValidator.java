package com.accountmanagement.validators;

import java.util.UUID;

import com.accountmanagement.repository.SundaySchoolTeacherRepository;
import com.accountmanagement.validations.ValidTeacherId;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TeacherIdValidator implements ConstraintValidator<ValidTeacherId, UUID> {

    private final SundaySchoolTeacherRepository sundaySchoolTeacherRepository;

    public TeacherIdValidator(SundaySchoolTeacherRepository sundaySchoolTeacherRepository) {
        this.sundaySchoolTeacherRepository = sundaySchoolTeacherRepository;
    }

    @Override
    public boolean isValid(UUID id, ConstraintValidatorContext arg1) {

        if (id == null) {
            return true;
        }
        return sundaySchoolTeacherRepository.existsById(id);
    }

}
