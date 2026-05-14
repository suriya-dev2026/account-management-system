package com.accountmanagement.validations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import com.accountmanagement.validators.PhoneValidator;

@Constraint(validatedBy = PhoneValidator.class)
@Retention(RUNTIME)
@Target({ FIELD, METHOD })
public @interface ValidPhone {

    public String message() default "Invalid gender";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};

}
