package com.foodordering.restaurantservice.dto;

import jakarta.validation.constraints.NotBlank;

public class RestaurantDTO {

    @NotBlank(message = "Restaurant name cannot be blank")
    private String name;

    @NotBlank(message = "Location cannot be blank")
    private String location;

    public RestaurantDTO() {
    }

    public RestaurantDTO(String name, String location) {
        this.name = name;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
