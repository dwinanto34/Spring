package com.app.spring.dao;

import com.app.spring.entity.Product;

import java.util.List;

public interface ProductDao {
    void save(Product product);

    void update(Product product);

    void updateStock(Integer stock, String id);

    Product findById(String id);

    List<Product> findAll();

    List<Product> findAllAvailableProducts();

    void delete(Product product);
}
