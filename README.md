# Shopit-A-Microservices-Based-Online-Shopping-Platform

Shopit is an innovative online shopping application that leverages the power of microservices architecture. With a blend of MySQL AWS RDS and NoSQL MongoDB databases, the platform is built using cutting-edge technologies like Spring Boot, Hibernate, and JPA, ensuring a robust, scalable, and fault-tolerant solution capable of handling a high volume of transactions.

## Features

* User authentication and authorization
* Comprehensive product catalog browsing
* Efficient shopping cart management
* Seamless order processing and tracking
* Smooth payment integration

## Architecture

The application is designed following a microservices architecture, where each service is responsible for a specific functionality and communicates with others through REST APIs. The combination of MySQL AWS RDS and NoSQL MongoDB databases ensures optimal data storage and retrieval.

### Solution Architecture

![Solution Architecture](https://user-images.githubusercontent.com/60222871/235648774-543b4f4d-1ba8-425d-a9e7-d2d53b7d9f54.png)

### Logical Architecture

![Logical Architecture](https://user-images.githubusercontent.com/60222871/235648813-0b644998-c60a-4223-975d-31576195e144.png)

## Http Client

![Inter Process Communication](https://user-images.githubusercontent.com/60222871/235648842-f29b147d-8646-4096-bc65-94021ce0258c.png)

### Synchronous Communication

Shopit employs **Synchronous Communication** when the order service requests product availability from the inventory service. In this case, the inventory service responds to the order service with a response. Shopit utilizes WebClient, which is the recommended choice over RestTemplate due to its modern API and support for sync, async, and streaming scenarios.

### Asynchronous Communication

When the Order service requests information from the Inventory service but doesn't require an immediate response, it follows an **Asynchronous Communication** pattern. In this case, the order service initiates a request and continues its operations without waiting for the response, following a fire and forget pattern.


## Service Discovery Using Netflix Eureka: Creating Instances of Microservices

To maintain multiple instances of microservices, Netflix Eureka is used. It enables each client microservice to maintain a local registry.

![Screenshot 2023-05-06 at 6 29 38 AM](https://user-images.githubusercontent.com/60222871/236623539-5add7e23-6370-41cb-84b2-a58d97b5d75f.png)

In the example above, multiple instances exist, each with a dynamic IP address. In this case, the order service doesn't know which instance to call. here Service Discovery Pattern comes into play.

![Screenshot 2023-05-06 at 6 33 16 AM](https://user-images.githubusercontent.com/60222871/236623675-5f8fc695-b461-4ecc-b392-3b2ea47f38de.png)

After creating each instance, it gets configured with the Discovery server, and the server adds that instance as a client in its registry.

### How it works:

On the creation of an instance, it gets configured with the discovery client. If another microservice wants to use that microservice, the discovery server lets that microservice know which instance to route to.

![Screenshot 2023-05-06 at 6 34 26 AM](https://user-images.githubusercontent.com/60222871/236623775-04762b18-f94f-4817-a9f2-f712d37325e4.png)

In the case of a failure of the Discovery Server, once communicated, each microservice also keeps a local registry about other microservice's instances and uses that list to connect to that particular service.

![Screenshot 2023-05-06 at 6 38 57 AM](https://user-images.githubusercontent.com/60222871/236624024-91cdb7f2-3f9d-4ff1-b9c7-3ab980a813e6.png)

## API Gateway: Single Entry Point for External Consumers

<img width="1059" alt="Screenshot 2023-05-08 at 10 25 19 AM" src="https://user-images.githubusercontent.com/60222871/236850191-9128f29d-8257-44bf-a597-017f7d8e63be.png">

### Need:

In the development environment, calling a particular service on arbitrary ports (e.g., 8080, 8081, 8082) may work fine. However, in a production environment with multiple instances of one service, this approach is not feasible. This is where the API gateway comes into the picture.

<img width="1090" alt="Screenshot 2023-05-08 at 10 27 16 AM" src="https://user-images.githubusercontent.com/60222871/236850457-780cce80-ca85-4e1e-8324-c58d5db368d8.png">

In this project, we use Spring Cloud's own implementation of an API gateway called "[Spring Cloud Gateway](https://spring.io/projects/spring-cloud-gateway)". It allows modifying requests using filters and provides additional concerns like authentication, security (Keycloak), load balancing for multiple service instances, and SSL termination for terminating HTTPS calls at the API Gateway layer.

## Getting Started

To get started with the application, follow these steps:

1. Clone the repository
2. Install the required dependencies
3. Start each service using the provided scripts
4. Access the application at http://localhost:8080

For detailed instructions on setting up and running the application, please refer to the Installation Guide.

## Contributing

Contributions to this project are always welcome! If you have any ideas or suggestions, please feel free to submit a pull request or create an issue on the repository. Let's collaborate and make Shopit even better!





