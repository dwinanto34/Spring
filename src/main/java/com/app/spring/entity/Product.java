package com.app.spring.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name = Product.TABLE_NAME)
public class Product {
    public static final String TABLE_NAME = "products";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String PRICE = "price";
    private static final String AVAILABLE_STOCK = "available_stock";

    @Id
    @Column(name = Product.ID)
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @org.springframework.data.annotation.Id
    private String id;

    @Column(name = Product.NAME)
    private String name;

    @Column(name = Product.DESCRIPTION)
    private String description;

    @Column(name = Product.PRICE)
    private BigDecimal price;

    @Column(name = Product.AVAILABLE_STOCK)
    private Integer availableStock;
}
