package com.accountmanagement.validators;

import com.accountmanagement.repository.MemberCategoryRepository;
import com.accountmanagement.validations.ValidCategory;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CategoryValidator implements ConstraintValidator<ValidCategory, String> {

    private final MemberCategoryRepository memberCategoryRepository;

    CategoryValidator(MemberCategoryRepository memberCategoryRepository) {
        this.memberCategoryRepository = memberCategoryRepository;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext arg1) {

        if(value == null){
            return false;
        }
        return memberCategoryRepository.existsByCategory(value);

    }

}
