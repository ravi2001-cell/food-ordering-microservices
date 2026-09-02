package com.foodordering.notificationservice.controller;

import com.foodordering.notificationservice.dto.NotificationDTO;
import com.foodordering.notificationservice.service.NotificationService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private static final Logger logger = LoggerFactory.getLogger(NotificationController.class);

    @Autowired
    private NotificationService notificationService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> sendNotification(@Valid @RequestBody NotificationDTO notificationDTO) {
        logger.info("Sending notification to user: {}", notificationDTO.getUserId());
        notificationService.sendNotification(notificationDTO);

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Notification sent successfully to user " + notificationDTO.getUserId());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
