package com.accountmanagement.mapper;

import org.springframework.stereotype.Component;
import com.accountmanagement.model.EventParticipant;
import com.accountmanagement.model.Member;
import com.accountmanagement.request.EventParticipantRequest;
import com.accountmanagement.request.EventParticipantUpdateRequest;

@Component
public class EventParticipantMapper {

    public EventParticipant toCreateEventParticipant(Member member, EventParticipantRequest eventParticipantRequest) {
        EventParticipant eventParticipant = new EventParticipant();
        eventParticipant.setOrganizationId(eventParticipantRequest.getOrganizationId());
        eventParticipant.setEventId(eventParticipantRequest.getEventId());
        eventParticipant.setMemberId(eventParticipantRequest.getMemberId());
        eventParticipant.setSchoolGrade(eventParticipantRequest.getSchoolGrade());
        eventParticipant.setEmergencyContactName(eventParticipantRequest.getEmergencyContactName());
        eventParticipant.setEmergencyContactNumber(eventParticipantRequest.getEmergencyContactNumber());
        if (member != null) {
            eventParticipant.setFullName(member.getUserName());
            eventParticipant.setDateOfBirth(member.getDateOfBirth());
            eventParticipant.setAddress(member.getAddress());
            eventParticipant.setContactNumber(member.getContactNumber());
        } else {
            eventParticipant.setFullName(eventParticipantRequest.getFullName());
            eventParticipant.setDateOfBirth(eventParticipantRequest.getDateOfBirth());
            eventParticipant.setAddress(eventParticipantRequest.getAddress());
            eventParticipant.setContactNumber(eventParticipantRequest.getContactNumber());
        }
        return eventParticipant;
    }

    public EventParticipant toUpdateEventParticipant(EventParticipant eventParticipant,
            EventParticipantUpdateRequest eventParticipantUpdateRequest) {
        eventParticipant.setEventId(eventParticipantUpdateRequest.getEventId());
        eventParticipant.setContactNumber(eventParticipantUpdateRequest.getContactNumber());
        eventParticipant.setEmergencyContactName(eventParticipantUpdateRequest.getEmergencyContactName());
        eventParticipant.setEmergencyContactNumber(eventParticipantUpdateRequest.getEmergencyContactNumber());
        return eventParticipant;
    }
}
