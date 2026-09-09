package com.accountmanagement.service;

import com.accountmanagement.repository.SubscriptionOrganizationRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.accountmanagement.enums.AuditLogAction;
import com.accountmanagement.enums.BillingCycle;
import com.accountmanagement.enums.PaymentStatus;
import com.accountmanagement.enums.PaymentType;
import com.accountmanagement.enums.SubscriptionOrganizationStatus;
import com.accountmanagement.enums.UserType;
import com.accountmanagement.exceptions.BusinessException;
import com.accountmanagement.exceptions.RecordNotFoundException;
import com.accountmanagement.mapper.SubscriptionPaymentMapper;
import com.accountmanagement.model.SubscriptionOrganization;
import com.accountmanagement.model.SubscriptionPayment;
import com.accountmanagement.model.SubscriptionPlan;
import com.accountmanagement.model.User;
import com.accountmanagement.repository.SubscriptionPaymentRepository;
import com.accountmanagement.repository.UserRepository;
import com.accountmanagement.request.PaymentSuccessRequest;
import com.accountmanagement.request.SubscriptionPaymentRequest;

@Service
public class SubscriptionPaymentService {

    private final UserService userService;

    private final SubscriptionOrganizationRepository subscriptionOrganizationRepository;

    private final SubscriptionPaymentRepository subscriptionPaymentRepository;

    private final SubscriptionOrganizationService subscriptionOrganizationService;

    private final SubscriptionPlanService subscriptionPlanService;

    private final SubscriptionPaymentMapper subscriptionPaymentMapper;

    private final SubscriptionUsageService subscriptionUsageService;

    private final SubscriptionAuditLogService subscriptionAuditLogService;

    private final UserVerificationService userVerificationService;

    private final UserRepository userRepository;

