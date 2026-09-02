package com.foodordering.restaurantservice.service;

import com.foodordering.restaurantservice.dto.RestaurantDTO;
import com.foodordering.restaurantservice.entity.Restaurant;
import com.foodordering.restaurantservice.exception.ResourceNotFoundException;
import com.foodordering.restaurantservice.repository.RestaurantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantService {

    private static final Logger logger = LoggerFactory.getLogger(RestaurantService.class);

    @Autowired
    private RestaurantRepository restaurantRepository;

    public Restaurant createRestaurant(RestaurantDTO restaurantDTO) {
        Restaurant restaurant = new Restaurant();
        restaurant.setName(restaurantDTO.getName());
        restaurant.setLocation(restaurantDTO.getLocation());
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);
        logger.info("Restaurant created with id: {}", savedRestaurant.getId());
        return savedRestaurant;
    }

    public List<Restaurant> getAllRestaurants() {
        logger.info("Fetching all restaurants");
        return restaurantRepository.findAll();
    }

    public Restaurant getRestaurantById(Long id) {
        logger.info("Fetching restaurant with id: {}", id);
        Optional<Restaurant> restaurant = restaurantRepository.findById(id);
        if (restaurant.isEmpty()) {
            logger.warn("Restaurant not found with id: {}", id);
            throw new ResourceNotFoundException("Restaurant not found with id " + id);
        }
        return restaurant.get();
    }
}
