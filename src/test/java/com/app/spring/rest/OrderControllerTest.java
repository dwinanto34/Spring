package com.app.spring.rest;

import com.app.spring.entity.Product;
import com.app.spring.implementation.OrderServiceImpl;
import com.app.spring.model.request.OrderItemRequest;
import com.app.spring.model.request.OrderRequest;
import com.app.spring.model.response.OrderResponse;
import com.app.spring.repository.ProductRepository;
import com.app.spring.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@TestPropertySource("/application-test.properties")
@AutoConfigureMockMvc
@SpringBootTest
public class OrderControllerTest {
    private static final String ORDER_ID = "order id";
    private static final String ORDER_ITEM_ID = "order item id";
    private static final String PRODUCT_NAME = "product name";
    private static final Integer QUANTITY = 1;
    private static String DESCRIPTION = "description";
    private static BigDecimal PRICE = new BigDecimal(10.05);
    private static Integer AVAILABLE_STOCK = 10;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderServiceImpl orderServiceImpl;

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

        orderServiceImpl.save(orderRequest);
    }


    @Test
    public void saveOrderTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/orders"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        ModelAndView mav = mvcResult.getModelAndView();
    }
}
