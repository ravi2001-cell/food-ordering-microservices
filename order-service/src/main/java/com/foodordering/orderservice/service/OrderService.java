package com.foodordering.orderservice.service;

import com.foodordering.orderservice.dto.OrderDTO;
import com.foodordering.orderservice.entity.Order;
import com.foodordering.orderservice.exception.ResourceNotFoundException;
import com.foodordering.orderservice.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderRepository orderRepository;

    public Order createOrder(OrderDTO orderDTO) {
        Order order = new Order();
        order.setUserId(orderDTO.getUserId());
        order.setRestaurantId(orderDTO.getRestaurantId());
        order.setTotalPrice(orderDTO.getTotalPrice());
        Order savedOrder = orderRepository.save(order);
        logger.info("Order created with id: {}", savedOrder.getId());
        return savedOrder;
    }

    public List<Order> getAllOrders() {
        logger.info("Fetching all orders");
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        logger.info("Fetching order with id: {}", id);
        Optional<Order> order = orderRepository.findById(id);
        if (order.isEmpty()) {
            logger.warn("Order not found with id: {}", id);
            throw new ResourceNotFoundException("Order not found with id " + id);
        }
        return order.get();
    }

    public List<Order> getOrdersByUserId(Long userId) {
        logger.info("Fetching orders for user id: {}", userId);
        return orderRepository.findByUserId(userId);
    }

    public Order updateOrderStatus(Long id, String status) {
        Order order = getOrderById(id);
        order.setStatus(status);
        order.setUpdatedAt(LocalDateTime.now());
        Order updatedOrder = orderRepository.save(order);
        logger.info("Order status updated to: {}", status);
        return updatedOrder;
    }
}
