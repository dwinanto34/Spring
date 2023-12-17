package com.app.spring.model.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderManagementErrorResponse {
    private int httpCode;
    private String errorMessage;
    private Date date;
}
