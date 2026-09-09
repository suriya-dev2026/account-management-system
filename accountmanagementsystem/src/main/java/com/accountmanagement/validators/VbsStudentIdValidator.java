package com.accountmanagement.validators;

import java.util.UUID;

import com.accountmanagement.repository.VbsStudentRepository;
import com.accountmanagement.validations.ValidVbsStudentId;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class VbsStudentIdValidator implements ConstraintValidator<ValidVbsStudentId, UUID> {

    private final VbsStudentRepository vbsStudentRepository;

    public VbsStudentIdValidator(VbsStudentRepository vbsStudentRepository) {
        this.vbsStudentRepository = vbsStudentRepository;
    }

    @Override
    public boolean isValid(UUID id, ConstraintValidatorContext arg1) {
        if (id == null) {
            return false;
        }
        return vbsStudentRepository.existsById(id);

    }

}
