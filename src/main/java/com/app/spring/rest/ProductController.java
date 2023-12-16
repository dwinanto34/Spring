package com.app.spring.rest;

import com.app.spring.dao.ProductDao;
import com.app.spring.entity.Product;
import com.app.spring.model.OrderManagementErrorResponse;
import com.app.spring.model.OrderManagementNotFoundException;
import com.app.spring.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

//    Handle not found exception
//    In cases where both global and local handlers are present, the local handler will be prioritized.
    @ExceptionHandler
    public ResponseEntity<OrderManagementErrorResponse> handleNotFoundException(OrderManagementNotFoundException exception) {
        OrderManagementErrorResponse resp = OrderManagementErrorResponse.builder()
            .date(new Date())
            .httpCode(HttpStatus.NOT_FOUND.value())
            .errorMessage(exception.getMessage().concat(" LOCAL"))
            .build();
        return new ResponseEntity<>(resp, HttpStatus.NOT_FOUND);
    }

//    Handle any exception other than not found exception
    @ExceptionHandler
    public ResponseEntity<OrderManagementErrorResponse> handleAllExceptions(Exception exception) {
        OrderManagementErrorResponse resp = OrderManagementErrorResponse.builder()
            .date(new Date())
            .httpCode(HttpStatus.BAD_REQUEST.value())
            .errorMessage(exception.getMessage().concat(" LOCAL"))
            .build();
        return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
    }

    @GetMapping()
    public List<Product> getProducts() {
        return productService.findAll();
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable("id") String id) {
        Product product = productService.findById(id);
        if (product == null) {
            throw new OrderManagementNotFoundException("Product not found");
        }

        return product;
    }

    @PostMapping()
    public String saveProduct(@RequestBody Product product) {
        productService.save(product);
        return "Saved product successfully";
    }

    @PutMapping()
    public String updateProduct(@RequestBody Product product) {
        productService.update(product);
        return "Saved product successfully";
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable("id") String id) {
        productService.delete(id);
        return "Saved product successfully";
    }
}
