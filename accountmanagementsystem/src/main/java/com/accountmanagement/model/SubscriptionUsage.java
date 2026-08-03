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
@Table(name = "subscription_usages")
@Data
public class SubscriptionUsage {

    @Id
    @UuidGenerator
    @Column(name = "id")
    private UUID id;

    @Column(name = "subscription_organization_id")
    private UUID subscriptionOrganizationId;

    @Column(name = "current_members")
    private Integer currentMembers;

    @Column(name = "current_teachers")
    private Integer currentTeachers;

    @Column(name = "current_admins")
    private Integer currentAdmins;

    @Column(name = "storage_user_mb")
    private Integer storageUserMb;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
