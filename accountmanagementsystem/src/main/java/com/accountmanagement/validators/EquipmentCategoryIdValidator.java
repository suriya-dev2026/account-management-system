package com.accountmanagement.validators;

import com.accountmanagement.repository.EquipmentCategoryRepository;
import com.accountmanagement.validations.ValidEquipmentCategoryId;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EquipmentCategoryIdValidator implements ConstraintValidator<ValidEquipmentCategoryId, Integer> {

    private final EquipmentCategoryRepository equipmentCategoryRepository;

    public EquipmentCategoryIdValidator(EquipmentCategoryRepository equipmentCategoryRepository) {
        this.equipmentCategoryRepository = equipmentCategoryRepository;
    }

    @Override
    public boolean isValid(Integer id, ConstraintValidatorContext arg1) {
        if (id == null) {
            return true;
        }
        return equipmentCategoryRepository.existsById(id);
    }

}
