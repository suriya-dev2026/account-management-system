package com.accountmanagement.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.accountmanagement.model.SubscriptionFeature;
import com.accountmanagement.repository.SubscriptionFeatureRepository;

@Service
public class SubscriptionFeatureService {

    private final SubscriptionFeatureRepository subscriptionFeatureRepository;

    public SubscriptionFeatureService(SubscriptionFeatureRepository subscriptionFeatureRepository){
        this.subscriptionFeatureRepository = subscriptionFeatureRepository;
    }

    public List<SubscriptionFeature> viewAllSubscriptionFeature(){
        return subscriptionFeatureRepository.findAll();
    }
}
