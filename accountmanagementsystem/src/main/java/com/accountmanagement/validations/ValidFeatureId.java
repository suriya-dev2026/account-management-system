package com.accountmanagement.validations;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import com.accountmanagement.validators.FeatureIdValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Target({ TYPE, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = FeatureIdValidator.class)
public @interface ValidFeatureId {

    public String message() default "Subscription Feature Id Does Not Exists";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};

}
