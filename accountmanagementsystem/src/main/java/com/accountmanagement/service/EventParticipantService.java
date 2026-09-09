package com.accountmanagement.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.constants.message.EventParticipantMessage;
import com.accountmanagement.exceptions.RecordNotFoundException;
import com.accountmanagement.exceptions.UserAlreadyExistsException;
import com.accountmanagement.mapper.EventParticipantMapper;
import com.accountmanagement.model.EventParticipant;
import com.accountmanagement.model.Member;
import com.accountmanagement.repository.EventParticipantRepository;
import com.accountmanagement.repository.MemberRepository;
import com.accountmanagement.request.EventParticipantRequest;
import com.accountmanagement.request.EventParticipantUpdateRequest;

@Service
public class EventParticipantService {

    private final EventParticipantRepository eventParticipantRepository;

    private final MemberRepository memberRepository;

    private final EventParticipantMapper eventParticipantMapper;

    public EventParticipantService(EventParticipantRepository eventParticipantRepository,
            MemberRepository memberRepository, EventParticipantMapper eventParticipantMapper) {
        this.eventParticipantRepository = eventParticipantRepository;
        this.memberRepository = memberRepository;
        this.eventParticipantMapper = eventParticipantMapper;
    }

    public EventParticipant createEventParticipant(EventParticipantRequest eventParticipantRequest) {
        validateEventParticipant(eventParticipantRequest);
        Member member = null;
        if (eventParticipantRequest.getMemberId() != null) {
            member = memberRepository.findById(eventParticipantRequest.getMemberId())
                    .orElseThrow(() -> new RecordNotFoundException("Member Id Not Found"));
        }
        EventParticipant eventParticipant = eventParticipantMapper.toCreateEventParticipant(member,
                eventParticipantRequest);
        return eventParticipantRepository.save(eventParticipant);
    }

    public EventParticipant updateEventParticipant(UUID id,
            EventParticipantUpdateRequest eventParticipantUpdateRequest) {
        EventParticipant eventParticipant = findEventParticipantById(id);
        EventParticipant updatedEventParticipant = eventParticipantMapper.toUpdateEventParticipant(eventParticipant,
                eventParticipantUpdateRequest);
        return eventParticipantRepository.save(updatedEventParticipant);
    }

    public void deleteEventParticipantById(UUID id) {
        EventParticipant eventParticipant = findEventParticipantById(id);
        eventParticipant.setStatus(AppConstants.INACTIVE);
        eventParticipantRepository.save(eventParticipant);
    }

    public List<EventParticipant> viewAllEventParticipant() {
        return eventParticipantRepository.findAll();
    }

    public EventParticipant findEventParticipantById(UUID id) {
        return eventParticipantRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(EventParticipantMessage.EVENT_PARTICIPANT_ID_NOT_FOUND));
    }

    public void validateEventParticipant(EventParticipantRequest eventParticipantRequest) {
        boolean exists = eventParticipantRepository.existsByEventIdAndFullNameAndContactNumber(
                eventParticipantRequest.getEventId(),
                eventParticipantRequest.getFullName(),
                eventParticipantRequest.getContactNumber());
        if (exists) {
            throw new UserAlreadyExistsException("Participant Already Added To This Event");
        }
    }
}
