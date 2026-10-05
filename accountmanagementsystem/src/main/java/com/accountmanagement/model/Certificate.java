package com.accountmanagement.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import org.hibernate.annotations.UuidGenerator;
import com.accountmanagement.model.listeners.CertificateListener;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "certificates")
@Data
@EntityListeners(CertificateListener.class)
public class Certificate {

    @Id
    @UuidGenerator
    @Column(name = "id")
    private UUID id;

    @Column(name = "organization_id")
    private UUID organizationId;

    @Column(name = "member_id")
    private UUID memberId;

    @Column(name = "certificate_type")
    private String certificateType;

    @Column(name = "member_name")
    private String memberName;

    @Column(name = "father_name")
    private String fatherName;

    @Column(name = "mother_name")
    private String motherName;

    @Column(name = "issue_date")
    private LocalDate issueDate;

    @Column(name = "event_date")
    private LocalDate eventDate;

    @Column(name = "issued_by")
    private String issuedBy;

    @Column(name = "certificate_number")
    private String certificateNumber;

    @Column(name = "status")
    private String status;

    @JsonIgnore
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @JsonIgnore
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
