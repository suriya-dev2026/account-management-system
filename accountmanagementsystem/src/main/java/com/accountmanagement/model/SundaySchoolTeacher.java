package com.accountmanagement.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import org.hibernate.annotations.UuidGenerator;
import jakarta.persistence.Id;
import com.accountmanagement.model.listeners.SundaySchoolTeacherListener;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "sunday_school_teachers")
@Data
@EntityListeners(SundaySchoolTeacherListener.class)
public class SundaySchoolTeacher {

    @Id
    @UuidGenerator
    @Column(name = "id")
    private UUID Id;

    @Column(name = "organization_id")
    private UUID organizationId;

    @Column(name = "member_id")
    private UUID memberId;

    @Column(name = "class_id")
    private UUID classId;

    @Column(name = "date_of_join")
    private LocalDate dateOfJoin;

    @Column(name = "status")
    private String status;

    @JsonIgnore
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @JsonIgnore
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
