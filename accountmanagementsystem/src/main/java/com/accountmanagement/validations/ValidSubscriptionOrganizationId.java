package com.accountmanagement.validations;

import com.accountmanagement.validators.SubscriptionOrganizationIdValidator;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Target;

@Constraint(validatedBy = SubscriptionOrganizationIdValidator.class)
@Retention(RUNTIME)
@Target({ FIELD, METHOD })
public @interface ValidSubscriptionOrganizationId {

    public String message() default "Invalid subscription organization id";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};
}
