package com.app.spring.implementation;

import com.app.spring.entity.Order;
import com.app.spring.entity.OrderItem;
import com.app.spring.entity.Product;
import com.app.spring.model.response.OrderItemResponse;
import com.app.spring.model.response.OrderResponse;
import com.app.spring.repository.OrderItemRepository;
import com.app.spring.repository.OrderRepository;
import com.app.spring.repository.ProductRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class OrderServiceImplTest {
    private static final String ORDER_ID = "order id";
    private static final String ORDER_ITEM_ID = "order item id";
    private static final String PRODUCT_NAME = "product name";
    private static final Integer QUANTITY = 1;
    private static String DESCRIPTION = "description";
    private static BigDecimal PRICE = new BigDecimal(10.05);
    private static Integer AVAILABLE_STOCK = 10;

    // @InjectMocks is used to inject dependencies into the actual class that we want to test
    @InjectMocks
    private OrderServiceImpl orderServiceImpl;

    // @Mock is used to create a mock instance of the dependency class to control its behavior
    @Mock
    private OrderRepository orderRepository;

    @BeforeEach
    public void setUp() {
        // Initialize the mocks to ensure they are ready for use in each test method
        // This step is necessary to avoid null pointers when using mocks in test methods
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindAllOrdersSuccessfully() {
        List<Order> orderList = createOrderList();
        List<OrderResponse> expectedResponse = createOrderResponseList();

        when(orderRepository.findAll()).thenReturn(orderList);

        List<OrderResponse> resp = orderServiceImpl.findAll();

        verify(orderRepository, times(1)).findAll();
        assertEquals(expectedResponse, resp);
    }

    private List<OrderItem> createOrderItemList() {
        return List.of(
                OrderItem.builder()
                        .orderItemId(ORDER_ITEM_ID)
                        .pricePerQty(PRICE)
                        .quantity(QUANTITY)
                        .finalAmount(PRICE)
                        .product(createProduct())
                        .build()
        );
    }

    private Product createProduct() {
        return Product.builder()
                .name(PRODUCT_NAME)
                .price(PRICE)
                .availableStock(AVAILABLE_STOCK)
                .build();
    }

    private List<Order> createOrderList() {
        return List.of(
                Order.builder()
                    .orderId(ORDER_ID)
                    .orderItems(createOrderItemList())
                    .amount(PRICE)
                    .build()
        );
    }

    private List<OrderItemResponse> createOrderItemResponseList() {
        return List.of(
                OrderItemResponse.builder()
                        .orderItemId(ORDER_ITEM_ID)
                        .productName(PRODUCT_NAME)
                        .pricePerQuantity(PRICE)
                        .quantity(QUANTITY)
                        .finalAmount(PRICE)
                        .build()
        );
    }

    private List<OrderResponse> createOrderResponseList() {
        return List.of(
                OrderResponse.builder()
                        .orderId(ORDER_ID)
                        .orderAmount(PRICE)
                        .orderItemResponseList(createOrderItemResponseList())
                        .build()
        );
    }
}
