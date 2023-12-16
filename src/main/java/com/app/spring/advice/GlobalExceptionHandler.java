package com.app.spring.advice;

import com.app.spring.model.OrderManagementErrorResponse;
import com.app.spring.model.OrderManagementNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {
//    Handle not found exception
//    In cases where both global and local handlers are present, the local handler will be prioritized.
    @ExceptionHandler
    public ResponseEntity<OrderManagementErrorResponse> handleNotFoundException(OrderManagementNotFoundException exception) {
        OrderManagementErrorResponse resp = OrderManagementErrorResponse.builder()
                .date(new Date())
                .httpCode(HttpStatus.NOT_FOUND.value())
                .errorMessage(exception.getMessage().concat(" GLOBAL"))
                .build();
        return new ResponseEntity<>(resp, HttpStatus.NOT_FOUND);
    }

//    Handle any exception other than not found exception
    @ExceptionHandler
    public ResponseEntity<OrderManagementErrorResponse> handleAllExceptions(Exception exception) {
        OrderManagementErrorResponse resp = OrderManagementErrorResponse.builder()
                .date(new Date())
                .httpCode(HttpStatus.BAD_REQUEST.value())
                .errorMessage(exception.getMessage().concat(" GLOBAL"))
                .build();
        return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
    }
}
