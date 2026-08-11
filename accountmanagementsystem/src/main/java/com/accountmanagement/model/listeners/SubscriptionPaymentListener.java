package com.accountmanagement.model.listeners;

import java.time.LocalDateTime;
import com.accountmanagement.model.SubscriptionPayment;
import jakarta.persistence.PrePersist;
import lombok.Data;

@Data
public class SubscriptionPaymentListener {

    @PrePersist
    public void onCreate(SubscriptionPayment subscriptionPayment) {
        subscriptionPayment.setCreatedAt(LocalDateTime.now());
    }
}
