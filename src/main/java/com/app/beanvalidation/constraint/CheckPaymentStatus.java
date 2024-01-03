package com.app.beanvalidation.constraint;

import com.app.beanvalidation.enums.CaseMode;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.groups.Default;

import java.lang.annotation.*;

// Here we have 3 constraints: NotNull, Size, and CheckCase
// All those 3 constraints could be combined into a single annotation
// This feature called constraint composition
@NotNull(groups = {Default.class}, message = "Payment status cannot be blank")
@Size(min = 1, max = 10, message = "Size of order status must between 1 to 10")
@CheckCase(mode = CaseMode.UPPER)
@Documented
@Constraint(
        // leave it empty
        validatedBy = {}
)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)

// By default, Java will run all the constraints and report all the violations.
// If you want to merge the violations and use a single error message,
// you can add this annotation, and the violation message that will be shown is the one specified below.
@ReportAsSingleViolation
public @interface CheckPaymentStatus {
    String message() default "Order status is invalid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}