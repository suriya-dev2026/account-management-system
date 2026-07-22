package com.accountmanagement.validations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.accountmanagement.validators.LoginValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = LoginValidator.class)
@Retention(RUNTIME)
@Target({ FIELD, METHOD })

public @interface ValidLogin {

    public String message() default "Invalid username or email or contact number";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};
}
