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

![Screenshot 2023-04-29 at 8.22.35 AM.png](..%2F..%2F..%2F..%2F..%2F..%2F..%2Fvar%2Ffolders%2Fzw%2F10_qmd7j7z17296vgj8yj8qw0000gn%2FT%2FTemporaryItems%2FNSIRD_screencaptureui_2eH8oc%2FScreenshot%202023-04-29%20at%208.22.35%20AM.png)

### Logical Architecture

![Screenshot 2023-04-29 at 8.23.38 AM.png](..%2F..%2F..%2F..%2F..%2F..%2F..%2Fvar%2Ffolders%2Fzw%2F10_qmd7j7z17296vgj8yj8qw0000gn%2FT%2FTemporaryItems%2FNSIRD_screencaptureui_KPGWUv%2FScreenshot%202023-04-29%20at%208.23.38%20AM.png)

## Http Client

![Screenshot 2023-04-29 at 8.24.41 AM.png](..%2F..%2F..%2F..%2F..%2F..%2F..%2Fvar%2Ffolders%2Fzw%2F10_qmd7j7z17296vgj8yj8qw0000gn%2FT%2FTemporaryItems%2FNSIRD_screencaptureui_fyfKG3%2FScreenshot%202023-04-29%20at%208.24.41%20AM.png)

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

