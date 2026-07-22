package com.accountmanagement.model;

import java.time.LocalDateTime;
import java.util.UUID;
import org.hibernate.annotations.UuidGenerator;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "user_login_audit_log")
public class UserLoginAuditLog {

    @Id
    @UuidGenerator
    @Column(name = "id")
    private UUID id;

    @Column(name = "organization_id")
    private UUID organizationId;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "logged_time")
    private LocalDateTime loggedTime;

    @Column(name = "action")
    private String action;

    @Column(name = "logged_ip")
    private String loggedIp;

    @Column(name = "logged_browser")
    private String loggedBrowser;

    @Column(name = "os_version")
    private String osVersion;

    @Column(name = "is_mobile")
    private Boolean isMobile;

    @Column(name = "attempted_username")
    private String attemptedUsername;

    @Column(name = "attempted_password")
    private String attemptedPassword;

    @Column(name = "returned_result")
    private String returnedResult;

}
