package com.example.orderservice.service;

import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.entity.Order;
import com.example.orderservice.repository.OrderRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public OrderDto createOrder(OrderDto request) {
        request.setOrderId(UUID.randomUUID().toString());
        request.setTotalPrice(request.getQty()*request.getUnitPrice());

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Order order = mapper.map(request,Order.class);

        orderRepository.save(order);

        OrderDto result = mapper.map(order, OrderDto.class);
        return result;
    }
    @Override
    public OrderDto getOrderByOrderId(String orderId) {
        Optional<Order> order = orderRepository.findByOrderId(orderId);
        OrderDto orderDto = new ModelMapper().map(order.get() ,OrderDto.class);

        return orderDto;
    }

    @Override
    public Iterable<Order> getOrderByUserId(String userId) {
        return orderRepository.findByUserId(userId);
    }

    @Override
    public Iterable<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}
