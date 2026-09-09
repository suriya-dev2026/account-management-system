package com.accountmanagement.service;

import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.constants.message.EventMessage;
import com.accountmanagement.exceptions.DuplicateRecordException;
import com.accountmanagement.exceptions.RecordNotFoundException;
import com.accountmanagement.mapper.EventMapper;
import com.accountmanagement.model.Event;
import com.accountmanagement.repository.EventRepository;
import com.accountmanagement.request.EventRequest;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    public EventService(EventRepository eventRepository, EventMapper eventMapper) {
        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
    }

    public Event createEvent(EventRequest eventRequest) {
        validateEvent(eventRequest);
        Event event = eventMapper.toCreateEvent(eventRequest);
        return eventRepository.save(event);
    }

    public Event updateEvent(UUID id, EventRequest eventRequest) {
        Event event = findByEventId(id);
        Event updatedEvent = eventMapper.toUpdateEvent(event, eventRequest);
        return eventRepository.save(updatedEvent);
    }

    public void deleteEventById(UUID id) {
        Event event = findByEventId(id);
        event.setStatus(AppConstants.INACTIVE);
        eventRepository.save(event);
    }

    public List<Event> viewAllEvent() {
        return eventRepository.findAll();
    }

    public Event findByEventId(UUID id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(EventMessage.EVENT_ID_NOT_FOUND));
    }

    private void validateEventName(String eventName) {
        if (eventRepository
                .existsByEventNameIgnoreCase(eventName.trim())) {
            throw new DuplicateRecordException(
                    EventMessage.EVENT_NAME_EXISTS);
        }
    }

    private void validateEventType(String eventType) {
        if (eventRepository
                .existsByEventTypeIgnoreCase(eventType.trim())) {
            throw new DuplicateRecordException(
                    EventMessage.EVENT_TYPE_EXISTS);
        }
    }

    public void validateEvent(EventRequest eventRequest) {
        validateEventName(eventRequest.getEventName());
        validateEventType(eventRequest.getEventType());
    }
}
