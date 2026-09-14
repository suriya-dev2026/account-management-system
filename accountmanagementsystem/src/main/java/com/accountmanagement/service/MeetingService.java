package com.accountmanagement.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.accountmanagement.exceptions.RecordNotFoundException;
import com.accountmanagement.mapper.MeetingMapper;
import com.accountmanagement.model.Meeting;
import com.accountmanagement.repository.MeetingRepository;
import com.accountmanagement.request.MeetingRequest;
import com.accountmanagement.request.MeetingUpdateRequest;


@Service
public class MeetingService {

    private final MeetingRepository meetingRepository;

    private final MeetingMapper meetingMapper;

    public MeetingService(MeetingRepository meetingRepository, MeetingMapper meetingMapper) {
        this.meetingRepository = meetingRepository;
        this.meetingMapper = meetingMapper;
    }

    public Meeting createMeeting(MeetingRequest meetingRequest) {
        Meeting meeting = meetingMapper.toCreateMeeting(meetingRequest);
        return meetingRepository.save(meeting);
    }

    public Meeting updateMeetingById(Integer id, MeetingUpdateRequest meetingUpdateRequest) {
        Meeting meeting = findMeetingById(id);
        Meeting updatedMeeting = meetingMapper.toUpdateMeeting(meeting, meetingUpdateRequest);
        return meetingRepository.save(updatedMeeting);
    }

    public void deleteMeetingById(Integer id) {
        findMeetingById(id);
        meetingRepository.deleteById(id);
    }

    public List<Meeting> viewAllMeetings() {
        return meetingRepository.findAll();
    }

    public Meeting findMeetingById(Integer id) {
        return meetingRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Meeting Id Not Found"));
    }
}
