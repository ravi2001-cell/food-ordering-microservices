package com.foodordering.orderservice.controller;

import com.foodordering.orderservice.dto.OrderDTO;
import com.foodordering.orderservice.entity.Order;
import com.foodordering.orderservice.service.OrderService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> createOrder(@Valid @RequestBody OrderDTO orderDTO) {
        logger.info("Creating order for user id: {}", orderDTO.getUserId());
        Order order = orderService.createOrder(orderDTO);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        logger.info("Fetching all orders");
        List<Order> orders = orderService.getAllOrders();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        logger.info("Fetching order with id: {}", id);
        Order order = orderService.getOrderById(id);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getOrdersByUserId(@PathVariable Long userId) {
        logger.info("Fetching orders for user id: {}", userId);
        List<Order> orders = orderService.getOrdersByUserId(userId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long id, @RequestBody Map<String, String> request) {
        String status = request.get("status");
        logger.info("Updating order status for id: {} to: {}", id, status);
        Order order = orderService.updateOrderStatus(id, status);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }
}
