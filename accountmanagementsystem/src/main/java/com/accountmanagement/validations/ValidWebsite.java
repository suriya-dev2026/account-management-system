package com.accountmanagement.validations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.accountmanagement.validators.WebsiteValidator;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = WebsiteValidator.class)
@Retention(RUNTIME)
@Target({ FIELD, METHOD })
public @interface ValidWebsite {

    public String message() default "Invalid Website";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};
}
