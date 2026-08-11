package com.accountmanagement.model;

import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "access_control_roles")
@Data
public class AccessControlRoles {

    @Id
    @UuidGenerator
    @Column(name = "id")
    private UUID id;

    private String roleName;

    private String description;

    private String status;

}
