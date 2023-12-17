package com.app.spring.implementation;

import com.app.spring.entity.Order;
import com.app.spring.entity.OrderItem;
import com.app.spring.entity.Product;
import com.app.spring.model.request.OrderItemRequest;
import com.app.spring.model.request.OrderRequest;
import com.app.spring.model.response.OrderItemResponse;
import com.app.spring.model.response.OrderResponse;
import com.app.spring.model.response.ProductResponse;
import com.app.spring.repository.OrderItemRepository;
import com.app.spring.repository.OrderRepository;
import com.app.spring.repository.ProductRepository;
import com.app.spring.service.OrderService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private OrderRepository orderRepository;
    private OrderItemRepository orderItemRepository;
    private ProductRepository productRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, OrderItemRepository orderItemRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public void save(OrderRequest orderRequest) {
        Order order = convert(orderRequest);
        orderRepository.save(order);
        order.getOrderItems().forEach(orderItemRepository::save);
    }

    @Override
    public List<OrderResponse> findAll() {
        List<Order> orders = orderRepository.findAll();
        List<OrderResponse> orderResponses = orders.stream()
                .map(this::convert)
                .collect(Collectors.toList());
        return orderResponses;
    }

    @Override
    @Transactional
    public void delete(String id) {
        Optional<Order> order = orderRepository.findById(id);

        if (order.isPresent()) {
            List<OrderItem> orderItems = orderItemRepository.findByOrderOrderId(order.get().getOrderId());
            orderRepository.delete(order.get());
            orderItems.forEach(orderItemRepository::delete);
        }
    }

    private Order convert(OrderRequest orderRequest) {
        Order order = Order.builder()
                .orderId(orderRequest.getOrderId())
                .build();

        List<OrderItem> orderItems = orderRequest.getOrderItemRequestList().stream()
                .map(orderItemRequest -> convert(orderItemRequest, order))
                .collect(Collectors.toList());

        BigDecimal orderAmount = orderItems.stream()
                .map(OrderItem::getFinalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setAmount(orderAmount);
        order.setOrderItems(orderItems);

        return order;
    }

    private OrderItem convert(OrderItemRequest orderItemRequest, Order order) {
        Product product = productRepository.findByName(orderItemRequest.getProductName());

        if (product != null) {
            return OrderItem.builder()
                    .orderItemId(orderItemRequest.getOrderItemId())
                    .product(product)
                    .order(order)
                    .pricePerQty(product.getPrice())
                    .quantity(orderItemRequest.getQuantity())
                    .finalAmount(product.getPrice().multiply(new BigDecimal(orderItemRequest.getQuantity())))
                    .build();
        }

        return null;
    }

    private OrderResponse convert(Order order) {
        List<OrderItemResponse> orderItemResponseList = order.getOrderItems()
                .stream()
                .map(this::convert)
                .collect(Collectors.toList());

        return OrderResponse.builder()
                .orderId(order.getOrderId())
                .orderAmount(order.getAmount())
                .orderItemResponseList(orderItemResponseList)
                .build();
    }

    private OrderItemResponse convert(OrderItem orderItem) {
        return OrderItemResponse.builder()
                .orderItemId(orderItem.getOrderItemId())
                .productName(orderItem.getProduct().getName())
                .pricePerQuantity(orderItem.getPricePerQty())
                .quantity(orderItem.getQuantity())
                .finalAmount(orderItem.getFinalAmount())
                .build();
    }
}
