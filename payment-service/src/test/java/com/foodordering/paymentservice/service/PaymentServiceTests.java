package com.foodordering.paymentservice.service;

import com.foodordering.paymentservice.dto.PaymentDTO;
import com.foodordering.paymentservice.entity.Payment;
import com.foodordering.paymentservice.exception.ResourceNotFoundException;
import com.foodordering.paymentservice.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class PaymentServiceTests {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private PaymentRepository paymentRepository;

    @BeforeEach
    void setUp() {
        paymentRepository.deleteAll();
    }

    @Test
    void testProcessPayment() {
        PaymentDTO paymentDTO = new PaymentDTO(1L, new BigDecimal("250.00"));
        Payment payment = paymentService.processPayment(paymentDTO);

        assertNotNull(payment.getId());
        assertEquals(1L, payment.getOrderId());
        assertNotNull(payment.getStatus());
    }

    @Test
    void testGetPaymentById() {
        PaymentDTO paymentDTO = new PaymentDTO(1L, new BigDecimal("300.00"));
        Payment createdPayment = paymentService.processPayment(paymentDTO);

        Payment retrievedPayment = paymentService.getPaymentById(createdPayment.getId());
        assertEquals(createdPayment.getId(), retrievedPayment.getId());
    }

    @Test
    void testGetPaymentByIdNotFound() {
        assertThrows(ResourceNotFoundException.class, () -> {
            paymentService.getPaymentById(999L);
        });
    }

    @Test
    void testGetPaymentByOrderId() {
        PaymentDTO paymentDTO = new PaymentDTO(5L, new BigDecimal("500.00"));
        paymentService.processPayment(paymentDTO);

        Payment payment = paymentService.getPaymentByOrderId(5L);
        assertEquals(5L, payment.getOrderId());
    }
}
