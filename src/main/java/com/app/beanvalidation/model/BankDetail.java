package com.app.beanvalidation.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankDetail {
    @NotBlank(message = "Bank name can not be blank")
    private String bankName;
}