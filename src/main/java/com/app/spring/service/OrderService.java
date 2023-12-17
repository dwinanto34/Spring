package com.app.spring.service;

import com.app.spring.model.request.OrderRequest;
import com.app.spring.model.response.OrderResponse;

import java.util.List;

public interface OrderService {
    void save(OrderRequest orderRequest);

    List<OrderResponse> findAll();

    void delete(String id);
}
