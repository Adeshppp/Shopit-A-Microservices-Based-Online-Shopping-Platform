package com.OnlineShopping.orderservice.service;


import com.OnlineShopping.orderservice.dto.OrderLineItemsDto;
import com.OnlineShopping.orderservice.dto.OrderRequest;
import com.OnlineShopping.orderservice.model.Order;
import com.OnlineShopping.orderservice.model.OrderLineItems;
import com.OnlineShopping.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional //spirng will automatically create ans commit the transactions
public class OrderService {

    @Autowired
    private final OrderRepository orderRepository;

    // By default, WebClient makes asynchronous requests.
    private final WebClient webClient;

    public void placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
                .stream()
//                .map(orderLineItemsDto -> mapToDto(orderLineItemsDto))
                .map(this::mapToDto)
                .toList();
        order.setOrderLineItemsList(orderLineItems);

        // ==================================================== Inventory check ====================================================

        // In the Inventory Service Controller class, there is a GET method called isInStock() that returns a Boolean value.
        // This method is running on port 8082.
        Boolean result = webClient.get()
                .uri("http://localhost:8082/api/inventory")
                .retrieve() // Perform HTTP request and retrieve response body
                .bodyToMono(Boolean.class) // Convert the HTTP response body to a Mono of type Boolean
                .block(); // Make the WebClient request synchronous and block the execution until the response is received.

        if(result) orderRepository.save(order);
        else throw new IllegalArgumentException("Product not in stock, please try again later.");

        // ==========================================================================================================================
    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }


}
