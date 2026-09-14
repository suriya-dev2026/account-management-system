package com.accountmanagement.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.springframework.stereotype.Service;
import com.accountmanagement.exceptions.DuplicateRecordException;
import com.accountmanagement.exceptions.RecordNotFoundException;
import com.accountmanagement.model.MeetingAttendance;
import com.accountmanagement.repository.MeetingAttendanceRepository;
import com.accountmanagement.request.AttendanceUpdateRequest;
import com.accountmanagement.request.MeetingAttendanceList;
import com.accountmanagement.request.MeetingAttendanceRequest;

@Service
public class MeetingAttendanceService {

    private final MeetingAttendanceRepository meetingAttendanceRepository;

    public MeetingAttendanceService(MeetingAttendanceRepository meetingAttendanceRepository) {
        this.meetingAttendanceRepository = meetingAttendanceRepository;
    }

    public List<MeetingAttendance> createMeetingAttendance(MeetingAttendanceRequest request) {
        List<MeetingAttendance> attendanceList = new ArrayList<>();
        Set<UUID> memberIds = new HashSet<>();
        for (MeetingAttendanceList item : request.getAttendanceList()) {
            if (!memberIds.add(item.getMemberId())) {
                throw new DuplicateRecordException(
                        "Duplicate Member Id Found In The Request.");
            }
            if (meetingAttendanceRepository.existsByOrganizationIdAndMemberIdAndAttendanceDate(
                    request.getOrganizationId(), item.getMemberId(), request.getAttendanceDate())) {
                throw new DuplicateRecordException(
                        "Attendance Record already exists for this Member.");
            }
            MeetingAttendance attendance = new MeetingAttendance();
            attendance.setOrganizationId(request.getOrganizationId());
            attendance.setMeetingId(request.getMeetingId());
            attendance.setMemberId(item.getMemberId());
            attendance.setAttendanceDate(request.getAttendanceDate());
            attendance.setAttendanceStatus(item.getAttendanceStatus());
            attendanceList.add(attendance);
        }
        return meetingAttendanceRepository.saveAll(attendanceList);
    }

    public MeetingAttendance updateMeetingAttendanceById(UUID id,
            AttendanceUpdateRequest meetingAttendanceUpdateRequest) {
        MeetingAttendance meetingAttendance = findMeetingAttendanceById(id);
        meetingAttendance.setAttendanceStatus(meetingAttendanceUpdateRequest.getAttendanceStatus());
        return meetingAttendanceRepository.save(meetingAttendance);
    }

    public void deleteMeetingAttendanceById(UUID id) {
        findMeetingAttendanceById(id);
        meetingAttendanceRepository.deleteById(id);
    }

    public List<MeetingAttendance> viewAllMeetingAttendances() {
        return meetingAttendanceRepository.findAll();
    }

    public MeetingAttendance findMeetingAttendanceById(UUID id) {
        return meetingAttendanceRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Meeting Attendance Id Not Found"));
    }
}
