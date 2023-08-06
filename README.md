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

## Keycloak : Securing Microservices Architecture

This repository demonstrates the implementation of Keycloak as an authorization server to secure microservices in our architecture. Prior to integrating Keycloak, all services were accessible without any authentication, which posed security risks. By utilizing Keycloak, we have fortified the system with robust authentication and authorization mechanisms.

### Getting Started
To run Keycloak, we recommend using Docker, which simplifies the setup process. Follow these steps:

1. Ensure you have Docker installed on your system.

2. Open a terminal and execute the following command to run the Keycloak container: 

    docker run -p 9090:8080 -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:22.0.1 start-dev
   
   This command will pull the Keycloak image from Docker Hub and start the Keycloak server. It also sets up the admin user with the provided credentials (admin/admin in this case). Adjust the version number in the image tag if needed.

### Alternate Method (Local installation):
If you prefer not to use Docker, you can download the Keycloak zip file for your operating system from the official Keycloak website. Follow these steps:

1. Download the appropriate Keycloak zip file for your operating system.

2. Unzip the downloaded file and locate the keycloak.conf file inside the conf folder.

3. Open the keycloak.conf file and modify the http-port property at the end of the file to set your desired port, for example: http-port=9090

4. Start Keycloak by executing the following command in the downloaded folder: sudo bin/kc.sh start-dev

This will start Keycloak locally with the specified configuration, including the custom port (9090 in the example).

Please note that using Docker is recommended for consistency and ease of deployment across various environments. However, the alternate method provides flexibility for specific use cases or preferences.

## Authentication Mechanism for Discovery Server

In our microservices architecture, we have successfully implemented an authentication mechanism using Keycloak for our order and product services. However, we have yet to configure authentication for our discovery server. The reason is that the discovery server is accessed through a web browser, not through a tool like Postman, which allows us to add authentication parameters easily.

To address this, we will enable basic authentication for the discovery server. Basic authentication allows us to provide a username and password when accessing the discovery server's URL. This way, we can secure access to the server without the need for complex token-based authentication like in the other services.

### Setting up Basic Authentication for Discovery Server

To enable basic authentication for the discovery server, follow these steps:

1. Open the configuration file of the discovery server (e.g., application.properties).

2. Add the following properties to enable basic authentication:

#### Enable basic authentication

spring.security.user.name=your_username

spring.security.user.password=your_password


Replace your_username and your_password with the desired credentials. The ROLE_USER specifies the role assigned to the authenticated user.

Save the changes and restart the discovery server for the new configuration to take effect.

### Accessing the Discovery Server

After enabling basic authentication, when you access the discovery server's URL through a web browser, you will be prompted to enter the username and password. Upon successful authentication, you will be granted access to the discovery server's information and endpoints.

Please note that basic authentication is suitable for browser-based access but may not be ideal for programmatic access or API calls. For API calls, we recommend continuing to use the Keycloak-based authentication in the order and product services, as it provides more robust security features.

With basic authentication in place for the discovery server, our microservices architecture will be better protected against unauthorized access, ensuring a more secure environment for our applications.

## Circuit Breaker

The Circuit Breaker pattern is mainly used when we want to ensure resilient communication between our services.

<img width="677" alt="Screenshot 2023-08-04 at 10 42 33 PM" src="https://github.com/Adeshppp/Shopit-A-Microservices-Based-Online-Shopping-Platform/assets/60222871/0f22667d-ee13-4f2c-a7a9-67ddc7172902">

In our microservices architecture, the order service communicates with the inventory service to check the availability of products using a Web Client, which operates like synchronous communication. However, synchronous communication poses certain challenges. The inventory service may not always be available, and there might be delays in its response when called from the order service. Additionally, remote service calls can be slow if something goes wrong with the inventory service, such as performance issues or database problems, resulting in slow API calls.

To address these potential issues and ensure system resilience, we need to handle failures gracefully and avoid abrupt terminations of requests. We aim to "fail fast" and provide resiliency to cope with these challenges in a microservice environment.

The Circuit Breaker pattern is a mechanism that helps us achieve this goal. It involves maintaining a set of states within our application to manage potential failures in remote service calls.

