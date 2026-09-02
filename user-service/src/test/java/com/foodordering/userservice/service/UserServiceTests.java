package com.foodordering.userservice.service;

import com.foodordering.userservice.dto.UserDTO;
import com.foodordering.userservice.entity.User;
import com.foodordering.userservice.exception.ResourceNotFoundException;
import com.foodordering.userservice.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private UserDTO testUserDTO;
    private User testUser;

    @BeforeEach
    void setUp() {
        testUserDTO = new UserDTO(
                "Alice",
                "alice@example.com"
        );

        testUser = new User();
        testUser.setId(1L);
        testUser.setName("Alice");
        testUser.setEmail("alice@example.com");
    }

    @Test
    void testCreateUser() {

        when(userRepository.save(any(User.class)))
                .thenReturn(testUser);

        User user = userService.createUser(testUserDTO);

        assertNotNull(user);
        assertEquals(1L, user.getId());
        assertEquals("Alice", user.getName());
        assertEquals("alice@example.com", user.getEmail());

        verify(userRepository, times(1))
                .save(any(User.class));
    }

    @Test
    void testGetUserById() {

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(testUser));

        User retrievedUser = userService.getUserById(1L);

        assertNotNull(retrievedUser);
        assertEquals(1L, retrievedUser.getId());
        assertEquals("Alice", retrievedUser.getName());

        verify(userRepository, times(1))
                .findById(1L);
    }

    @Test
    void testGetUserByIdNotFound() {

        when(userRepository.findById(999L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> userService.getUserById(999L)
        );

        verify(userRepository, times(1))
                .findById(999L);
    }

    @Test
    void testGetAllUsers() {

        User user1 = new User();
        user1.setId(1L);
        user1.setName("User1");
        user1.setEmail("user1@example.com");

        User user2 = new User();
        user2.setId(2L);
        user2.setName("User2");
        user2.setEmail("user2@example.com");

        when(userRepository.findAll())
                .thenReturn(List.of(user1, user2));

        List<User> users = userService.getAllUsers();

        assertEquals(2, users.size());
        assertEquals("User1", users.get(0).getName());
        assertEquals("User2", users.get(1).getName());

        verify(userRepository, times(1))
                .findAll();
    }
}
