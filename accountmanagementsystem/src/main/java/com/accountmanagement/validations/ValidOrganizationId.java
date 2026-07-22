package com.accountmanagement.validations;

import java.lang.annotation.Retention;

import com.accountmanagement.validators.OrganizationIdValidator;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Target;

@Constraint(validatedBy = OrganizationIdValidator.class)
@Retention(RUNTIME)
@Target({ FIELD, METHOD })
public @interface ValidOrganizationId {

    public String message() default "Invalid organization id";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};
}
