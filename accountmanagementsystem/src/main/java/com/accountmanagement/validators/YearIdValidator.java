package com.accountmanagement.validators;

import com.accountmanagement.repository.VbsYearRepository;
import com.accountmanagement.validations.ValidYearId;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class YearIdValidator implements ConstraintValidator<ValidYearId, Integer> {

    private final VbsYearRepository vbsYearRepository;

    public YearIdValidator(VbsYearRepository vbsYearRepository) {
        this.vbsYearRepository = vbsYearRepository;
    }

    @Override
    public boolean isValid(Integer id, ConstraintValidatorContext arg1) {
        if (id == null) {
            return false;
        }
        return vbsYearRepository.existsById(id);
    }

}
