package com.app.beanvalidation.model;

import com.app.beanvalidation.groups.BankTransferPaymentGroup;
import com.app.beanvalidation.groups.CreditCardPaymentGroup;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.groups.ConvertGroup;
import jakarta.validation.groups.Default;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.Range;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDetail {
    // Bean validation already provides a lot of common validation constraints that we could use
    // Like NotBlank, NotNull, Size, etc
    // https://jakarta.ee/specifications/bean-validation/3.0/apidocs/jakarta/validation/constraints/package-summary
    @Size(max = 10, message = "The maximum length of an order ID is 10")
    // By default, if we don't specify constraint group, it will be assigned Default.class
    @NotBlank(groups = {Default.class}, message = "Order ID cannot be blank")
    private String orderId;

    // Other than Bean Validation, Hibernate Validator also provides additional validation constraints
    // Like Range, CreditCardNumber, etc
    // https://docs.jboss.org/hibernate/stable/validator/api/org/hibernate/validator/constraints/package-summary.html
    @NotNull(groups = {Default.class}, message = "Order amount cannot be null")
    // @Range(min = 100, max = 10000, message = "Amount must be between 100 and 10,000")
    // There are 2 ways to get the message interpolation for this constraint:
    // 1. Retrieve messages from the parameters, such as the 'min' and 'max' variables.
    // 2. Retrieve messages from the ValidationMessages.properties}file using a custom key.
    @Range(min = 100, max = 10000, message = "{order.amount.must.between} {min} and {max}")
    private Long amount;

    // We could also specify a custom constraint group class
    @NotNull(groups = {CreditCardPaymentGroup.class}, message = "Credit card number cannot be blank")
    @CreditCardNumber(groups = {CreditCardPaymentGroup.class}, message = "Invalid credit card number")
    private String creditCardNumber;

    @NotNull(groups = {BankTransferPaymentGroup.class}, message = "Bank account number cannot be null")
    private String bankAccountNumber;

    // By default, Bean Validation won't automatically validate the fields within a nested object.
    // To enable validation for the fields inside a nested object, use the @Valid annotation.
    @Valid
    @NotNull(groups = {Default.class}, message = "Bank detail cannot be null")
    // Converts the validation group from CreditCardPaymentGroup to Default.
    // When inside the bankDetail, the validation group is converted into the Default group.
    // @ConvertGroup annotation is used to specify this conversion during the validation process.
    @ConvertGroup(from = CreditCardPaymentGroup.class, to = Default.class)
    private BankDetail bankDetail;

    public void printName(@NotBlank(message = "Name argument cannot be blank") String name) {
        System.out.println("Do something:" + name);
    }

    @Range(min = 10, max = 100, message = "Fee amount reponse must between 10 and 100")
    public Long calculateFee() {
        return amount + 1;
    }

    @Valid
    public PaymentDetail(@NotBlank(message = "Constructor argument for order ID cannot be blank") String orderId) {
        this.orderId = orderId;
    }
}