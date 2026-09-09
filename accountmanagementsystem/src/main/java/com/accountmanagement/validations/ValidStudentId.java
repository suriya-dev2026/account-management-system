package com.accountmanagement.validations;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.accountmanagement.validators.StudentIdValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = StudentIdValidator.class)
@Retention(RUNTIME)
@Target({ FIELD, METHOD })
public @interface ValidStudentId {

    public String message() default "Sunday School Student Id does not exists";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};

}
