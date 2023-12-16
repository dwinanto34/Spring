package com.app.spring.service;

import com.app.spring.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> findAll();

    Product findById(String id);

    void save(Product product);

    void update(Product product);

    void delete(String id);
}
