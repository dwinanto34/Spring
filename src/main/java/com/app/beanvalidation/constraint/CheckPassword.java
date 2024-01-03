package com.app.beanvalidation.constraint;

import com.app.beanvalidation.validator.CheckPasswordValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(
        validatedBy = {CheckPasswordValidator.class}
)
// ElementType.TYPE indicates that it can be applied to a class, interface, abstract class, enum, etc
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckPassword {
    String message() default "[Default] Password and retype password does not match";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}