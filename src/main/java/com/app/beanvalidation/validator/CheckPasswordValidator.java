package com.app.beanvalidation.validator;

import com.app.beanvalidation.constraint.CheckPassword;
import com.app.beanvalidation.model.Account;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CheckPasswordValidator implements ConstraintValidator<CheckPassword, Account> {
    @Override
    public void initialize(CheckPassword constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Account account, ConstraintValidatorContext constraintValidatorContext) {
        if (account.getPassword() == null || account.getRetypePassword() == null) {
            return true;
        }

        return account.getPassword().equals(account.getRetypePassword());
    }
}
