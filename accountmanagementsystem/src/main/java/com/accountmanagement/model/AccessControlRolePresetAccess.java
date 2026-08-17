package com.accountmanagement.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.accountmanagement.model.listeners.AccessControlRolePresetAccessListener;
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
@Table(name = "access_control_role_preset_access")
@Data
@EntityListeners(AccessControlRolePresetAccessListener.class)
public class AccessControlRolePresetAccess {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "role_id")
    private UUID roleId;

    @Column(name = "module_preset_id")
    private Integer modulePresetId;

    @Column(name = "status")
    private String status;

    @JsonIgnore
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @JsonIgnore
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
