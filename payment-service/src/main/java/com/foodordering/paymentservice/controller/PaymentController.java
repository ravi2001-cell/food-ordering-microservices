package com.foodordering.paymentservice.controller;

import com.foodordering.paymentservice.dto.PaymentDTO;
import com.foodordering.paymentservice.entity.Payment;
import com.foodordering.paymentservice.service.PaymentService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public ResponseEntity<Payment> processPayment(@Valid @RequestBody PaymentDTO paymentDTO) {
        logger.info("Processing payment request for order: {}", paymentDTO.getOrderId());
        Payment payment = paymentService.processPayment(paymentDTO);
        return new ResponseEntity<>(payment, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable Long id) {
        logger.info("Fetching payment with id: {}", id);
        Payment payment = paymentService.getPaymentById(id);
        return new ResponseEntity<>(payment, HttpStatus.OK);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<Payment> getPaymentByOrderId(@PathVariable Long orderId) {
        logger.info("Fetching payment for order: {}", orderId);
        Payment payment = paymentService.getPaymentByOrderId(orderId);
        return new ResponseEntity<>(payment, HttpStatus.OK);
    }
}
