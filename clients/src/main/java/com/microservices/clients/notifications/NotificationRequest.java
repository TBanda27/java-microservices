package com.microservices.clients.notifications;

public record NotificationRequest(
        Integer toCustomerId,
        String toCustomerEmail,
        String message
){}
