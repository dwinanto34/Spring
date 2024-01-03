package com.app.beanvalidation.model;

import com.app.beanvalidation.payload.EmailErrorPayload;
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
    @NotBlank(message = "Bank name can not be blank", payload = {EmailErrorPayload.class})
    private String bankName;
}