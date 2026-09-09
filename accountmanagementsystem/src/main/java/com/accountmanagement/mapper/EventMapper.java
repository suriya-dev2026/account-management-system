package com.accountmanagement.mapper;

import org.springframework.stereotype.Component;

import com.accountmanagement.model.Event;
import com.accountmanagement.request.EventRequest;

@Component
public class EventMapper {

    public Event toCreateEvent(EventRequest eventRequest) {
        Event event = new Event();
        event.setOrganizationId(eventRequest.getOrganizationId());
        event.setEventName(eventRequest.getEventName());
        event.setEventType(eventRequest.getEventType());
        event.setStartDate(eventRequest.getStartDate());
        event.setEndDate(eventRequest.getEndDate());
        event.setLocation(eventRequest.getLocation());
        event.setDescription(eventRequest.getDescription());
        return event;
    }

    public Event toUpdateEvent(Event event, EventRequest eventRequest) {
        event.setOrganizationId(eventRequest.getOrganizationId());
        event.setEventName(eventRequest.getEventName());
        event.setEventType(eventRequest.getEventType());
        event.setStartDate(eventRequest.getStartDate());
        event.setEndDate(eventRequest.getEndDate());
        event.setLocation(eventRequest.getLocation());
        event.setDescription(eventRequest.getDescription());
        return event;
    }
}
