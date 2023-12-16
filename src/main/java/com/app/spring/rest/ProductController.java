package com.app.spring.rest;

import com.app.spring.dao.ProductDao;
import com.app.spring.entity.Product;
import com.app.spring.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    public List<Product> getProducts() {
        return productService.findAll();
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable("id") String id) {
        return productService.findById(id);
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
