package com.app.spring.rest;

import com.app.spring.model.exception.OrderManagementErrorResponse;
import com.app.spring.model.exception.OrderManagementNotFoundException;
import com.app.spring.model.request.ProductRequest;
import com.app.spring.model.response.ProductResponse;
import com.app.spring.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public List<ProductResponse> getProducts() {
        return productService.findAll();
    }

    @GetMapping("/{id}")
    public ProductResponse getProduct(@PathVariable("id") String id) {
        ProductResponse productResponse = productService.findById(id);
        if (productResponse == null) {
            throw new OrderManagementNotFoundException("Product not found");
        }

        return productResponse;
    }

    @PostMapping()
    public String saveProduct(@RequestBody ProductRequest productRequest) {
        productService.save(productRequest);
        return "Saved product successfully";
    }

    @PutMapping("/{id}")
    public String updateProduct(
            @PathVariable("id") String id,
            @RequestBody ProductRequest productRequest
    ) {
        productService.update(id, productRequest);
        return "Updated product successfully";
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable("id") String id) {
        productService.delete(id);
        return "Deleted product successfully";
    }
}
