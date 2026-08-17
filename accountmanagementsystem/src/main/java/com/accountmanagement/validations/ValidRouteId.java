package com.accountmanagement.validations;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import com.accountmanagement.validators.RouteIdValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Target({ TYPE, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = RouteIdValidator.class)
public @interface ValidRouteId {

    public String message() default "Access Control Route Id does not exists";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};
}
