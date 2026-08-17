package com.accountmanagement.model;

import java.time.LocalDateTime;
import java.util.UUID;
import org.hibernate.annotations.UuidGenerator;
import com.accountmanagement.model.listeners.AccessControlRoleListener;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "access_control_roles")
@Data
@EntityListeners(AccessControlRoleListener.class)
public class AccessControlRole {

    @Id
    @UuidGenerator
    @Column(name = "id")
    private UUID id;

    @Column(name = "role_name")
    private String roleName;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private String status;

    @JsonIgnore
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @JsonIgnore
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
