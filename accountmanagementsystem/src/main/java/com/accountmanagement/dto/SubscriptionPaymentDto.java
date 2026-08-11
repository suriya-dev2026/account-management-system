package com.accountmanagement.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.accountmanagement.enums.PaymentStatus;
import com.accountmanagement.enums.PaymentType;

import lombok.Data;

@Data
public class SubscriptionPaymentDto {

    private String paymentReference;

    private String transactionId;

    private PaymentType paymentType;

    private BigDecimal billingAmount;

    private String currency;

    private PaymentStatus status;

    private LocalDateTime paidAt;
}
