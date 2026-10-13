package com.accountmanagement.validators;

import java.time.LocalDate;

import com.accountmanagement.request.VbsYearRequest;
import com.accountmanagement.validations.ValidVbsDate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class VbsDateValidator implements ConstraintValidator<ValidVbsDate, VbsYearRequest> {

    @Override
    public boolean isValid(VbsYearRequest request, ConstraintValidatorContext context) {

        if (request == null) {
            return true;
        }
        LocalDate startDate = request.getStartDate();
        LocalDate endDate = request.getEndDate();

        if (startDate != null &&
                (startDate.isBefore(LocalDate.of(1900, 1, 1))
                        || startDate.isAfter(LocalDate.of(2100, 12, 31)))) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "Start date must be between 1900-01-01 and 2100-12-31")
                    .addPropertyNode("startDate")
                    .addConstraintViolation();
            return false;
        }
        if (endDate != null &&
                (endDate.isBefore(LocalDate.of(1900, 1, 1))
                        || endDate.isAfter(LocalDate.of(2100, 12, 31)))) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "End date must be between 1900-01-01 and 2100-12-31")
                    .addPropertyNode("endDate")
                    .addConstraintViolation();
            return false;
        }
        if (startDate != null && endDate != null
                && startDate.isAfter(endDate)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "End date must be after or equal to start date")
                    .addPropertyNode("endDate")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }

}
