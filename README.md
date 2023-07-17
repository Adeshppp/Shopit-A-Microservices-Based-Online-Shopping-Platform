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

## Service Discovery Using Netflix Eureka : To create instances of microservice

In order to maintain multiple instances of a microservices Netflix Eureka is used. as it helps to each client microservice to maintain local registry.

![Screenshot 2023-05-06 at 6 29 38 AM](https://user-images.githubusercontent.com/60222871/236623539-5add7e23-6370-41cb-84b2-a58d97b5d75f.png)

In above examplemultiple instances are there and each instance have dynamic IP address. In this case order service don't know to which instance to call. Here **Service Discovery Pattern** comes into picture.

![Screenshot 2023-05-06 at 6 33 16 AM](https://user-images.githubusercontent.com/60222871/236623675-5f8fc695-b461-4ecc-b392-3b2ea47f38de.png)

After creation of each instance, it will get configured with the Discovery server and that server will add that instance as aclient in its registry.


### How it works:

On creation of instance, it will get configured with discovery client and if another microservice wants to use that microservice then discovery server let that microservice know that which instance to route.

![Screenshot 2023-05-06 at 6 34 26 AM](https://user-images.githubusercontent.com/60222871/236623775-04762b18-f94f-4817-a9f2-f712d37325e4.png)

In case of failure of Discovery Server, once communicated each microservice also keeps local registry about other microservice's instances.
as use that list to connect to that perticular service.

![Screenshot 2023-05-06 at 6 38 57 AM](https://user-images.githubusercontent.com/60222871/236624024-91cdb7f2-3f9d-4ff1-b9c7-3ab980a813e6.png)

## API Gateway : Single entry point for external consumers to access the microservices in the system

<img width="1059" alt="Screenshot 2023-05-08 at 10 25 19 AM" src="https://user-images.githubusercontent.com/60222871/236850191-9128f29d-8257-44bf-a597-017f7d8e63be.png">

### Need:

To call particular service we need to call that service on arbitrary port for example 8080, 8081, 8082. This should be good in development environment but in Production environment this won't work as there could be many instances of one service. here API gateway comes in a picture.

<img width="1090" alt="Screenshot 2023-05-08 at 10 27 16 AM" src="https://user-images.githubusercontent.com/60222871/236850457-780cce80-ca85-4e1e-8324-c58d5db368d8.png">

In this project, we are going to user spring cloud's own implementation of API gateway called "[Spring Cloud Gateway](https://spring.io/projects/spring-cloud-gateway)".

We can modify request in API gateway by using filters.

Along with **routing**, it also provides additional cost cutting concerns as below:

1. Authentication  
2. Security : Keycloak
3. Load Balancing : to manage multiple instances of service
4. SSL Termination : terminates https calls at API Gateway leyer

## Getting Started

To get started with the application, follow these steps:

Clone the repository
Install the required dependencies
Start each service using the provided scripts
Access the application at http://localhost:8080
For detailed instructions on setting up and running the application, please refer to the Installation Guide.

## Contributing

Contributions to this project are always welcome! If you have any ideas or suggestions, please feel free to submit a pull request or create an issue on the repository.

