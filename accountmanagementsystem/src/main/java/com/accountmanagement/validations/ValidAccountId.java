package com.accountmanagement.validations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import com.accountmanagement.validators.AccountIdValidator;

@Constraint(validatedBy = AccountIdValidator.class)
@Retention(RUNTIME)
@Target({ FIELD, METHOD })
public @interface ValidAccountId {

    public String message() default "Invalid Account Id";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};
}
