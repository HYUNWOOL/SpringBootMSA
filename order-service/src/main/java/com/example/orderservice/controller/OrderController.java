package com.example.orderservice.controller;

import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.entity.Order;
import com.example.orderservice.service.OrderService;
import com.example.orderservice.vo.RequestOrder;
import com.example.orderservice.vo.ResponseOrder;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/order-service")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    Environment env;

    @GetMapping("/health-check")
    public String health_check(){
        return String.format("It's Working this Class on Port %s",env
            .getProperty("local.server.port"));
    }

    @PostMapping("/{userId}/orders")
    public ResponseEntity<ResponseOrder> saveOrder(@PathVariable String userId, @RequestBody RequestOrder order){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        OrderDto orderDto = mapper.map(order,OrderDto.class);
        orderDto.setUserId(userId);
        OrderDto createData = orderService.createOrder(orderDto);

        ResponseOrder result = mapper.map(createData, ResponseOrder.class);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/{userId}/orders")
    public ResponseEntity<List<ResponseOrder>> getOrderByUserId(@PathVariable String userId){

        Iterable<Order> orders = orderService.getOrderByUserId(userId);
        List<ResponseOrder> result = new ArrayList<>();

        orders.forEach(
            t->{
                result.add(new ModelMapper().map(t, ResponseOrder.class));
            }
        );

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<ResponseOrder> getOrderByOrderId(@PathVariable String orderId){
        OrderDto orders = orderService.getOrderByOrderId(orderId);

        ResponseOrder result = new ModelMapper().map(orders, ResponseOrder.class);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }


}
