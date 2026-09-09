package com.accountmanagement.model;

import java.time.LocalDateTime;
import java.util.UUID;
import com.accountmanagement.model.listeners.VbsClassListener;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "vbs_classes")
@Data
@EntityListeners(VbsClassListener.class)
public class VbsClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "organization_id")
    private UUID organizationId;

    @Column(name = "class_name")
    private String className;

    @Column(name = "year_id")
    private Integer yearId;

    @Column(name = "teacher_id")
    private UUID teacherId;

    @Column(name = "status")
    private String status;

    @JsonIgnore
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @JsonIgnore
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
