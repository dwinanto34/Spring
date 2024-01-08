package com.app.spring.controller;

import com.app.spring.model.request.OrderItemRequest;
import com.app.spring.model.request.OrderRequest;
import com.app.spring.model.response.OrderItemResponse;
import com.app.spring.model.response.OrderResponse;
import com.app.spring.rest.OrderController;
import com.app.spring.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class OrderControllerTest {
    private static final String ORDER_ID = "order id";
    private static final String ORDER_ITEM_ID = "order item id";
    private static final String PRODUCT_NAME = "product name";
    private static final Integer QUANTITY = 1;
    private static BigDecimal PRICE = new BigDecimal(10.05);

    @InjectMocks
    private OrderController orderController;

    @Mock
    private OrderService orderService;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        // Initialize mocks using MockitoAnnotations
        MockitoAnnotations.initMocks(this);

        // Create a standalone instance of MockMvc with the specified controller
        // Standalone setup allows testing of a single controller in isolation
        this.mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();

        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void testGetAllOrdersUsingJsonPath() throws Exception {
        List<OrderResponse> orderResponses = createOrderResponseList();

        when(orderService.findAll()).thenReturn(orderResponses);

        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(orderResponses.size())) // Assuming you return a JSON array
                .andExpect(jsonPath("$[0].orderId").value(orderResponses.get(0).getOrderId()))
                .andExpect(jsonPath("$[0].orderAmount").value(orderResponses.get(0).getOrderAmount().doubleValue()));

        verify(orderService, times(1)).findAll();
    }

    @Test
    public void testGetAllOrdersUsingAssertions() throws Exception {
        List<OrderResponse> orderResponses = createOrderResponseList();

        when(orderService.findAll()).thenReturn(orderResponses);

        MvcResult mvcResult = mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        String responseJson = response.getContentAsString();

        List<OrderResponse> actualResponse = objectMapper.readValue(responseJson, new TypeReference<List<OrderResponse>>(){});

        verify(orderService, times(1)).findAll();

        assertEquals(orderResponses, actualResponse);
    }

    @Test
    public void testCreateOrder() throws Exception {
        OrderRequest request = createOrderRequest();

        doNothing().when(orderService).save(request);

        mockMvc.perform(post("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(new MediaType(MediaType.TEXT_PLAIN, Charset.forName("ISO-8859-1"))))
                .andExpect(jsonPath("$").value("Saved order successfully"));

        verify(orderService, times(1)).save(request);
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

    private List<OrderResponse> createOrderResponseList() {
        return List.of(
                OrderResponse.builder()
                        .orderId(ORDER_ID)
                        .orderAmount(PRICE)
                        .orderItemResponseList(createOrderItemResponseList())
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
}
