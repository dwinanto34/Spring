package com.app.beanvalidation.payload;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Payload;

public class EmailErrorPayload implements Payload {
    private static final String MESSAGE_TEMPLATE = "Message template";

    public void sendEmail(ConstraintViolation<?> violation) {
        System.out.println("Send email: " + MESSAGE_TEMPLATE + " : " + violation.getMessage());
    }
}
