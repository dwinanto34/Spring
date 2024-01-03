package com.app.beanvalidation.validator;

import com.app.beanvalidation.constraint.CheckPasswordParameter;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.constraintvalidation.SupportedValidationTarget;
import jakarta.validation.constraintvalidation.ValidationTarget;


// SupportedValidationTarget annotation indicates that it will be used to validate cross parameter
@SupportedValidationTarget(ValidationTarget.PARAMETERS)
public class CheckPasswordParameterValidator implements ConstraintValidator<CheckPasswordParameter, Object[]> {
    int passwordParam;
    int retypePasswordParam;

    @Override
    public void initialize(CheckPasswordParameter constraintAnnotation) {
        passwordParam = constraintAnnotation.passwordParam();
        retypePasswordParam = constraintAnnotation.retypePasswordParam();
    }

    @Override
    public boolean isValid(Object[] objects, ConstraintValidatorContext constraintValidatorContext) {
        String password = (String) objects[passwordParam];
        String retypePassword = (String) objects[retypePasswordParam];

        if (password == null || retypePassword == null) {
            return true;
        }

        boolean isValid = password.equals(retypePassword);

        if (!isValid) {
            // Custom the context
            // Need to disable the default constraint violation
            constraintValidatorContext.disableDefaultConstraintViolation();

            // Build custom constraint violation
            constraintValidatorContext.buildConstraintViolationWithTemplate("Password is different with retypePassword")
                    .addPropertyNode("password")
                    .addConstraintViolation();

            constraintValidatorContext.buildConstraintViolationWithTemplate("Retype password is different with password")
                    .addPropertyNode("retypePassword")
                    .addConstraintViolation();
        }

        return isValid;
    }
}