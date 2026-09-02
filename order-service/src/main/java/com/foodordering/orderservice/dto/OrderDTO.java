package com.foodordering.orderservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class OrderDTO {

    @NotNull(message = "User ID cannot be null")
    private Long userId;

    @NotNull(message = "Restaurant ID cannot be null")
    private Long restaurantId;

    @NotNull(message = "Total price cannot be null")
    @Min(value = 0, message = "Total price must be greater than 0")
    private BigDecimal totalPrice;

    public OrderDTO() {
    }

    public OrderDTO(Long userId, Long restaurantId, BigDecimal totalPrice) {
        this.userId = userId;
        this.restaurantId = restaurantId;
        this.totalPrice = totalPrice;
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
}
