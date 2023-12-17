package com.app.spring.rest;


import com.app.spring.model.request.OrderRequest;
import com.app.spring.model.request.ProductRequest;
import com.app.spring.model.response.OrderResponse;
import com.app.spring.model.response.ProductResponse;
import com.app.spring.service.OrderService;
import com.app.spring.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping()
    public List<OrderResponse> getProducts() {
        return orderService.findAll();
    }

    @PostMapping()
    public String saveOrder(@RequestBody OrderRequest orderRequest) {
        orderService.save(orderRequest);
        return "Saved order successfully";
    }

    @DeleteMapping("/{id}")
    public String deleteOrder(@PathVariable("id") String id) {
        orderService.delete(id);
        return "Deleted order successfully";
    }
}