By implementing the Circuit Breaker pattern, we can improve the stability and reliability of our microservices architecture, ensuring that our system can gracefully handle issues and maintain responsiveness.

### State Diagram of Circuit Breaker Pattern

<img width="586" alt="Screenshot 2023-08-04 at 10 47 54 PM" src="https://github.com/Adeshppp/Shopit-A-Microservices-Based-Online-Shopping-Platform/assets/60222871/5b49cd7b-c174-4927-acd1-f1298ae59660">

As shown in the above diagram of circuit breaker states, the Circuit Breaker pattern helps manage communication failures between services. In our case, the order service and the inventory service communicate with each other. If there is a failure of communication due to a network issue or a database problem, the circuit breaker enters the "open" state from the "closed" state and prevents calls from the order service to the inventory service for a certain amount of time. The duration of this period can be configured according to our needs. During this time, the circuit breaker can either throw an error message or execute a fallback logic to handle the situation gracefully.

After the specified time period, the circuit breaker enters the "half-open" state and begins to check whether the requests are going through successfully by gradually allowing some API calls from the order service to the inventory service. If these requests are executed successfully, the circuit breaker transitions back to the "closed" state, indicating that the communication is restored and reliable. However, if the requests continue to fail, the circuit breaker will revert to the "open" state, ensuring that further calls to the inventory service are blocked until it's safe to retry.

The Circuit Breaker pattern helps protect the overall system from cascading failures and ensures that the communication between microservices remains robust and resilient.

To implement this logic we are using Resilience4J library.

We can check the health of actuator by hitting http://localhost:52476/actuator/health

replace the port number with the order-service's port number.

## Distributed Tracing with Spring Cloud Sleuth and Zipkin

In our system, we have already implemented the circuit breaker pattern with functionalities such as retry, timeout, and fallback mechanisms, provided by the resilience4j library. This has made our system quite resilient, and we can check logs to track down issues. However, relying solely on logs may not be sufficient in a production setting, where there could be millions of logs to manually inspect. To address this, we have adopted a design pattern called distributed tracing, which helps us track requests from start to end.

<img width="947" alt="Screenshot 2023-08-06 at 5 31 11 AM" src="https://github.com/Adeshppp/Shopit-A-Microservices-Based-Online-Shopping-Platform/assets/60222871/141e4fa5-a242-4c14-9134-1771557c98f0">

### How Distributed Tracing Works

As shown in the diagram above, when a user places an order, the request first passes through an API Gateway, then reaches the order service, and eventually calls the inventory service to check product availability. To trace this flow of requests, we use a trace ID and span ID.

* **Trace ID**: This is a unique identifier assigned to a request. It allows us to track the entire lifecycle of the request.

* **Span ID**: A span represents a trip to a service. For example, in our case, a user placing an order involves three trips. The first trip goes to the API Gateway, the second to the order service, and the last one to the inventory service. Each span has a unique span ID, which helps identify issues that may occur during the request's lifecycle.

### Spring Cloud Sleuth and Zipkin

We have integrated Spring Cloud Sleuth, a distributed tracing framework, into our system. Spring Cloud Sleuth helps generate and propagate trace IDs and span IDs across different services, making it easy to follow the request flow.

To visualize and analyze this tracing information, we use a tool called Zipkin. Zipkin provides a user-friendly interface to view and analyze the traces, helping us quickly identify and troubleshoot any issues that may arise in the system.

By leveraging distributed tracing with Spring Cloud Sleuth and Zipkin, we can gain valuable insights into our system's behavior and ensure a more reliable and efficient production environment.

[to access zipkin I have used docker way](https://zipkin.io/pages/quickstart.html)

I have used below command to run docker container.

``` docker run -d -p 9411:9411 openzipkin/zipkin ```

You can access Zipkin on port 9411 by hitting: http://localhost:9411

## Contributing

We welcome contributions to improve and expand the functionality of this microservices architecture. If you find any issues or have new ideas, please feel free to open an issue or submit a pull request.

Let's secure our microservices with Keycloak and build a safer and more reliable system together!


