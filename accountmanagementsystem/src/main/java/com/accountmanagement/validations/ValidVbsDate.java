package com.accountmanagement.validations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import com.accountmanagement.validators.VbsDateValidator;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.ElementType;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = VbsDateValidator.class)
@Retention(RUNTIME)
@Target(ElementType.TYPE)
public @interface ValidVbsDate {

    public String message() default "Invalid Vbs Date";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};

}
