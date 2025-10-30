package com.microservices.clients.notifications;

import java.time.LocalDateTime;

public record NotificationResponse(
        Integer notificationId,
        Integer toCustomerId,
        String toCustomerEmail,
        String sender,
        String message,
        LocalDateTime sentAt
) {

}
