package com.app.spring.model;

public class OrderManagementNotFoundException extends RuntimeException {
    public OrderManagementNotFoundException(String message) {
        super(message);
    }

    public OrderManagementNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public OrderManagementNotFoundException(Throwable cause) {
        super(cause);
    }
}
