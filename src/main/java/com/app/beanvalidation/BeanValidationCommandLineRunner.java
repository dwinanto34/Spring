package com.app.beanvalidation;

import com.app.beanvalidation.container.*;
import com.app.beanvalidation.extractor.DataIntegerValueExtractor;
import com.app.beanvalidation.extractor.DataValueExtractor;
import com.app.beanvalidation.extractor.EntryValueExtractorKey;
import com.app.beanvalidation.extractor.EntryValueExtractorValue;
import com.app.beanvalidation.groups.BankTransferPaymentGroup;
import com.app.beanvalidation.groups.CreditCardPaymentGroup;
import com.app.beanvalidation.groups.PaymentGroup;
import com.app.beanvalidation.model.Account;
import com.app.beanvalidation.model.BankDetail;
import com.app.beanvalidation.payload.EmailErrorPayload;
import com.app.beanvalidation.validator.ValidatorFactorySingleton;
import com.app.beanvalidation.model.PaymentDetail;
import jakarta.validation.*;
import jakarta.validation.executable.ExecutableValidator;
import jakarta.validation.metadata.BeanDescriptor;
import jakarta.validation.metadata.ConstraintDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
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
        // customConstraintDemo(validator);
        // constraintCompositionDemo(validator);
        // classLevelConstraint(validator);
        // crossParametersConstraint(validator);
        // customValidatorContext(validator);
        // containerDataDemo(validator);
        // valueExtractionDemo();
        // constraintViolationException(validator);
        metadata(validator);
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

    private void customConstraintDemo(Validator validator) {
        PaymentDetail paymentDetail = new PaymentDetail();
        paymentDetail.setOrderId("lowercase");
        // expect violation error for order ID because it is in lower case
        Set<ConstraintViolation<PaymentDetail>> constraintViolationSet = validator.validate(paymentDetail);
        printConstraintViolations(constraintViolationSet);
    }

    private void constraintCompositionDemo(Validator validator) {
        PaymentDetail paymentDetail = PaymentDetail.builder()
                .orderStatus("status_is_too_long_and_in_lower_case")
                .build();

        Set<ConstraintViolation<PaymentDetail>> constraintViolationSet = validator.validate(paymentDetail);
        printConstraintViolations(constraintViolationSet);
    }

    private void classLevelConstraint(Validator validator) {
        // Class-level constraints allow validation across multiple fields within a class.
        Account  account = new Account("username", "password", "passwordnotmatch");
        // expect 1 violation because the password does not match retype password
        Set<ConstraintViolation<Account>> constraintViolationSet = validator.validate(account);
        printConstraintViolations(constraintViolationSet);
    }

    private void crossParametersConstraint(Validator validator) throws NoSuchMethodException {
        ExecutableValidator executableValidator = validator.forExecutables();
        Constructor<Account> accountConstructor = Account.class.getConstructor(String.class, String.class, String.class);
        // expect 1 violation because the password does not match retype password
        // the validation happens on constructor level
        Set<ConstraintViolation<Account>> constraintViolationSet = executableValidator
                .validateConstructorParameters(accountConstructor, new Object[]{"username", "password", "passwordnotmatch"});
        printConstraintViolations(constraintViolationSet);
    }

    private void customValidatorContext(Validator validator) throws NoSuchMethodException {
        ExecutableValidator executableValidator = validator.forExecutables();
        Constructor<Account> accountConstructor = Account.class.getConstructor(String.class, String.class, String.class);
        // expect custom validator context because we just disabled the default one, and build a custom one
        Set<ConstraintViolation<Account>> constraintViolationSet = executableValidator
                .validateConstructorParameters(accountConstructor, new Object[]{"username", "password", "passwordnotmatch"});
        printConstraintViolations(constraintViolationSet);
    }

    private void containerDataDemo(Validator validator) {
        // Container data: Optional, List, Map, Set, Collection, etc
        Account account = new Account("username", "password", "password");
        account.setPasswordHistories(new ArrayList<>());
        account.getPasswordHistories().add("");

        Set<ConstraintViolation<Account>> constraintViolationSet = validator.validate(account);
        printConstraintViolations(constraintViolationSet);
    }

    private void valueExtractionDemo() {
        // Extract the value inside container so that the value could be validated

        ValidatorFactory valueExtractorFactory;
        Validator validator;
        Set<ConstraintViolation<Object>> constraintViolationSet;

        // 1. Single generic type
        valueExtractorFactory = Validation.byDefaultProvider()
                .configure()
                .addValueExtractor(new DataValueExtractor())
                .buildValidatorFactory();
        validator = valueExtractorFactory.getValidator();

        SampleData sampleData = new SampleData();
        sampleData.setData(new Data<>());
        sampleData.getData().setData("Hi");

        // If we don't use value extractor, this is the exception that we should expect
        // 2023-12-30T22:36:14.540+09:00  WARN 62532 --- [nio-8080-exec-1] .m.m.a.ExceptionHandlerExceptionResolver : Resolved [jakarta.validation.ConstraintDeclarationException: HV000197: No value extractor found for type parameter 'T' of type com.app.bean_validation.container.Data.]
        // Set<ConstraintViolation<SampleData>> constraintViolationSet = validator.validate(sampleData);

        // Here is the implementation using value extractor
        constraintViolationSet = validator.validate(sampleData);
        printConstraintViolations(constraintViolationSet);

        // 2. Multiple generic type
        valueExtractorFactory = Validation.byDefaultProvider()
                .configure()
                .addValueExtractor(new EntryValueExtractorKey())
                .addValueExtractor(new EntryValueExtractorValue())
                .buildValidatorFactory();
        validator = valueExtractorFactory.getValidator();

        SampleEntry sampleEntry = new SampleEntry();
        sampleEntry.setEntry(new Entry<>());
        constraintViolationSet = validator.validate(sampleEntry);
        printConstraintViolations(constraintViolationSet);

        // 3. For non-generic type
        valueExtractorFactory = Validation.byDefaultProvider()
                .configure()
                .addValueExtractor(new DataIntegerValueExtractor())
                .buildValidatorFactory();
        validator = valueExtractorFactory.getValidator();

        DataInteger dataInteger = new DataInteger();
        dataInteger.setData(1);
        constraintViolationSet = validator.validate(dataInteger);
        printConstraintViolations(constraintViolationSet);
    }

    private void constraintViolationException(Validator validator) {
        // Normally, Bean validation would return violations
        // But, in few cases, we could also throw exception
        PaymentDetail paymentDetail = new PaymentDetail();
        try {
            Set<ConstraintViolation<PaymentDetail>> constraintViolationSet = validator.validate(paymentDetail);
            if (!constraintViolationSet.isEmpty()) {
                throw new ConstraintViolationException(constraintViolationSet);
            }
        } catch (ConstraintViolationException e) {
            System.out.println(e.getMessage());
        }
    }

    private void metadata(Validator validator) {
        // Demonstrates how to retrieve metadata information from a Validator related to constraints on a class.
        // This method prints information about constrained constructors and properties of the PaymentDetail} class,
        // along with details of the associated constraints using the {@code BeanDescriptor}.
        BeanDescriptor beanDescriptor = validator.getConstraintsForClass(PaymentDetail.class);
        System.out.println(beanDescriptor.getConstrainedConstructors());
        beanDescriptor.getConstrainedProperties().forEach(propertyDescriptor -> {
            for (ConstraintDescriptor<?> constraintDescriptor : propertyDescriptor.getConstraintDescriptors()) {
                System.out.println(constraintDescriptor);
            }
        });
    }
}
