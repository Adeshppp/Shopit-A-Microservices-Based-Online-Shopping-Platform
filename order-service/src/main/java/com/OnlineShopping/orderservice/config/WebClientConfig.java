package com.OnlineShopping.orderservice.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration // I am going to maintain configuration for webclient class
public class WebClientConfig {

    // defining a bean of webclient
    @Bean
    @LoadBalanced
    public WebClient.Builder webClientBuilder(){
        return WebClient.builder();
    }
}


// by using webclient.builder, I am enabling client side load-balancing.
// note: to use WebClient class I have added spring web flux dependency for maven.
