package com.app.beanvalidation.constraint;

import com.app.beanvalidation.enums.CaseMode;
import com.app.beanvalidation.validator.CheckCaseValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(
    validatedBy = {CheckCaseValidator.class}
)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckCase {
    CaseMode mode();

    // below three methods are template and mandatory to be there
    String message() default "Invalid case";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}