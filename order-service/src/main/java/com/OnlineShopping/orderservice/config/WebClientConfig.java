package com.OnlineShopping.orderservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration // I am going to maintain configuration for webclient class
public class WebClientConfig {

    // defining a bean of webclient
    @Bean
    public WebClient webClient(){
        return WebClient.builder().build();
    }
}
