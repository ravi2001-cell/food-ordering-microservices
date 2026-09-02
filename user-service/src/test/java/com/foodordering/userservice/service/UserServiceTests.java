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
        UserDTO userDTO = new UserDTO("Alice", "alice@example.com");
        User user = userService.createUser(userDTO);

        assertNotNull(user.getId());
        assertEquals("Alice", user.getName());
        assertEquals("alice@example.com", user.getEmail());
    }

    @Test
    void testGetUserById() {
        UserDTO userDTO = new UserDTO("Bob", "bob@example.com");
        User createdUser = userService.createUser(userDTO);

        User retrievedUser = userService.getUserById(createdUser.getId());
        assertEquals(createdUser.getId(), retrievedUser.getId());
        assertEquals("Bob", retrievedUser.getName());
    }

    @Test
    void testGetUserByIdNotFound() {
        assertThrows(ResourceNotFoundException.class, () -> {
            userService.getUserById(999L);
        });
    }

    @Test
    void testGetAllUsers() {
        userService.createUser(new UserDTO("User1", "user1@example.com"));
        userService.createUser(new UserDTO("User2", "user2@example.com"));

        List<User> users = userService.getAllUsers();
        assertEquals(2, users.size());
    }
}
