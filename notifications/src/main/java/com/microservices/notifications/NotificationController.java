package com.microservices.notifications;

import com.microservices.clients.notifications.NotificationRequest;
import com.microservices.clients.notifications.NotificationResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/notifications")
@AllArgsConstructor
@Slf4j
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    public NotificationResponse sendNotification(@RequestBody NotificationRequest notificationRequest) {
        return notificationService.sendNotification(notificationRequest);
    }
}
