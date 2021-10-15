package com.example.orderservice.service;

import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.entity.Order;

public interface OrderService {

    OrderDto createOrder(OrderDto request);
    OrderDto getOrderByOrderId(String orderId);
    Iterable<Order> getOrderByUserId(String userId);
    Iterable<Order> getAllOrders();
}
