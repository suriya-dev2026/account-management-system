package com.accountmanagement.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import com.accountmanagement.enums.Gender;
import com.accountmanagement.model.listeners.VbsStudentListener;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "vbs_students")
@Data
@EntityListeners(VbsStudentListener.class)
public class VbsStudent {

    @Id
    @UuidGenerator
    @Column(name = "id")
    private UUID id;

    @Column(name = "organization_id")
    private UUID organizationId;

    @Column(name = "vbs_year_id")
    private Integer vbsYearId;

    @Column(name = "vbs_class_id")
    private Integer vbsClassId;

    @Column(name = "member_id")
    private UUID memberId;

    @Column(name = "student_name")
    private String studentName;

    @Column(name = "gender")
    private Gender gender;

    @Column(name = "contact_number")
    private String contactNumber;

    @Column(name = "emergency_contact_number")
    private String emergencyContactNumber;

    @Column(name = "emergency_contact_person")
    private String emergencyContactPerson;

    @Column(name = "address")
    private String address;

    @Column(name = "status")
    private String status;

    @JsonIgnore
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @JsonIgnore
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
