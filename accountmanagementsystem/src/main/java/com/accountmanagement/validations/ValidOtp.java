package com.accountmanagement.validations;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import com.accountmanagement.validators.OtpValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Target({ TYPE, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = OtpValidator.class)
public @interface ValidOtp {

	public String message() default "otp must be 6 digits only";

	public Class<?>[] groups() default {};

	public Class<? extends Payload>[] payload() default {};

}
