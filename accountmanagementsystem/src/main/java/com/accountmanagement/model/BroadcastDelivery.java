package com.accountmanagement.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import org.hibernate.annotations.UuidGenerator;
import com.accountmanagement.enums.DeliveryStatus;
import com.accountmanagement.model.listeners.BroadcastDeliveryListener;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "broadcast_deliveries")
@Data
@EntityListeners(BroadcastDeliveryListener.class)
public class BroadcastDelivery {

    @Id
    @UuidGenerator
    @Column(name = "id")
    private UUID id;

    @Column(name = "broadcast_id")
    private UUID broadcastId;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "delivery_status")
    private DeliveryStatus deliveryStatus;

    @Column(name = "sent_to")
    private String sentTo;

    @Column(name = "sent_at")
    private LocalDate sentAt;

    @Column(name = "status")
    private String status;

    @JsonIgnore
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @JsonIgnore
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
