package com.accountmanagement.validations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import com.accountmanagement.validators.TimeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TimeValidator.class)
public @interface ValidTime {

    public String message() default "Invalid End Date";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};

}
