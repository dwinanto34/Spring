package com.app.beanvalidation;

import com.app.beanvalidation.groups.BankTransferPaymentGroup;
import com.app.beanvalidation.groups.CreditCardPaymentGroup;
import com.app.beanvalidation.groups.PaymentGroup;
import com.app.beanvalidation.model.BankDetail;
import com.app.beanvalidation.payload.EmailErrorPayload;
import com.app.beanvalidation.validator.ValidatorFactorySingleton;
import com.app.beanvalidation.model.PaymentDetail;
import jakarta.validation.*;
import jakarta.validation.executable.ExecutableValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
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
        // constraintGroupDemo(validator);
        // sequenceGroupDemo(validator);
        // conversionGroupDemo(validator);
        // payloadDemo(validator);
        // methodValidationDemo(validator);
        // constructorValidationDemo(validator);
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

    private void sequenceGroupDemo(Validator validator) {
        PaymentDetail paymentDetail = PaymentDetail.builder()
                // The maximum length of an order ID is 10
                .orderId("ABCDEFHGIJKLMNOPQRSTUVWXYZ")
                // Amount must be between 100 and 10,000
                .amount(1L)
                // Invalid credit card number
                .creditCardNumber("invalid-credit-card-number")
                .build();

        // Only print violations from the Default group.
        // Violations related to the amount, bank detail, and the length of the order ID will be displayed.
        // Other violations from non-Default groups won't be printed because the first sequence, Default group, already results in a violation, preventing the validation from proceeding to the next sequences.
        Set<ConstraintViolation<PaymentDetail>> constraintViolationSet = validator.validate(paymentDetail, PaymentGroup.class);
        printConstraintViolations(constraintViolationSet);
    }

    private void conversionGroupDemo(Validator validator) {
        PaymentDetail paymentDetail = PaymentDetail.builder()
                .orderId("123")
                .amount(111L)
                .creditCardNumber("4111111111111111")
                // Bank name field inside the bankDetail model is blank
                .bankDetail(new BankDetail())
                .build();

        // Even though the bank name field is empty and violates the constraint,
        // when validated using the CreditCardPaymentGroup class, no violations are found
        // because the bank name field inside the bankDetail model does not implement CreditCardPaymentGroup, but implements the Default group.
        // To obtain violations for the bank name field, there are two possible approaches:
        // 1. Add groups information and include CreditCardPaymentGroup for the bank name in bankDetail.
        // 2. Add a conversion from the PaymentDetail model, where the bankDetail field is converted from CreditCardPaymentGroup to Default.
        Set<ConstraintViolation<PaymentDetail>> constraintViolationSet = validator.validate(paymentDetail, CreditCardPaymentGroup.class);
        printConstraintViolations(constraintViolationSet);
    }

    private void payloadDemo(Validator validator) {
        PaymentDetail paymentDetail = PaymentDetail.builder()
                .orderId("123")
                .amount(111L)
                .creditCardNumber("4111111111111111")
                // Bank name field inside the bankDetail model is blank
                .bankDetail(new BankDetail())
                .build();

        Set<ConstraintViolation<PaymentDetail>> constraintViolationSet = validator.validate(paymentDetail, CreditCardPaymentGroup.class);
        for (ConstraintViolation<PaymentDetail> paymentDetailConstraintViolation : constraintViolationSet) {
            Set<Class<? extends Payload>> payloadClassSet = paymentDetailConstraintViolation.getConstraintDescriptor().getPayload();
            for(Class<? extends Payload> aClass : payloadClassSet) {
                if (aClass == EmailErrorPayload.class) {
                    EmailErrorPayload errorPayload = new EmailErrorPayload();
                    errorPayload.sendEmail(paymentDetailConstraintViolation);
                }
            }
        }
    }

    private void methodValidationDemo(Validator validator) throws NoSuchMethodException {
        ExecutableValidator executableValidator = validator.forExecutables();
        PaymentDetail paymentDetail;
        Method method;
        Set<ConstraintViolation<PaymentDetail>> constraintViolationSet;

        // 1. Validate the printName method with an empty name argument
        paymentDetail = new PaymentDetail();
        method = paymentDetail.getClass().getMethod("printName", String.class);
        String name = "";

        // expect 1 violation because the name argument is an empty string
        constraintViolationSet = executableValidator
                .validateParameters(paymentDetail, method, new Object[]{name});
        printConstraintViolations(constraintViolationSet);

        // 2. Validate the return value of a method
        paymentDetail = PaymentDetail.builder()
                .amount(1L)
                .build();
        method = paymentDetail.getClass().getMethod("calculateFee");
        Long fee = paymentDetail.calculateFee();

        // expect 1 violation because the fee response is 2, which is not between 10 and 100
        constraintViolationSet = executableValidator
                .validateReturnValue(paymentDetail, method, fee);
        printConstraintViolations(constraintViolationSet);
    }

    private void constructorValidationDemo(Validator validator) throws NoSuchMethodException {
        ExecutableValidator executableValidator = validator.forExecutables();
        Set<ConstraintViolation<PaymentDetail>> constraintViolationSet;

        // 1. Validate the arguments in constructor
        Constructor<PaymentDetail> paymentDetailConstructor = PaymentDetail.class
                .getConstructor(String.class);

        // expect 1 violation because the order ID is an empty string.
        constraintViolationSet = executableValidator
                .validateConstructorParameters(paymentDetailConstructor, new Object[]{""});
        printConstraintViolations(constraintViolationSet);

        // 2. Validate the return value in constructor
        PaymentDetail paymentDetail = new PaymentDetail("");
        Constructor<PaymentDetail> constructor = PaymentDetail.class
                .getConstructor(String.class);

        // expect few validations because the bank detail, the order amount, and the order ID is null
        constraintViolationSet = executableValidator
                .validateConstructorReturnValue(constructor, paymentDetail);
        printConstraintViolations(constraintViolationSet);
    }
}
