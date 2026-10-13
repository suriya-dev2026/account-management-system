package com.accountmanagement.controller.v1;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.constants.message.SubscriptionMessage;
import com.accountmanagement.model.SubscriptionFeature;
import com.accountmanagement.model.SubscriptionOrganization;
import com.accountmanagement.model.SubscriptionPayment;
import com.accountmanagement.model.SubscriptionPlan;
import com.accountmanagement.model.SubscriptionPlanFeature;
import com.accountmanagement.request.SubscriptionFeatureRequest;
import com.accountmanagement.request.SubscriptionPlanFeatureRequest;
import com.accountmanagement.request.UpdateSubscriptionPlanFeatureRequest;
import com.accountmanagement.response.ApiResponse;
import com.accountmanagement.service.SubscriptionFeatureService;
import com.accountmanagement.service.SubscriptionOrganizationService;
import com.accountmanagement.service.SubscriptionPaymentService;
import com.accountmanagement.service.SubscriptionPlanFeatureService;
import com.accountmanagement.service.SubscriptionPlanService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/v1/subscription")
@Tag(name = "SubscriptionController")
@RequiredArgsConstructor
public class SubscriptionController {

        private final SubscriptionPlanService subscriptionPlanService;

        private final SubscriptionFeatureService subscriptionFeatureService;

        private final SubscriptionPlanFeatureService subscriptionPlanFeatureService;

        private final SubscriptionOrganizationService subscriptionOrganizationService;

        private final SubscriptionPaymentService subscriptionPaymentService;

        @GetMapping("/plan/view")
        public ResponseEntity<ApiResponse> viewAll() {
                List<SubscriptionPlan> subscriptionPlan = subscriptionPlanService.viewAllSubscriptionPlan();
                ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                                SubscriptionMessage.VIEW_ALL_SUBCRIPTION_PLAN,
                                200);
                response.setData(subscriptionPlan);
                return new ResponseEntity<>(response, HttpStatus.OK);
        }

        @PostMapping("/feature/create")
        public ResponseEntity<ApiResponse> addSubcriptionFeature(
                        @Valid @RequestBody SubscriptionFeatureRequest subscriptionFeatureRequest) {
                subscriptionFeatureRequest.sanitizeInput();
                subscriptionFeatureService.addSubscriptionFeature(subscriptionFeatureRequest);
                ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                                SubscriptionMessage.ADD_SUBSCRIPTION_FEATURE,
                                201);
                return new ResponseEntity<>(response, HttpStatus.CREATED);
        }

        @PutMapping("/feature/update/{id}")
        public ResponseEntity<ApiResponse> updateSubcriptionFeature(@PathVariable UUID id,
                        @Valid @RequestBody SubscriptionFeatureRequest subscriptionFeatureRequest) {
                subscriptionFeatureRequest.sanitizeInput();
                subscriptionFeatureService.updateSubscriptionFeature(id, subscriptionFeatureRequest);
                ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                                SubscriptionMessage.UPDATE_SUBSCRIPTION_FEATURE,
                                200);
                return new ResponseEntity<>(response, HttpStatus.OK);
        }

        @DeleteMapping("/feature/delete/{id}")
        public ResponseEntity<ApiResponse> deleteSubcriptionFeatureById(@PathVariable UUID id) {
                subscriptionFeatureService.deleteSubscriptionFeatureById(id);
                ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                                SubscriptionMessage.DELETE_SUBSCRIPTION_FEATURE,
                                200);
                return new ResponseEntity<>(response, HttpStatus.OK);
        }

        @GetMapping("/feature/view")
        public ResponseEntity<ApiResponse> viewAllFeatures() {
                List<SubscriptionFeature> subscriptionFeature = subscriptionFeatureService
                                .viewAllSubscriptionFeatures();
                ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                                SubscriptionMessage.VIEW_ALL_SUBCRIPTION_FEATURE,
                                200);
                response.setData(subscriptionFeature);
                return new ResponseEntity<>(response, HttpStatus.OK);
        }

        @PostMapping("/plan/feature/create")
        public ResponseEntity<ApiResponse> addSubscriptionPlanFeature(
                        @Valid @RequestBody SubscriptionPlanFeatureRequest subscriptionPlanFeatureRequest) {
                subscriptionPlanFeatureService.addSubscriptionPlanFeature(subscriptionPlanFeatureRequest);
                ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                                SubscriptionMessage.ADD_SUBSCRIPTION_PLAN_FEATURE,
                                201);
                return new ResponseEntity<>(response, HttpStatus.CREATED);
        }

        @PutMapping("/plan/feature/update/{planId}")
        public ResponseEntity<ApiResponse> updateSubscriptionPlanFeature(@PathVariable UUID planId,
                        @Valid @RequestBody UpdateSubscriptionPlanFeatureRequest updateSubscriptionPlanFeatureRequest) {
                subscriptionPlanFeatureService.updateSubscriptionPlanFeature(planId,
                                updateSubscriptionPlanFeatureRequest);
                ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                                SubscriptionMessage.UPDATE_SUBSCRIPTION_PLAN_FEATURE, 200);
                return new ResponseEntity<>(response, HttpStatus.OK);
        }

        @PutMapping("/plan/feature/delete/{id}")
        public ResponseEntity<ApiResponse> deleteSubscriptionPlanFeatureById(@PathVariable UUID id) {
                subscriptionPlanFeatureService.deleteSubscriptionPlanFeatureById(id);
                ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                                SubscriptionMessage.DELETE_SUBSCRIPTION_PLAN_FEATURE, 200);
                return new ResponseEntity<>(response, HttpStatus.OK);
        }

        @GetMapping("/plan/feature/view")
        public ResponseEntity<ApiResponse> viewAllSubscriptionPlanFeatures() {
                List<SubscriptionPlanFeature> subscriptionPlanFeature = subscriptionPlanFeatureService
                                .viewAllSubscriptionPlanFeature();
                ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                                SubscriptionMessage.ADD_SUBSCRIPTION_PLAN_FEATURE,
                                201);
                response.setData(subscriptionPlanFeature);
                return new ResponseEntity<>(response, HttpStatus.OK);
        }

        @GetMapping("/organization/view")
        public ResponseEntity<ApiResponse> viewAllSubscriptionOrganization() {
                List<SubscriptionOrganization> subscriptionOrganization = subscriptionOrganizationService
                                .viewAllSubscriptionOrganization();
                ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                                SubscriptionMessage.VIEW_ALL_SUBSCRIPTION_ORGANIZATION, 200);
                response.setData(subscriptionOrganization);
                return new ResponseEntity<>(response, HttpStatus.OK);
        }

        @GetMapping("/payment/view")
        public ResponseEntity<ApiResponse> viewAllSubscriptionPayment() {
                List<SubscriptionPayment> subscriptionPayment = subscriptionPaymentService.viewAllSubscriptionPayment();
                ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                                SubscriptionMessage.VIEW_ALL_SUBSCRIPTION_PAYMENT,
                                200);
                response.setData(subscriptionPayment);
                return new ResponseEntity<>(response, HttpStatus.OK);
        }
}
