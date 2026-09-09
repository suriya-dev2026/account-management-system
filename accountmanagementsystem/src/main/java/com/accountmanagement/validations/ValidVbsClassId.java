package com.accountmanagement.validations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import com.accountmanagement.validators.VbsClassIdValidator;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = VbsClassIdValidator.class)
@Retention(RUNTIME)
@Target({ FIELD, METHOD })
public @interface ValidVbsClassId {

    public String message() default "Invalid Vbs Class Id";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};

}
