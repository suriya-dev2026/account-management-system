package com.accountmanagement.validators;

import java.util.UUID;

import com.accountmanagement.repository.AccountingAccountRepository;
import com.accountmanagement.validations.ValidAccountId;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AccountIdValidator implements ConstraintValidator<ValidAccountId, UUID> {

    private final AccountingAccountRepository accountingAccountRepository;

    @Override
    public boolean isValid(UUID id, ConstraintValidatorContext arg1) {
        if (id == null) {
            return true;
        }
        return accountingAccountRepository.existsById(id);
    }

}
