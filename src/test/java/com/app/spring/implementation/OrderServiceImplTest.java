package com.app.spring.implementation;

import com.app.spring.entity.Product;
import com.app.spring.model.request.OrderItemRequest;
import com.app.spring.model.request.OrderRequest;
import com.app.spring.model.response.OrderItemResponse;
import com.app.spring.model.response.OrderResponse;
import com.app.spring.model.response.ProductResponse;
import com.app.spring.repository.OrderRepository;
import com.app.spring.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
// We have h2 as a depdendency.
// so, SpringBoot will auto-configure a connection to the embedded H2 database
@TestPropertySource("/application-test.properties")
public class OrderServiceImplTest {
    private static final String ORDER_ID = "order id";
    private static final String ORDER_ITEM_ID = "order item id";
    private static final String PRODUCT_NAME = "product name";
    private static final Integer QUANTITY = 1;
    private static String DESCRIPTION = "description";
    private static BigDecimal PRICE = new BigDecimal(10.05);
    private static Integer AVAILABLE_STOCK = 10;

    @Autowired
    private OrderServiceImpl serviceImpl;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    public void setupDatabase() {
        // insert master product data
        Product product = Product.builder()
                .name(PRODUCT_NAME)
                .description(DESCRIPTION)
                .price(PRICE)
                .availableStock(AVAILABLE_STOCK)
                .build();
        productRepository.save(product);

        // insert order data
        OrderItemRequest orderItemRequest = OrderItemRequest.builder()
                .orderItemId(ORDER_ITEM_ID)
                .productName(PRODUCT_NAME)
                .quantity(QUANTITY)
                .build();

        OrderRequest orderRequest = OrderRequest.builder()
                .orderId(ORDER_ID)
                .orderItemRequestList(List.of(orderItemRequest))
                .build();

        serviceImpl.save(orderRequest);
    }

    @Test
    public void findAllSuccess() {
        List<OrderResponse> orderResponseList = serviceImpl.findAll();
        assertEquals(1, orderResponseList.size(), "Expected one order in the response list");

        OrderResponse orderResponse = orderResponseList.get(0);
        assertEquals(ORDER_ID, orderResponse.getOrderId());

        assertEquals(1, orderResponseList.get(0).getOrderItemResponseList().size(), "Expected one order item in the order");

        OrderItemResponse orderItemResponse = orderResponse.getOrderItemResponseList().get(0);
        assertEquals(ORDER_ITEM_ID, orderItemResponse.getOrderItemId());
        assertEquals(PRODUCT_NAME, orderItemResponse.getProductName());
        assertEquals(QUANTITY, orderItemResponse.getQuantity());
        assertEquals(PRICE.setScale(2, RoundingMode.DOWN), orderItemResponse.getPricePerQuantity().setScale(2, RoundingMode.DOWN));
        assertEquals(PRICE.setScale(2, RoundingMode.DOWN), orderItemResponse.getFinalAmount().setScale(2, RoundingMode.DOWN));
    }
}
