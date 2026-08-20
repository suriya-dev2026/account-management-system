package com.accountmanagement.validations;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import com.accountmanagement.validators.TeacherIdValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Target({ TYPE, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = TeacherIdValidator.class)
public @interface ValidTeacherId {

    public String message() default "Sunday School Teacher id does not exists";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};

}
