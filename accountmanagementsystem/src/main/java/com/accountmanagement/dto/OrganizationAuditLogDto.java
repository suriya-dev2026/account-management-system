package com.accountmanagement.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Data;

@Data
public class OrganizationAuditLogDto {

    private UUID id;

    private String organizationCode;

    private UUID userId;

    private LocalDateTime loggedtime;

    private String entityName;

    private String entityPk;

    private String actionName;

    private Object existingValue;

    private Object updatedValue;

    private String remarks;

    private String ipAddress;

}
