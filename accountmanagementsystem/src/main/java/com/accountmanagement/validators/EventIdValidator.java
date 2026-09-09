package com.accountmanagement.validators;

import java.util.UUID;
import com.accountmanagement.repository.EventRepository;
import com.accountmanagement.validations.ValidEventId;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EventIdValidator implements ConstraintValidator<ValidEventId, UUID> {

    private final EventRepository eventRepository;

    public EventIdValidator(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public boolean isValid(UUID id, ConstraintValidatorContext arg1) {
        if (id == null) {
            return true;
        }
        return eventRepository.existsById(id);
    }

}
