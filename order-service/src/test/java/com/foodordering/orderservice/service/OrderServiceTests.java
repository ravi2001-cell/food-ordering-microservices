package com.foodordering.orderservice.service;

import com.foodordering.orderservice.dto.OrderDTO;
import com.foodordering.orderservice.entity.Order;
import com.foodordering.orderservice.exception.ResourceNotFoundException;
import com.foodordering.orderservice.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class OrderServiceTests {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @BeforeEach
    void setUp() {
        orderRepository.deleteAll();
    }

    @Test
    void testCreateOrder() {
        OrderDTO orderDTO = new OrderDTO(1L, 1L, new BigDecimal("250.00"));
        Order order = orderService.createOrder(orderDTO);

        assertNotNull(order.getId());
        assertEquals(1L, order.getUserId());
        assertEquals(1L, order.getRestaurantId());
        assertEquals("PENDING", order.getStatus());
    }

    @Test
    void testGetOrderById() {
        OrderDTO orderDTO = new OrderDTO(1L, 1L, new BigDecimal("300.00"));
        Order createdOrder = orderService.createOrder(orderDTO);

        Order retrievedOrder = orderService.getOrderById(createdOrder.getId());
        assertEquals(createdOrder.getId(), retrievedOrder.getId());
    }

    @Test
    void testGetOrderByIdNotFound() {
        assertThrows(ResourceNotFoundException.class, () -> {
            orderService.getOrderById(999L);
        });
    }

    @Test
    void testGetOrdersByUserId() {
        orderService.createOrder(new OrderDTO(1L, 1L, new BigDecimal("250.00")));
        orderService.createOrder(new OrderDTO(1L, 2L, new BigDecimal("350.00")));

        List<Order> orders = orderService.getOrdersByUserId(1L);
        assertEquals(2, orders.size());
    }

    @Test
    void testUpdateOrderStatus() {
        Order createdOrder = orderService.createOrder(new OrderDTO(1L, 1L, new BigDecimal("250.00")));
        Order updatedOrder = orderService.updateOrderStatus(createdOrder.getId(), "CONFIRMED");
        assertEquals("CONFIRMED", updatedOrder.getStatus());
    }
}
