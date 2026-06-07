package com.accountmanagement.validations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import com.accountmanagement.validators.UserPhoneNumberValidator;

@Constraint(validatedBy = UserPhoneNumberValidator.class)
@Retention(RUNTIME)
@Target({ FIELD, METHOD })
public @interface ValidUserPhoneNumber {

    public String message() default "phone number already exists";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};
}
