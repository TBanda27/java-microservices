package com.microservices.customer;

import com.microservices.clients.fraud.FraudCheckResponse;
import com.microservices.clients.fraud.FraudClient;
import com.microservices.clients.notifications.NotificationRequest;
import com.microservices.clients.notifications.NotificationsClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
@Slf4j
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final RestTemplate restTemplate;
    private final FraudClient fraudClient;
    private final NotificationsClient notificationsClient;

    public void  registerCustomer(CustomerRegistrationRequest customerRegistrationRequest) {
        Customer customer = Customer.builder()
                .firstName(customerRegistrationRequest.firstName())
                .lastName(customerRegistrationRequest.lastName())
                .email(customerRegistrationRequest.email())
                .build();
        customerRepository.saveAndFlush(customer);

        FraudCheckResponse fraudCheckResponse = fraudClient.isFraudster(customer.getId());
        log.info("fraud check for customer with id {}", customer.getId());

        assert fraudCheckResponse != null;
        if(fraudCheckResponse.isFraudster()){
            throw new IllegalStateException("Customer with id: " + customer.getId() + " is a fraudster");
        }

        notificationsClient.sendNotification(
                new NotificationRequest(
                        customer.getId(),
                        customer.getEmail(),
                        String.format("Hi %s, welcome to Banda's Microservices System...", customer.getFirstName())
                )
        );
    }
}
