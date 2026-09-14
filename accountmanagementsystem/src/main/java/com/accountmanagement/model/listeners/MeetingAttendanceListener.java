package com.accountmanagement.model.listeners;

import java.time.LocalDateTime;
import com.accountmanagement.model.MeetingAttendance;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;

@Data
public class MeetingAttendanceListener {

    @PrePersist
    public void onCreateMeetingAttendance(MeetingAttendance meetingAttendance) {
        meetingAttendance.setCreatedAt(LocalDateTime.now());
    }

    @PreUpdate
    public void onUpdateMeetingAttendance(MeetingAttendance meetingAttendance) {
        meetingAttendance.setUpdatedAt(LocalDateTime.now());
    }

}
