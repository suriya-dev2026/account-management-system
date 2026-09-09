package com.accountmanagement.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import org.hibernate.annotations.UuidGenerator;
import com.accountmanagement.enums.AttendanceStatus;
import com.accountmanagement.model.listeners.StaffAttendanceListener;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "staff_attendance")
@EntityListeners(StaffAttendanceListener.class)
public class StaffAttendance {

    @Id
    @UuidGenerator
    @Column(name = "id")
    private UUID id;

    private UUID organizationId;

    private UUID staffId;

    private LocalDate attendanceDate;

    private AttendanceStatus attendanceStatus;

    @JsonIgnore
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @JsonIgnore
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
