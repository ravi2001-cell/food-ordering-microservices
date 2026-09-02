package com.foodordering.userservice.service;

import com.foodordering.userservice.dto.UserDTO;
import com.foodordering.userservice.entity.User;
import com.foodordering.userservice.exception.ResourceNotFoundException;
import com.foodordering.userservice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class UserServiceTests {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    void testCreateUser() {
        UserDTO userDTO = new UserDTO("John Doe", "john@example.com");
        User user = userService.createUser(userDTO);

        assertNotNull(user.getId());
        assertEquals("John Doe", user.getName());
        assertEquals("john@example.com", user.getEmail());
    }

    @Test
    void testGetAllUsers() {
        UserDTO user1 = new UserDTO("User One", "user1@example.com");
        UserDTO user2 = new UserDTO("User Two", "user2@example.com");

        userService.createUser(user1);
        userService.createUser(user2);

        List<User> users = userService.getAllUsers();
        assertEquals(2, users.size());
    }

    @Test
    void testGetUserById() {
        UserDTO userDTO = new UserDTO("Test User", "test@example.com");
        User createdUser = userService.createUser(userDTO);

        User retrievedUser = userService.getUserById(createdUser.getId());
        assertEquals(createdUser.getId(), retrievedUser.getId());
        assertEquals("Test User", retrievedUser.getName());
    }

    @Test
    void testGetUserByIdNotFound() {
        assertThrows(ResourceNotFoundException.class, () -> {
            userService.getUserById(999L);
        });
    }
}
