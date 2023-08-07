package com.OnlineShopping.notificationservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication
@Slf4j
public class NotificationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotificationServiceApplication.class, args);
    }


    // when orderService raise notificationTopic topic then this notification service will listen to that and will execute below logic.
    @KafkaListener(topics="notificationTopic")
    public void handleNotification(OrderPlacedEvent orderPlacedEvent){
        // todo: Send out email notification

        // for now, I am implementing logging by using slf4j from lombok, so we will know this is working fine

        log.info("Received Notification for order = {}", orderPlacedEvent.getOrderNumber());
    }

}
