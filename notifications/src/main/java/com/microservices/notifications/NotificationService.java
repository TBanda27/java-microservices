package com.microservices.notifications;

import com.microservices.clients.notifications.NotificationRequest;
import com.microservices.clients.notifications.NotificationResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationResponse sendNotification(NotificationRequest notificationRequest) {
        Notification notification = Notification.builder()
                .toCustomerEmail(notificationRequest.toCustomerEmail())
                .toCustomerId(notificationRequest.toCustomerId())
                .message(notificationRequest.message())
                .sentAt(LocalDateTime.now())
                .sender("System")
                .build();
        notificationRepository.saveAndFlush(notification);

        return new NotificationResponse(
                notification.getNotificationId(),
                notification.getToCustomerId(),
                notification.getToCustomerEmail(),
                notification.getSender(),
                notification.getMessage(),
                notification.getSentAt()
        );
    }
}
