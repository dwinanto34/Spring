package com.app.spring.service;

import com.app.spring.model.request.ProductRequest;
import com.app.spring.model.response.ProductResponse;

import java.util.List;

public interface ProductService {
    List<ProductResponse> findAll();

    ProductResponse findById(String id);

    void save(ProductRequest ProductRequest);

    void update(String id, ProductRequest ProductRequest);

    void delete(String id);
}
