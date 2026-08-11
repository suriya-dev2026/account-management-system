package com.accountmanagement.validations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import com.accountmanagement.validators.LocationIdValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = LocationIdValidator.class)
@Retention(RUNTIME)
@Target({ FIELD, METHOD })
public @interface ValidLocationId {

    public String message() default "Invalid location id";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};

}
