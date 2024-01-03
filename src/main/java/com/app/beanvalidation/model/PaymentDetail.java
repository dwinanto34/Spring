package com.app.beanvalidation.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.Range;

@Data
@Builder
public class PaymentDetail {
    // Bean validation already provides a lot of common validation constraints that we could use
    // Like NotBlank, NotNull, Size, etc
    // https://jakarta.ee/specifications/bean-validation/3.0/apidocs/jakarta/validation/constraints/package-summary
    @NotBlank(message = "Order ID cannot be blank")
    @Size(max = 10, message = "The maximum length of an order ID is 10")
    private String orderId;

    // Other than Bean Validation, Hibernate Validator also provides additional validation constraints
    // Like Range, CreditCardNumber, etc
    // https://docs.jboss.org/hibernate/stable/validator/api/org/hibernate/validator/constraints/package-summary.html
    @NotNull(message = "Order amount cannot be null")
    @Range(min = 100, max = 10000, message = "Amount must be between 100 and 10,000")
    private Long amount;

    @NotNull(message = "Credit card number cannot be blank")
    @CreditCardNumber(message = "Invalid credit card number")
    private String creditCardNumber;

    // By default, Bean Validation won't automatically validate the fields within a nested object.
    // To enable validation for the fields inside a nested object, use the @Valid annotation.
    @Valid
    @NotNull(message = "Bank detail cannot be null")
    private BankDetail bankDetail;
}