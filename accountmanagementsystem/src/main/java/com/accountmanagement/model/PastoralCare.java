package com.accountmanagement.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import org.hibernate.annotations.UuidGenerator;
import com.accountmanagement.model.listeners.PastoralCareListener;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "pastoral_care")
@Data
@EntityListeners(PastoralCareListener.class)
public class PastoralCare {

    @Id
    @UuidGenerator
    @Column(name = "id")
    private UUID id;

    @Column(name = "organization_id", nullable = false)
    private UUID organizationId;

    @Column(name = "member_id")
    private UUID memberId;

    @Column(name = "visitor_name")
    private String visitorName;

    @Column(name = "visitor_contact_number")
    private String visitorContactNumber;

    @Column(name = "visit_date")
    private LocalDate visitDate;

    @Column(name = "visit_type")
    private String visitType;

    @Column(name = "notes")
    private String notes;

    @Column(name = "status")
    private String status;

    @JsonIgnore
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @JsonIgnore
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
