# Shopit-A-Microservices-Based-Online-Shopping-Platform

Shopit-A-Microservices-Based-Online-Shopping-Platform is an online shopping application built using microservices architecture. It uses both MySQL AWS RDS and NoSQL MongoDB databases and was developed using Spring Boot, Hibernate, and JPA. The application is designed to provide a scalable, fault-tolerant solution that can handle a high volume of transactions.

## Features

User authentication and authorization
Product catalog browsing
Shopping cart management
Order processing and tracking
Payment integration
Architecture

The application follows a microservices architecture with the following services:


Each service is responsible for a specific functionality and communicates with other services using REST APIs. The application uses a combination of MySQL AWS RDS and NoSQL MongoDB databases to store data.

## Architecture

### Solution Architecture

![Solution Architecture](https://user-images.githubusercontent.com/60222871/235648774-543b4f4d-1ba8-425d-a9e7-d2d53b7d9f54.png)

### Logical Architecture

![Logical Architecture](https://user-images.githubusercontent.com/60222871/235648813-0b644998-c60a-4223-975d-31576195e144.png)

## Http Client

![Inter Process Communication](https://user-images.githubusercontent.com/60222871/235648842-f29b147d-8646-4096-bc65-94021ce0258c.png)

### Synchronous Communication

Once order service asks for product availability to inventory service, it will reply back with response, known as Synchronous Communication.
if service A requests service B and wait for response from service B, this kind of communication known as **Synchronous Communication**.
This kind of communication usually done by HTTP clients. In springboot system we have two clients as follows:

1. RestTemplate
2. WebClient

RestTemplate is there in springboot framework and WebClient is there in WebFlux project.

NOTE: [As of 5.0 this class is in maintenance mode, with only minor requests for changes and bugs to be accepted going forward. Please, consider using the org.springframework.web.reactive.client.WebClient which has a **_more modern API_** and supports sync, async, and streaming scenarios.](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/client/RestTemplate.html)

so we are using WebClient.


### Asynchronous Communication

If Order service requests Inventory service and does not care about response then It is called as Asynchronous communication. It follows fire and forget pattern.


## Getting Started

To get started with the application, follow these steps:

Clone the repository
Install the required dependencies
Start each service using the provided scripts
Access the application at http://localhost:8080
For detailed instructions on setting up and running the application, please refer to the Installation Guide.

## Contributing

Contributions to this project are always welcome! If you have any ideas or suggestions, please feel free to submit a pull request or create an issue on the repository.

