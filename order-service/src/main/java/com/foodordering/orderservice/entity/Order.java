package com.foodordering.orderservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "User ID cannot be null")
    @Column(nullable = false)
    private Long userId;

    @NotNull(message = "Restaurant ID cannot be null")
    @Column(nullable = false)
    private Long restaurantId;

    @NotNull(message = "Total price cannot be null")
    @Min(value = 0, message = "Total price must be greater than 0")
    @Column(nullable = false)
    private BigDecimal totalPrice;

    @Column(nullable = false)
    private String status = "PENDING";

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt = LocalDateTime.now();

    public Order() {
    }

    public Order(Long userId, Long restaurantId, BigDecimal totalPrice) {
        this.userId = userId;
        this.restaurantId = restaurantId;
        this.totalPrice = totalPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", userId=" + userId +
                ", restaurantId=" + restaurantId +
                ", totalPrice=" + totalPrice +
                ", status='" + status + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
