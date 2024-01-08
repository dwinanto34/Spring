package com.app.spring.implementation;

import com.app.spring.entity.Product;
import com.app.spring.model.response.ProductResponse;
import com.app.spring.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ProductServiceImplTest {
    private static final String PRODUCT_NAME = "name";
    private static final int AVAILABLE_STOCK = 100;
    private static final String PRODUCT_DESCRIPTION = "description";
    private static final BigDecimal PRODUCT_PRICE = new BigDecimal(100);

    // Injects mock dependency into test class
    @InjectMocks
    private ProductServiceImpl serviceImpl;

    // Creates mock object of dependencies
    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    private void setUp() {
        // Initialize mocks and inject dependencies using MockitoAnnotations
        // This is necessary to ensure that Mockito annotations like @Mock and @InjectMocks are properly processed.
        // Without this initialization, Mockito annotations wouldn't work, leading to potential issues such as null pointers or unexpected behavior.
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void findAllTestSuccess() {
        List<Product> productList = new ArrayList<>();
        productList.add(Product.builder()
                .name(PRODUCT_NAME)
                .availableStock(AVAILABLE_STOCK)
                .description(PRODUCT_DESCRIPTION)
                .price(PRODUCT_PRICE)
                .build());

        when(productRepository.findAll()).thenReturn(productList);

        List<ProductResponse> respList = serviceImpl.findAll();

        verify(productRepository, times(1)).findAll();
        assertEquals(1, respList.size(), "Expected one product in the response list");

        ProductResponse productResponse = respList.get(0);
        assertEquals(PRODUCT_NAME, productResponse.getName(), "Product name mismatch");
        assertEquals(AVAILABLE_STOCK, productResponse.getAvailableStock(), "Available stock mismatch");
        assertEquals(PRODUCT_DESCRIPTION, productResponse.getDescription(), "Description mismatch");
        assertEquals(PRODUCT_PRICE, productResponse.getPrice(), "Price mismatch");
    }
}
