package com.accountmanagement.request;

import lombok.Data;

@Data
public class SubscriptionPlanRequest {

    
    private String name;

    private String description;

    private String billingCycle;

    private Double price;

    private String currency;

    private Integer trialDays;

    private Integer maxStudents;

    private Integer maxTeachers;

    private Integer maxAdmin;

    private Double discountPercentage;

}
