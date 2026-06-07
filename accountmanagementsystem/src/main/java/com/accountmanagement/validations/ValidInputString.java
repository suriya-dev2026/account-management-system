package com.accountmanagement.validations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.accountmanagement.validators.InputStringValidator;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = InputStringValidator.class)
@Retention(RUNTIME)
@Target({ FIELD, METHOD })
public @interface ValidInputString {

    public String message() default "Name must contain only alphabets and be between 3 and 50 characters";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};

    public boolean allowNull() default false;

    public boolean alphaOnly() default false;

}
