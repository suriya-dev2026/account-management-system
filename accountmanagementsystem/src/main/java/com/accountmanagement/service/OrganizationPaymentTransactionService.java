package com.accountmanagement.service;

import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.accountmanagement.constants.message.OrganizationPaymentTransactionMessage;
import com.accountmanagement.enums.GatewayStatus;
import com.accountmanagement.exceptions.RecordNotFoundException;
import com.accountmanagement.model.OrganizationPaymentTransaction;
import com.accountmanagement.repository.OrganizationPaymentTransactionRespository;
import com.accountmanagement.request.OrganizationPaymentTransactionRequest;
import com.accountmanagement.request.PaymentGatewayRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrganizationPaymentTransactionService {

    private final OrganizationPaymentTransactionRespository organizationPaymentTransactionRespository;

    @Transactional
    public OrganizationPaymentTransaction createPaymentTransaction(
            OrganizationPaymentTransactionRequest request) {
        OrganizationPaymentTransaction transaction = new OrganizationPaymentTransaction();
        transaction.setOrganizationId(request.getOrganizationId());
        transaction.setGateway(request.getGateway());
        transaction.setAmount(request.getAmount());
        transaction.setCurrency(request.getCurrency());
        transaction.setPaymentTime(LocalDateTime.now());
        transaction.setTransactionId(generateTransactionId());
        transaction.setVerified(false);
        transaction.setStatus(GatewayStatus.PENDING);
        return organizationPaymentTransactionRespository.save(transaction);
    }

    private String generateTransactionId() {
        return "PAY-" + UUID.randomUUID().toString().replace("-", "").substring(0, 12).toUpperCase();
    }

    @Transactional
    public OrganizationPaymentTransaction updatePaymentResponse(String transactionId, PaymentGatewayRequest request) {
        OrganizationPaymentTransaction transaction = organizationPaymentTransactionRespository
                .findByTransactionId(transactionId)
                .orElseThrow(() -> new RecordNotFoundException(
                        OrganizationPaymentTransactionMessage.ORGANIZATION_PAYMENT_TRANSACTION_ID_NOT_FOUND));
        transaction.setGatewayReference(request.getGatewayReference());
        transaction.setResponseJson(request.getResponseJson());
        transaction.setStatus(GatewayStatus.SUCCESS);
        transaction.setVerified(true);
        return organizationPaymentTransactionRespository.save(transaction);
    }

}
