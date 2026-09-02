package com.foodordering.notificationservice.service;

import com.foodordering.notificationservice.dto.NotificationDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    public void sendNotification(NotificationDTO notificationDTO) {
        logger.info("Notification sent to user {}: {}", notificationDTO.getUserId(), notificationDTO.getMessage());
    }
}
