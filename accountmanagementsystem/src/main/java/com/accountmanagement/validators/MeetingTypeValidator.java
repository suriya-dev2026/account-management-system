package com.accountmanagement.validators;

import com.accountmanagement.repository.MeetingTypeRepository;
import com.accountmanagement.validations.ValidMeetingTypeId;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MeetingTypeValidator implements ConstraintValidator<ValidMeetingTypeId, Integer> {

    private final MeetingTypeRepository meetingTypeRepository;

    public MeetingTypeValidator(MeetingTypeRepository meetingTypeRepository) {
        this.meetingTypeRepository = meetingTypeRepository;
    }

    @Override
    public boolean isValid(Integer id, ConstraintValidatorContext arg1) {
        if (id == null) {
            return true;
        }
        return meetingTypeRepository.existsById(id);
    }

}
