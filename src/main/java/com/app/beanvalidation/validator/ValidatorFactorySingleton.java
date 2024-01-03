package com.app.beanvalidation.validator;

import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;

public class ValidatorFactorySingleton {
    private static ValidatorFactory validatorFactory;

    public static ValidatorFactory buildOrGetValidatorFactory() {
        if (validatorFactory == null) {
            validatorFactory = Validation.buildDefaultValidatorFactory();
        }

        return validatorFactory;
    }
}
