package com.app.beanvalidation;

import com.app.beanvalidation.groups.BankTransferPaymentGroup;
import com.app.beanvalidation.groups.CreditCardPaymentGroup;
import com.app.beanvalidation.model.BankDetail;
import com.app.beanvalidation.validator.ValidatorFactorySingleton;
import com.app.beanvalidation.model.PaymentDetail;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class BeanValidationCommandLineRunner implements CommandLineRunner {
    // using dependency injection method
    // private final Validator validator;

    // @Autowired
    // public BeanValidationCommandLineRunner(Validator validator) {
    //     this.validator = null;
    // }

    @Override
    public void run(String... args) throws Exception {
        Validator validator = getValidator();

        // constraintViolationDemo(validator);
        // nestedObjectValidationDemo(validator);
        constraintGroupDemo(validator);
    }

    private Validator getValidator() {
        ValidatorFactory validatorFactory = ValidatorFactorySingleton.buildOrGetValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        return validator;
    }

    private <T> Set<ConstraintViolation<T>> validate(Validator validator, T object) {
        return validator.validate(object);
    }

    private <T> void printConstraintViolations(Set<ConstraintViolation<T>> constraintViolationSet) {
        for (ConstraintViolation<T> constraintViolation : constraintViolationSet) {
            // space between each row
            System.out.println("+++++++++++++++++++++");

            // Print the constraint message
            // Amount must be between 100 and 10,000
            System.out.println(constraintViolation.getMessage());

            // PaymentDetail(orderId=ABCDEFHGIJKLMNOPQRSTUVWXYZ, amount=1, creditCardNumber=123)
            System.out.println(constraintViolation.getLeafBean());

            // Example annotation value
            // @org.hibernate.validator.constraints.Range(groups={}, min=100L, message="Amount must be between 100 and 10,000", payload={}, max=10000L)
            System.out.println(constraintViolation.getConstraintDescriptor().getAnnotation());

            // print the actual value (the invalid value)
            System.out.println(constraintViolation.getInvalidValue());
        }
    }

    private void constraintViolationDemo(Validator validator) {
        // There are 2 libraries we can use to validate our model
        // Bean Validation and Hibernate Validator already provides common constraint that we could use
        PaymentDetail paymentDetail = PaymentDetail.builder()
                // The maximum length of an order ID is 10
                .orderId("ABCDEFHGIJKLMNOPQRSTUVWXYZ")
                // Amount must be between 100 and 10,000
                .amount(1L)
                // Invalid credit card number
                .creditCardNumber("123")
                .build();

        // expect 3 constraint violations here
        Set<ConstraintViolation<PaymentDetail>> constraintViolationSet = validate(validator, paymentDetail);
        printConstraintViolations(constraintViolationSet);
    }

    private void nestedObjectValidationDemo(Validator validator) {
        PaymentDetail paymentDetail = PaymentDetail.builder()
                .orderId("123")
                .amount(111L)
                .creditCardNumber("4111111111111111")
                // Bank name field inside the bankDetail model is blank
                .bankDetail(new BankDetail())
                .build();

        // expect 1 constraint violation here
        Set<ConstraintViolation<PaymentDetail>> constraintViolationSet = validate(validator, paymentDetail);
        printConstraintViolations(constraintViolationSet);
    }

    private void constraintGroupDemo(Validator validator) {
        PaymentDetail paymentDetail = PaymentDetail.builder()
                .orderId("123")
                .amount(111L)
                // put an invalid credit card information
                .creditCardNumber("invalid-credit-card-number")
                // put an invalid bank account number
                .bankAccountNumber(null)
                .bankDetail(BankDetail.builder()
                        .bankName("bank-name")
                        .build())
                .build();

        // expect 1 constraint violation here because of invalid credit card information
        // the second argument is not required, if we leave it empty, it will validate using Default group
        // But if we specify a specific group class, it will validate using the specific group that we define, and won't include Default group anymore unless we do specify
        Set<ConstraintViolation<PaymentDetail>> constraintViolationCreditCardGroupSet = validator.validate(paymentDetail, CreditCardPaymentGroup.class);
        printConstraintViolations(constraintViolationCreditCardGroupSet);

        // expect 1 constraint violation here because of bank account number information
        Set<ConstraintViolation<PaymentDetail>> constraintViolationBankTransferSet = validator.validate(paymentDetail, BankTransferPaymentGroup.class);
        printConstraintViolations(constraintViolationBankTransferSet);
    }
}
