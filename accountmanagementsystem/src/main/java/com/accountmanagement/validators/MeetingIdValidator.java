package com.accountmanagement.validators;

import com.accountmanagement.repository.MeetingRepository;
import com.accountmanagement.validations.ValidMeeting;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MeetingIdValidator implements ConstraintValidator<ValidMeeting, Integer> {

    private final MeetingRepository meetingRepository;

    public MeetingIdValidator(MeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;
    }

    @Override
    public boolean isValid(Integer id, ConstraintValidatorContext arg1) {
        if (id == null) {
            return true;
        }
        return meetingRepository.existsById(id);
    }

}
