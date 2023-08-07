package com.OnlineShopping.orderservice.service;


import com.OnlineShopping.orderservice.dto.InventoryResponse;
import com.OnlineShopping.orderservice.dto.OrderLineItemsDto;
import com.OnlineShopping.orderservice.dto.OrderRequest;
import com.OnlineShopping.orderservice.event.OrderPlacedEvent;
import com.OnlineShopping.orderservice.model.Order;
import com.OnlineShopping.orderservice.model.OrderLineItems;
import com.OnlineShopping.orderservice.repository.OrderRepository;
import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional //spring will automatically create and commit the transactions
public class OrderService {

    @Autowired
    private final OrderRepository orderRepository;

    // By default, WebClient makes asynchronous requests.
    private final WebClient.Builder webClientBuilder;

    private final io.micrometer.tracing.Tracer tracer;

    @Autowired
    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

    public String placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
                .stream()
//                .map(orderLineItemsDto -> mapToDto(orderLineItemsDto))
                .map(this::mapToDto)
                .toList();
        order.setOrderLineItemsList(orderLineItems);

        log.info("order : "+order);

        // collecting product skuCode from order object
        List<String> skuCodes = order.getOrderLineItemsList().stream().map(OrderLineItems::getSkuCode).toList();

        // ==================================================== Inventory check ====================================================

        // In the Inventory Service Controller class, there is a GET method called isInStock() that returns a Boolean value.
        // This method is running on port 8082.

        Span inventoryServiceLookup = tracer.nextSpan().name("InventoryServiceLookup");

        try (Tracer.SpanInScope spanInScope = tracer.withSpan(inventoryServiceLookup.start())) {
            InventoryResponse[] inventoryResponseArray = webClientBuilder.build().get()
                    .uri("http://inventory-service/api/inventory", uriBuilder -> uriBuilder.queryParam("skuCode",skuCodes).build())//skuCodes is a List of skuCode so uriBuilder, which will build uri in this format "http://localhost:8082/api/inventory?skuCode=iphone-14&skuCode=iphone-13"
                    .retrieve() // Perform HTTP request and retrieve response body
                    .bodyToMono(InventoryResponse[].class) // Convert the HTTP response body to a Mono of type InventoryResponse
                    .block(); // Make the WebClient request synchronous and block the execution until the response is received.


            //        boolean allProductsInStock = Arrays.stream(inventoryResponseArray).allMatch(InventoryResponse::isInStock);

            log.info("inventoryResponseArray :"+inventoryResponseArray);
            //      todo: check available stock for each product and post on log if not available
            //
            //if(allProductsInStock) {
            orderRepository.save(order);
            kafkaTemplate.send("notificationTopic",new OrderPlacedEvent(order.getOrderNumber()));

            return "Order Placed Successfully!";
            // todo: update inventory
            //}
            //        else throw new IllegalArgumentException("Product not in stock, please try again later.");

            // ==========================================================================================================================
        }
        finally {
            // Once done remember to end the span. This will allow collecting
            // the span to send it to a distributed tracing system e.g. Zipkin
            inventoryServiceLookup.end();
        }



    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }

}
