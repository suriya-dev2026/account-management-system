package com.accountmanagement.mapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;
import com.accountmanagement.model.SubscriptionPayment;
import com.accountmanagement.request.SubscriptionPaymentRequest;

@Component
public class SubscriptionPaymentMapper {

    public SubscriptionPayment toCreateSubscriptionPayment(SubscriptionPaymentRequest subscriptionPaymentRequest,
            BigDecimal amount, String currency, BigDecimal billingAmount) {

        SubscriptionPayment subscriptionPayment = new SubscriptionPayment();
        subscriptionPayment.setSubscriptionOrganizationId(subscriptionPaymentRequest.getSubscriptionOrganizationId());
        subscriptionPayment.setPaymentType(subscriptionPaymentRequest.getPaymentType());
        subscriptionPayment.setAmount(amount);
        subscriptionPayment.setDiscountPercentage(subscriptionPaymentRequest.getDiscountPercentage());
        subscriptionPayment.setCurrency(currency);
        subscriptionPayment.setBillingAmount(billingAmount);
        subscriptionPayment.setPaidAt(LocalDateTime.now());
        return subscriptionPayment;
    }

}
