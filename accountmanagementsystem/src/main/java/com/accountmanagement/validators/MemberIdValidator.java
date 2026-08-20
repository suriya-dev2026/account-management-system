package com.accountmanagement.validators;

import java.util.UUID;

import com.accountmanagement.repository.MemberRepository;
import com.accountmanagement.validations.ValidMemberId;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MemberIdValidator implements ConstraintValidator<ValidMemberId, UUID> {

    private MemberRepository memberRepository;

    public MemberIdValidator(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public boolean isValid(UUID id, ConstraintValidatorContext arg1) {
        if (id == null) {
            return false;
        }
        return memberRepository.existsById(id);
    }

}
