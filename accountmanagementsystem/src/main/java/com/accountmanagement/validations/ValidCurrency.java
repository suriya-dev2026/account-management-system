package com.accountmanagement.validations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import com.accountmanagement.validators.CurrencyValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = CurrencyValidator.class)
@Retention(RUNTIME)
@Target({ FIELD, METHOD })
public @interface ValidCurrency {

    public String message() default "Currency must be a valid 3-letter ISO code (e.g. INR, USD, EUR)";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};

}
