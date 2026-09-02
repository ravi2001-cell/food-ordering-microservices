package com.foodordering.paymentservice.service;

import com.foodordering.paymentservice.dto.PaymentDTO;
import com.foodordering.paymentservice.entity.Payment;
import com.foodordering.paymentservice.exception.ResourceNotFoundException;
import com.foodordering.paymentservice.repository.PaymentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class PaymentService {

    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);
    private static final Random random = new Random();

    @Autowired
    private PaymentRepository paymentRepository;

    public Payment processPayment(PaymentDTO paymentDTO) {
        logger.info("Processing payment for order: {} with amount: {}", paymentDTO.getOrderId(), paymentDTO.getAmount());

        // Create payment with PENDING status
        Payment payment = new Payment();
        payment.setOrderId(paymentDTO.getOrderId());
        payment.setAmount(paymentDTO.getAmount());
        payment.setStatus("PENDING");
        Payment savedPayment = paymentRepository.save(payment);
        logger.info("Payment created with id: {} for order: {}", savedPayment.getId(), savedPayment.getOrderId());

        // Simulate payment processing
        simulatePaymentProcessing(savedPayment);

        return savedPayment;
    }

    public Payment getPaymentById(Long id) {
        logger.info("Fetching payment with id: {}", id);
        Optional<Payment> payment = paymentRepository.findById(id);
        if (payment.isEmpty()) {
            logger.warn("Payment not found with id: {}", id);
            throw new ResourceNotFoundException("Payment not found with id " + id);
        }
        return payment.get();
    }

    public Payment getPaymentByOrderId(Long orderId) {
        logger.info("Fetching payment for order: {}", orderId);
        Optional<Payment> payment = paymentRepository.findByOrderId(orderId);
        if (payment.isEmpty()) {
            logger.warn("Payment not found for order: {}", orderId);
            throw new ResourceNotFoundException("Payment not found for order " + orderId);
        }
        return payment.get();
    }

    private void simulatePaymentProcessing(Payment payment) {
        // Simulate payment gateway delay
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Payment processing interrupted", e);
        }

        // Simulate random success/failure (80% success rate)
        boolean isSuccess = random.nextDouble() < 0.8;

        if (isSuccess) {
            payment.setStatus("SUCCESS");
            logger.info("Payment processed successfully for order: {}", payment.getOrderId());
        } else {
            payment.setStatus("FAILED");
            logger.warn("Payment processing failed for order: {}", payment.getOrderId());
        }

        payment.setUpdatedAt(LocalDateTime.now());
        paymentRepository.save(payment);
    }
}
