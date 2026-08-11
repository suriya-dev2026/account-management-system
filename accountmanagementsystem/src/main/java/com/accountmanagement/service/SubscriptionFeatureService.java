package com.accountmanagement.service;

import com.accountmanagement.request.SubscriptionFeatureRequest;
import java.util.List;
import java.util.UUID;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.exceptions.DuplicateRecordException;
import com.accountmanagement.exceptions.RecordNotFoundException;
import com.accountmanagement.mapper.SubscriptionFeatureMapper;
import com.accountmanagement.model.SubscriptionFeature;
import com.accountmanagement.repository.SubscriptionFeatureRepository;

@Service
public class SubscriptionFeatureService {

    private final SubscriptionFeatureRepository subscriptionFeatureRepository;

    private final SubscriptionFeatureMapper subscriptionFeatureMapper;

    private final JdbcTemplate jdbcTemplate;

    public SubscriptionFeatureService(SubscriptionFeatureRepository subscriptionFeatureRepository,
            SubscriptionFeatureMapper subscriptionFeatureMapper, JdbcTemplate jdbcTemplate) {
        this.subscriptionFeatureRepository = subscriptionFeatureRepository;
        this.subscriptionFeatureMapper = subscriptionFeatureMapper;
        this.jdbcTemplate = jdbcTemplate;
    }

    public SubscriptionFeature addSubscriptionFeature(SubscriptionFeatureRequest subscriptionFeatureRequest) {
        validateSubscriptionFeature(subscriptionFeatureRequest.getName());
        String code = generateSubscriptionFeatureCode();
        SubscriptionFeature subscriptionFeature = subscriptionFeatureMapper
                .toCreateSubscriptionFeature(code, subscriptionFeatureRequest);
        return subscriptionFeatureRepository.save(subscriptionFeature);
    }

    public SubscriptionFeature updateSubscriptionFeature(UUID id,
            SubscriptionFeatureRequest subscriptionFeatureRequest) {
        SubscriptionFeature subscriptionFeature = findBySubcriptionFeatureId(id);
        SubscriptionFeature updatedSubscriptionFeature = subscriptionFeatureMapper
                .toUpdateSubscriptionFeature(subscriptionFeature, subscriptionFeatureRequest);
        return subscriptionFeatureRepository.save(updatedSubscriptionFeature);
    }

    public void deleteSubscriptionFeatureById(UUID id) {
        SubscriptionFeature subscriptionFeature = findBySubcriptionFeatureId(id);
        subscriptionFeature.setStatus(AppConstants.INACTIVE);
        subscriptionFeatureRepository.save(subscriptionFeature);
    }

    public List<SubscriptionFeature> viewAllSubscriptionFeature() {
        return subscriptionFeatureRepository.findAll();
    }

    public SubscriptionFeature findBySubcriptionFeatureId(UUID id) {
        return subscriptionFeatureRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Subscription Feature id not found"));
    }

    public String generateSubscriptionFeatureCode() {
        Long nextValue = jdbcTemplate.queryForObject(
                "SELECT nextval('subscription_feature_code_seq')", Long.class);
        return (String.format("SF-%05d", nextValue));
    }

    private void validateSubscriptionFeature(String name) {
        if (subscriptionFeatureRepository
                .existsByNameIgnoreCase(name.trim())) {
            throw new DuplicateRecordException(
                    "subscription feature already exists");
        }
    }

}
