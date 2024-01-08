package com.app.spring;

import com.app.spring.entity.Product;
import com.app.spring.model.request.OrderItemRequest;
import com.app.spring.model.request.OrderRequest;
import com.app.spring.model.response.OrderItemResponse;
import com.app.spring.model.response.OrderResponse;
import com.app.spring.repository.OrderItemRepository;
import com.app.spring.repository.OrderRepository;
import com.app.spring.repository.ProductRepository;
import com.app.spring.service.OrderService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.Charset;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// @SpringBootTest is used to indicate that this is an integration test and should load the entire Spring context.
@SpringBootTest
// @AutoConfigureMockMvc is used to automatically configure the MockMvc instance.
@AutoConfigureMockMvc
// Read the configuration from particular file path
@TestPropertySource("/application-test.properties")
public class OrderIntegrationTest {
    private static final String ORDER_ID = "order id";
    private static final String ORDER_ITEM_ID = "order item id";
    private static final String PRODUCT_NAME = "product name";
    private static final String PRODUCT_ID = "product ID";
    private static final Integer QUANTITY = 1;
    private static final Integer AVAILABLE_STOCK = 100;
    private static BigDecimal PRICE = new BigDecimal(10.05).setScale(2, RoundingMode.DOWN);

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @BeforeEach
    public void setUp() {
        // Insert a product into the database for testing
        Product product = createProduct();
        productRepository.save(product);
    }

    @AfterEach
    public void afterEach() {
        // Clean up test data after each test
        orderItemRepository.deleteAll();
        orderRepository.deleteAll();
        productRepository.deleteAll();
    }

    @Test
    public void testOrderFlow() throws Exception {
        OrderRequest request = createOrderRequest();

        // create an order
        mockMvc.perform(post("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(new MediaType(MediaType.TEXT_PLAIN, Charset.forName("UTF-8"))))
                .andExpect(content().string("Saved order successfully"));

        // try to get previous created order
        MvcResult mvcResult = mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        String responseJson = response.getContentAsString();

        List<OrderResponse> actualResponse = objectMapper.readValue(responseJson, new TypeReference<List<OrderResponse>>(){});
        List<OrderResponse> expectedOrderResponses = createOrderResponseList();

        assertEquals(expectedOrderResponses, actualResponse);
    }

    private List<OrderResponse> createOrderResponseList() {
        return List.of(OrderResponse.builder()
                .orderAmount(PRICE)
                .orderId(ORDER_ID)
                .orderItemResponseList(List.of(
                        OrderItemResponse.builder()
                                .orderItemId(ORDER_ITEM_ID)
                                .productName(PRODUCT_NAME)
                                .quantity(QUANTITY)
                                .pricePerQuantity(PRICE)
                                .finalAmount(PRICE)
                                .build()
                ))
                .build()
        );
    }

    private Product createProduct() {
        return Product.builder()
                .id(PRODUCT_ID)
                .name(PRODUCT_NAME)
                .price(PRICE)
                .availableStock(AVAILABLE_STOCK)
                .build();
    }

    private OrderRequest createOrderRequest() {
        return OrderRequest.builder()
                .orderId(ORDER_ID)
                .orderItemRequestList(createOrderItemRequestList())
                .build();
    }

    private List<OrderItemRequest> createOrderItemRequestList() {
        return List.of(
                OrderItemRequest.builder()
                        .orderItemId(ORDER_ITEM_ID)
                        .productName(PRODUCT_NAME)
                        .quantity(QUANTITY)
                        .build()
        );
    }
}
