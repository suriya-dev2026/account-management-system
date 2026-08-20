package com.accountmanagement.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import com.accountmanagement.model.listeners.SundaySchoolTransitionListener;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "sunday_school_transitions")
@Data
@EntityListeners(SundaySchoolTransitionListener.class)
public class SundaySchoolTransition {

    @Id
    @UuidGenerator
    @Column(name = "id")
    private UUID id;

    @Column(name = "student_id")
    private UUID studentId;

    @Column(name = "from_class_id")
    private UUID fromClassId;

    @Column(name = "to_class_id")
    private UUID toClassId;

    @Column(name = "transition_by")
    private UUID transitionBy;

    @Column(name = "transition_date")
    private LocalDate transitionDate;

    @JsonIgnore
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @JsonIgnore
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
