package com.accountmanagement.mapper;

import org.springframework.stereotype.Component;

import com.accountmanagement.model.Meeting;
import com.accountmanagement.request.MeetingRequest;
import com.accountmanagement.request.MeetingUpdateRequest;

@Component
public class MeetingMapper {

    public Meeting toCreateMeeting(MeetingRequest meetingRequest) {
        Meeting meeting = new Meeting();
        meeting.setOrganizationId(meetingRequest.getOrganizationId());
        meeting.setMeetingTypeId(meetingRequest.getMeetingTypeId());
        meeting.setMeetingDate(meetingRequest.getMeetingDate());
        meeting.setStartTime(meetingRequest.getStartTime());
        meeting.setEndTime(meetingRequest.getEndTime());
        meeting.setDescription(meetingRequest.getDescription());
        return meeting;
    }

    public Meeting toUpdateMeeting(Meeting meeting, MeetingUpdateRequest request) {
        meeting.setMeetingTypeId(request.getMeetingTypeId());
        meeting.setMeetingDate(request.getMeetingDate());
        meeting.setStartTime(request.getStartTime());
        meeting.setEndTime(request.getEndTime());
        meeting.setDescription(request.getDescription());
        return meeting;
    }
}
