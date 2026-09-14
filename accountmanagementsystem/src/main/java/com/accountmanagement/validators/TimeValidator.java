package com.accountmanagement.validators;

import com.accountmanagement.request.MeetingRequest;
import com.accountmanagement.validations.ValidTime;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TimeValidator implements ConstraintValidator<ValidTime, MeetingRequest> {

    @Override
    public boolean isValid(MeetingRequest request, ConstraintValidatorContext arg1) {

        if (request.getStartTime() == null || request.getEndTime() == null) {
            return true;
        }
        return request.getEndTime().isAfter(request.getStartTime());
    }

}
