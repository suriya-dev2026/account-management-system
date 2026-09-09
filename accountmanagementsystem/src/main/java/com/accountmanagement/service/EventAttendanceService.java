package com.accountmanagement.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.springframework.stereotype.Service;
import com.accountmanagement.constants.message.EventAttendanceMessage;
import com.accountmanagement.exceptions.DuplicateRecordException;
import com.accountmanagement.exceptions.RecordNotFoundException;
import com.accountmanagement.model.EventAttendance;
import com.accountmanagement.repository.EventAttendanceRepository;
import com.accountmanagement.request.AttendanceUpdateRequest;
import com.accountmanagement.request.EventAttendanceList;
import com.accountmanagement.request.EventAttendanceRequest;

@Service 
public class EventAttendanceService {

    private final EventAttendanceRepository eventAttendanceRepository;

    public EventAttendanceService(EventAttendanceRepository eventAttendanceRepository){
        this.eventAttendanceRepository = eventAttendanceRepository;
    }

    public List<EventAttendance> createEventAttendance(EventAttendanceRequest eventAttendanceRequest){
        List<EventAttendance> attendanceList = new ArrayList<>();
        Set<UUID> eventParticipantIds = new HashSet<>();
        for (EventAttendanceList item : eventAttendanceRequest.getAttendanceList()) {
            if (!eventParticipantIds.add(item.getEventParticipantId())) {
                throw new DuplicateRecordException(
                        EventAttendanceMessage.DUPLICATE_EVENT_PARTICIPANT_ID);
            }
            if (eventAttendanceRepository.existsByOrganizationIdAndEventParticipantIdAndAttendanceDate(
                    eventAttendanceRequest.getOrganizationId(), item.getEventParticipantId(), eventAttendanceRequest.getAttendanceDate())) {
                throw new DuplicateRecordException(
                        EventAttendanceMessage.EVENT_ATTENDANCE_EXISTS);
            }
            EventAttendance attendance = new EventAttendance();
            attendance.setOrganizationId(eventAttendanceRequest.getOrganizationId());
            attendance.setEventId(eventAttendanceRequest.getEventId());
            attendance.setEventParticipantId(item.getEventParticipantId());
            attendance.setAttendanceDate(eventAttendanceRequest.getAttendanceDate());
            attendance.setAttendanceStatus(item.getAttendanceStatus());
            attendanceList.add(attendance);
        }
        return eventAttendanceRepository.saveAll(attendanceList);
    }

    public EventAttendance updateEventAttendance(UUID id, AttendanceUpdateRequest request){
        EventAttendance eventAttendance = findEventAttendanceById(id);
        eventAttendance.setAttendanceStatus(request.getAttendanceStatus());
        return eventAttendanceRepository.save(eventAttendance);
    }

    public List<EventAttendance> viewAllEventAttendance(){
        return eventAttendanceRepository.findAll();
    }

    public void deleteEventAttendanceById(UUID id){
        findEventAttendanceById(id);
        eventAttendanceRepository.deleteById(id);
    }

    public EventAttendance findEventAttendanceById(UUID id){
        return  eventAttendanceRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(EventAttendanceMessage.EVENT_ATTENDANCE_ID_NOT_FOUND));
    }

}
