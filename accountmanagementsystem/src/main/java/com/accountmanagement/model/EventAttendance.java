package com.accountmanagement.model;

import java.util.UUID;
import org.hibernate.annotations.UuidGenerator;

import com.accountmanagement.enums.AttendanceStatus;
import com.accountmanagement.model.listeners.EventAttendanceListener;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table (name = "event_attendance")
@Data
@EntityListeners (EventAttendanceListener.class)
public class EventAttendance {

    @Id
    @UuidGenerator
    @Column(name = "id")
    private UUID id;

    @Column (name = "organization_id")
    private UUID organizationId;

    @Column (name = "event_id")
    private UUID eventId;

    @Column (name = "event_participant_id")
    private UUID eventParticipantId;

    @Column (name = "attendance_date")
    private LocalDate attendanceDate;

    @Column (name = "attendance_status")
    private AttendanceStatus attendanceStatus;

    @JsonIgnore
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @JsonIgnore
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
