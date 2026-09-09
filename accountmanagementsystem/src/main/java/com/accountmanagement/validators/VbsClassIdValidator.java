package com.accountmanagement.validators;

import com.accountmanagement.repository.VbsClassRepository;
import com.accountmanagement.validations.ValidVbsClassId;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class VbsClassIdValidator implements ConstraintValidator<ValidVbsClassId, Integer> {

    private final VbsClassRepository vbsClassRepository;

    public VbsClassIdValidator(VbsClassRepository vbsClassRepository) {
        this.vbsClassRepository = vbsClassRepository;
    }

    @Override
    public boolean isValid(Integer id, ConstraintValidatorContext arg1) {
        if (id == null) {
            return false;
        }
        return vbsClassRepository.existsById(id);

    }

}
