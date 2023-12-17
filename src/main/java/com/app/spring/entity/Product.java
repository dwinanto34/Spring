package com.app.spring.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
// toString() method caused StackOVerFlowError due to cyclic dependency
//@Data
@Getter
@Setter
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

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;

    @ManyToMany
    @JoinTable(
        name = "order_items",
        joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    )
    private List<Order> orders;
}
