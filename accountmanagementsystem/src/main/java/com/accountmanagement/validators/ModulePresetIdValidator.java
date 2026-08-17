package com.accountmanagement.validators;

import com.accountmanagement.repository.AccessControlModulePresetRepository;
import com.accountmanagement.validations.ValidModulePresetId;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ModulePresetIdValidator implements ConstraintValidator<ValidModulePresetId, Integer> {

    private final AccessControlModulePresetRepository accessControlModulePresetRepository;

    public ModulePresetIdValidator(AccessControlModulePresetRepository accessControlModulePresetRepository) {
        this.accessControlModulePresetRepository = accessControlModulePresetRepository;
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext arg1) {
        if (value == null) {
            return false;
        }
        return accessControlModulePresetRepository.existsById(value);
    }

}
