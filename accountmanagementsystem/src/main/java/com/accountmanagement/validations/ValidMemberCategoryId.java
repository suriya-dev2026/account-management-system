package com.accountmanagement.validations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import com.accountmanagement.validators.MemberCategoryIdValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = MemberCategoryIdValidator.class)
@Retention(RUNTIME)
@Target({ FIELD, METHOD })
public @interface ValidMemberCategoryId {

    public String message() default "Invalid member category id";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};

}
