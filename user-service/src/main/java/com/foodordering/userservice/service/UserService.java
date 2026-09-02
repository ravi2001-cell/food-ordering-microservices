package com.foodordering.userservice.service;

import com.foodordering.userservice.dto.UserDTO;
import com.foodordering.userservice.entity.User;
import com.foodordering.userservice.exception.ResourceNotFoundException;
import com.foodordering.userservice.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    public User createUser(UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        User savedUser = userRepository.save(user);
        logger.info("User created with id: {}", savedUser.getId());
        return savedUser;
    }

    public List<User> getAllUsers() {
        logger.info("Fetching all users");
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        logger.info("Fetching user with id: {}", id);
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            logger.warn("User not found with id: {}", id);
            throw new ResourceNotFoundException("User not found with id " + id);
        }
        return user.get();
    }
}