    public SubscriptionPaymentService(SubscriptionPaymentRepository subscriptionPaymentRepository,
            SubscriptionOrganizationService subscriptionOrganizationService,
            SubscriptionPlanService subscriptionPlanService,
            SubscriptionOrganizationRepository subscriptionOrganizationRepository,
            SubscriptionPaymentMapper subscriptionPaymentMapper, SubscriptionUsageService subscriptionUsageService,
            SubscriptionAuditLogService subscriptionAuditLogService, UserVerificationService userVerificationService,
            UserRepository userRepository, UserService userService) {
        this.subscriptionPaymentRepository = subscriptionPaymentRepository;
        this.subscriptionOrganizationService = subscriptionOrganizationService;
        this.subscriptionPlanService = subscriptionPlanService;
        this.subscriptionOrganizationRepository = subscriptionOrganizationRepository;
        this.subscriptionPaymentMapper = subscriptionPaymentMapper;
        this.subscriptionUsageService = subscriptionUsageService;
        this.subscriptionAuditLogService = subscriptionAuditLogService;
        this.userVerificationService = userVerificationService;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Transactional
    public SubscriptionPayment createSubscriptionPayment(SubscriptionPaymentRequest request) {
        SubscriptionOrganization subscriptionOrganization = subscriptionOrganizationService
                .findBySubscriptionOrganizationId(
                        request.getSubscriptionOrganizationId());
        if (subscriptionOrganization.getStatus() == SubscriptionOrganizationStatus.ACTIVE) {
            throw new BusinessException(
                    "Subscription is already active");
        }
        boolean pendingPayment = subscriptionPaymentRepository
                .existsBySubscriptionOrganizationIdAndStatus(subscriptionOrganization.getId(), PaymentStatus.PENDING);

        if (pendingPayment) {
            throw new BusinessException("Payment is already in progress");
        }
        User user = userRepository
                .findByOrganizationIdAndUserType(subscriptionOrganization.getOrganizationId(), UserType.SUPERADMIN)
                .orElseThrow(() -> new RecordNotFoundException("User not found"));
        SubscriptionPlan plan = subscriptionPlanService.findBySubscriptionPlanId(
                subscriptionOrganization.getPlanId());
        BigDecimal billingAmount = calculateBillingAmount(plan.getPrice(), request.getDiscountPercentage());
        SubscriptionPayment payment = subscriptionPaymentMapper.toCreateSubscriptionPayment(request, plan.getPrice(),
                plan.getCurrency(), billingAmount);

        if (request.getPaymentType() == PaymentType.ONLINE) {
            payment.setStatus(PaymentStatus.PENDING);
            payment.setPaymentReference(generatePaymentReference());
            return subscriptionPaymentRepository.save(payment);
        }
        payment.setStatus(PaymentStatus.SUCCESS);
        payment.setPaidAt(LocalDateTime.now());
        SubscriptionPayment savedPayment = subscriptionPaymentRepository.save(payment);
        activateSubscription(subscriptionOrganization, plan, user);
        return savedPayment;
    }

    private void activateSubscription(SubscriptionOrganization subscriptionOrganization, SubscriptionPlan plan,
            User user) {
        if (subscriptionOrganization.getStatus() == SubscriptionOrganizationStatus.ACTIVE) {
            throw new BusinessException("Subscription is already active");
        }
        subscriptionOrganization.setStatus(SubscriptionOrganizationStatus.ACTIVE);
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = calculateEndDate(startDate,
                subscriptionOrganization.getBillingCycle());
        subscriptionOrganization.setStartDate(startDate);
        subscriptionOrganization.setEndDate(endDate);
        subscriptionOrganizationRepository.save(subscriptionOrganization);
        subscriptionUsageService.createSubscriptionUsage(subscriptionOrganization.getId());
        subscriptionAuditLogService.createSubscriptionAuditLog(subscriptionOrganization.getOrganizationId(),
                plan.getId(), AuditLogAction.SUBSCRIBED,
                "Subscription activated successfully");
        userVerificationService.completeSubscription(user.getId());
        userService.createTemporaryCredentials(user.getId());
    }

    @Transactional
    public SubscriptionPayment paymentSuccess(
            PaymentSuccessRequest request) {
        SubscriptionPayment payment = subscriptionPaymentRepository
                .findByPaymentReference(request.getPaymentReference())
                .orElseThrow(() -> new RecordNotFoundException("Payment not found"));
        if (payment.getStatus() == PaymentStatus.SUCCESS) {
            return payment;
        }
        payment.setTransactionId(request.getTransactionId());
        payment.setStatus(PaymentStatus.SUCCESS);
        payment.setPaidAt(LocalDateTime.now());
        SubscriptionPayment savedPayment = subscriptionPaymentRepository.save(payment);
        SubscriptionOrganization subscriptionOrganization = subscriptionOrganizationService
                .findBySubscriptionOrganizationId(
                        savedPayment.getSubscriptionOrganizationId());
        User user = userRepository.findByOrganizationIdAndUserType(
                subscriptionOrganization.getOrganizationId(), UserType.SUPERADMIN)
                .orElseThrow(() -> new RecordNotFoundException("User not found"));
        SubscriptionPlan plan = subscriptionPlanService.findBySubscriptionPlanId(
                subscriptionOrganization.getPlanId());
        activateSubscription(subscriptionOrganization, plan, user);
        return savedPayment;
    }

    private String generatePaymentReference() {
        return "PAY_" + UUID.randomUUID().toString().replace("-", "").substring(0,
                12).toUpperCase();
    }

    private BigDecimal calculateBillingAmount(BigDecimal amount, BigDecimal discountPercentage) {

        if (discountPercentage == null) {
            discountPercentage = BigDecimal.ZERO;
        }
        BigDecimal discountAmount = amount.multiply(discountPercentage)
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
        return amount.subtract(discountAmount);
    }

    private LocalDate calculateEndDate(LocalDate startDate, BillingCycle billingCycle) {
        switch (billingCycle) {
            case MONTHLY:
                return startDate.plusMonths(1);

            case YEARLY:
                return startDate.plusYears(1);

            default:
                throw new IllegalArgumentException("Invalid billing cycle");
        }
    }

    public SubscriptionPayment getByPaymentReference(String paymentReference) {
        return subscriptionPaymentRepository
                .findByPaymentReference(paymentReference)
                .orElseThrow(() -> new RecordNotFoundException("Payment not found"));
    }

    public List<SubscriptionPayment> viewAllSubscriptionPayment() {
        return subscriptionPaymentRepository.findAll();
    }

}
