package com.accountmanagement.validators;

import com.accountmanagement.repository.MemberCategoryRepository;
import com.accountmanagement.validations.ValidMemberCategoryId;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MemberCategoryIdValidator implements ConstraintValidator<ValidMemberCategoryId, Integer> {

    private final MemberCategoryRepository memberCategoryRepository;

    MemberCategoryIdValidator(MemberCategoryRepository memberCategoryRepository) {
        this.memberCategoryRepository = memberCategoryRepository;
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext arg1) {

        if (value == null) {
            return false;
        }
        return memberCategoryRepository.existsById(value);

    }

}
