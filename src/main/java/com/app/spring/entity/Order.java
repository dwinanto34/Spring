package com.app.spring.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
//@Data
@Getter
@Setter
@Table(name = Order.TABLE_NAME)
public class Order {
    public static final String TABLE_NAME = "orders";
    private static final String ID = "id";
    private static final String ORDER_ID = "order_id";
    private static final String AMOUNT = "amount";

    @Id
    @Column(name = Order.ID)
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @org.springframework.data.annotation.Id
    private String id;

    @Column(name = Order.ORDER_ID)
    private String orderId;

    @Column(name = Order.AMOUNT)
    private BigDecimal amount;

//    Default Fetch Types:
//    - OneToOne: EAGER
//    - ManyToOne: EAGER
//    - OneToMany: LAZY
//    - ManyToMany: LAZY

//    CascadeType:
//    - PERSIST: Related entities will also be saved
//    - REMOVE: Related entities will also be removed
//    - REFRESH: Related entities will also be refreshed
//    - DETACH: Detach from related entities
//    - MERGE: The opposite of DETACH
//    - ALL: All types above

//    Issue with Session:
//    When the session is already closed, attempting to retrieve lazy-loaded data will throw an exception.
//    Solutions:
//    - Solution #1: Change from lazy to eager loading, ensuring data is fetched eagerly during initial retrieval.
//    - Solution #2: Call a Data Access Object (DAO) function to explicitly retrieve the related data instead of relying on lazy loading through getters.
//    - Solution #3: Define a specific query to FETCH the related data, allowing for explicit control over when and how data is loaded.
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
    private List<OrderItem> orderItems;

    @ManyToMany
    @JoinTable(
        name = "order_items",
        joinColumns = @JoinColumn(name = "order_id", referencedColumnName = "order_id"),
        inverseJoinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id")
    )
    private List<Product> productList;
}


