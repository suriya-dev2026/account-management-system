package com.accountmanagement.validations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import com.accountmanagement.validators.YearIdValidator;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = YearIdValidator.class)
@Retention(RUNTIME)
@Target({ FIELD, METHOD })
public @interface ValidYearId {

    public String message() default "Invalid Year Id";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};

}
