package com.accountmanagement.validations;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.accountmanagement.validators.UUIDValidator;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = UUIDValidator.class)
@Retention(RUNTIME)
@Target({ FIELD, METHOD })
public @interface ValidUUID {

    public String message() default "Unrecognized ID, please enter valid id. ";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};
}
