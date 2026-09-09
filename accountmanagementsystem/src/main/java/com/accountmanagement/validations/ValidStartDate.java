package com.accountmanagement.validations;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import com.accountmanagement.validators.StartDateValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = StartDateValidator.class)
@Retention(RUNTIME)
@Target({ FIELD, METHOD })
public @interface ValidStartDate {

    public String message() default "Invalid Start Date";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};

}
