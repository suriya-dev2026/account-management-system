package com.accountmanagement.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.accountmanagement.model.listeners.AccessControlUserRoleListener;
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
@Table(name = "access_control_user_roles")
@Data
@EntityListeners(AccessControlUserRoleListener.class)
public class AccessControlUserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "role_id")
    private UUID roleId;

    @Column(name = "status")
    private String status;

    @JsonIgnore
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @JsonIgnore
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
