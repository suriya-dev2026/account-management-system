package com.accountmanagement.validations;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import com.accountmanagement.validators.PlanIdValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Target({ TYPE, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = PlanIdValidator.class)
public @interface ValidPlanId {

    public String message() default "Subscription plan id does not exists";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};

}
