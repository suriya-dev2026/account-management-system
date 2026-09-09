package com.accountmanagement.validators;

import com.accountmanagement.request.EventRequest;
import com.accountmanagement.validations.ValidEndDate;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EndDateValidator implements ConstraintValidator<ValidEndDate, EventRequest> {

    @Override
    public boolean isValid(EventRequest request, ConstraintValidatorContext context) {

        if (request == null ||
                request.getStartDate() == null ||
                request.getEndDate() == null) {
            return true;
        }
        if (request.getEndDate().isBefore(request.getStartDate())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    context.getDefaultConstraintMessageTemplate())
                    .addPropertyNode("endDate")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }

}
