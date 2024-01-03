package com.app.beanvalidation.constraint;

import com.app.beanvalidation.validator.CheckPasswordParameterValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(
        validatedBy = {CheckPasswordParameterValidator.class}
)
@Target({ElementType.METHOD, ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckPasswordParameter {
    int passwordParam();
    int retypePasswordParam();

    String message() default "Password does not match from parameter / constructor";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}