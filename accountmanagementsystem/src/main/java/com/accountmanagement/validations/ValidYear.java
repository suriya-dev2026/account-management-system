package com.accountmanagement.validations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import com.accountmanagement.validators.YearValidator;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = YearValidator.class)
@Retention(RUNTIME)
@Target({ FIELD, METHOD })
public @interface ValidYear {

    public String message() default "Invalid Year";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};

}
