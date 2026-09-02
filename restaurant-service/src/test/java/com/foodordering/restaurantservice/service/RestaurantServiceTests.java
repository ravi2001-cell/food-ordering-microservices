package com.foodordering.restaurantservice.service;

import com.foodordering.restaurantservice.dto.RestaurantDTO;
import com.foodordering.restaurantservice.entity.Restaurant;
import com.foodordering.restaurantservice.exception.ResourceNotFoundException;
import com.foodordering.restaurantservice.repository.RestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class RestaurantServiceTests {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @BeforeEach
    void setUp() {
        restaurantRepository.deleteAll();
    }

    @Test
    void testCreateRestaurant() {
        RestaurantDTO restaurantDTO = new RestaurantDTO("Paradise Biryani", "Hyderabad");
        Restaurant restaurant = restaurantService.createRestaurant(restaurantDTO);

        assertNotNull(restaurant.getId());
        assertEquals("Paradise Biryani", restaurant.getName());
        assertEquals("Hyderabad", restaurant.getLocation());
    }

    @Test
    void testGetAllRestaurants() {
        RestaurantDTO r1 = new RestaurantDTO("Restaurant 1", "Location 1");
        RestaurantDTO r2 = new RestaurantDTO("Restaurant 2", "Location 2");

        restaurantService.createRestaurant(r1);
        restaurantService.createRestaurant(r2);

        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        assertEquals(2, restaurants.size());
    }

    @Test
    void testGetRestaurantById() {
        RestaurantDTO restaurantDTO = new RestaurantDTO("Test Restaurant", "Test Location");
        Restaurant createdRestaurant = restaurantService.createRestaurant(restaurantDTO);

        Restaurant retrievedRestaurant = restaurantService.getRestaurantById(createdRestaurant.getId());
        assertEquals(createdRestaurant.getId(), retrievedRestaurant.getId());
        assertEquals("Test Restaurant", retrievedRestaurant.getName());
    }

    @Test
    void testGetRestaurantByIdNotFound() {
        assertThrows(ResourceNotFoundException.class, () -> {
            restaurantService.getRestaurantById(999L);
        });
    }
}
