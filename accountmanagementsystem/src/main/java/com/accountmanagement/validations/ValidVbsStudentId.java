package com.accountmanagement.validations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import com.accountmanagement.validators.VbsStudentIdValidator;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = VbsStudentIdValidator.class)
@Retention(RUNTIME)
@Target({ FIELD, METHOD })
public @interface ValidVbsStudentId {

    public String message() default "Invalid Vbs Student Id";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};

}
