package com.accountmanagement.validators;

import java.util.UUID;
import com.accountmanagement.repository.EventParticipantRepository;
import com.accountmanagement.validations.ValidEventParticipantId;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EventParticipantIdValidator implements ConstraintValidator<ValidEventParticipantId, UUID> {

    private final EventParticipantRepository eventParticipantRepository;

    public EventParticipantIdValidator(EventParticipantRepository eventParticipantRepository) {
        this.eventParticipantRepository = eventParticipantRepository;
    }

    @Override
    public boolean isValid(UUID id, ConstraintValidatorContext arg1) {
        if (id == null) {
            return true;
        }
        return eventParticipantRepository.existsById(id);
    }

}
