package com.accountmanagement.validations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.accountmanagement.validators.UserIdValidator;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.lang.annotation.ElementType.FIELD;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = UserIdValidator.class)
@Retention(RUNTIME)
@Target({ FIELD, METHOD })
public @interface ValidUserId {

    public String message() default "User Id Does Not Exists";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};
}
