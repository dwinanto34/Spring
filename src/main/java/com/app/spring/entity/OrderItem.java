package com.app.spring.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
//@Data
@Getter
@Table(name = OrderItem.TABLE_NAME)
public class OrderItem {
    public static final String TABLE_NAME = "order_items";
    private static final String ID = "id";
    private static final String ORDER_ID = "order_id";
    private static final String ORDER_ITEM_ID = "order_item_id";
    private static final String PRODUCT_ID = "product_id";
    private static final String PRICE_PER_QUANTITY = "price_per_quantity";
    private static final String QUANTITY = "quantity";
    private static final String FINAL_AMOUNT = "final_amount";

    @Id
    @Column(name = OrderItem.ID)
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @org.springframework.data.annotation.Id
    private String id;

    @ManyToOne
//    Need to define referencedColumnName, since we are trying to map to non primary key
    @JoinColumn(name = OrderItem.ORDER_ID, referencedColumnName = "order_id")
    private Order order;

    @Column(name = OrderItem.ORDER_ITEM_ID)
    private String orderItemId;

    @ManyToOne
    @JoinColumn(name = OrderItem.PRODUCT_ID)
    private Product product;

    @Column(name = OrderItem.PRICE_PER_QUANTITY)
    private BigDecimal pricePerQty;

    @Column(name = OrderItem.QUANTITY)
    private Integer quantity;

    @Column(name = OrderItem.FINAL_AMOUNT)
    private BigDecimal finalAmount;
}
