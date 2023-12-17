package com.app.spring.model.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemResponse {
    String orderItemId;
    String productName;
    BigDecimal pricePerQuantity;
    Integer quantity;
    BigDecimal finalAmount;
}
