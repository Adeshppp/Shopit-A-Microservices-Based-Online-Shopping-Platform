package com.OnlineShopping.orderservice.event;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



// We are sending this class as a JSON message as a notification.
// Simple (Plain Old JAVA Object)POJO class
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderPlacedEvent {
    private String orderNumber;
}
