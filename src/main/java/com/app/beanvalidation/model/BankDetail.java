package com.app.beanvalidation.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BankDetail {
    @NotBlank(message = "Bank name can not be blank")
    private String bankName;
}