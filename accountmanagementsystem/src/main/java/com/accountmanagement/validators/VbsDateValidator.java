package com.accountmanagement.validators;

import java.time.LocalDate;

import com.accountmanagement.request.VbsYearRequest;
import com.accountmanagement.validations.ValidVbsDate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class VbsDateValidator implements ConstraintValidator<ValidVbsDate, VbsYearRequest> {

    @Override
    public boolean isValid(VbsYearRequest request, ConstraintValidatorContext arg1) {

        if (request == null) {
            return false;
        }
        LocalDate startDate = request.getStartDate();
        LocalDate endDate = request.getEndDate();

        if (startDate != null) {
            if (startDate.isBefore(LocalDate.of(1900, 1, 1))
                    || startDate.isAfter(LocalDate.of(2100, 12, 31))) {
                return false;
            }
        }

        if (endDate != null) {
            if (endDate.isBefore(LocalDate.of(1900, 1, 1))
                    || endDate.isAfter(LocalDate.of(2100, 12, 31))) {
                return false;
            }
        }

        if (startDate != null && endDate != null
                && startDate.isAfter(endDate)) {
            return false;
        }

        return true;
    }

}
