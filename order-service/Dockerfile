# Stage 1: Build Stage
FROM eclipse-temurin:17.0.4.1_1-jre as builder

# Set the working directory within the container
WORKDIR extracted

# Add the application JAR to the container
ADD target/*.jar app.jar

# Run the application JAR in layered mode to extract layers
RUN java -Djarmode=layertools -jar app.jar extract

# Stage 2: Final Stage
FROM eclipse-temurin:17.0.4.1_1-jre

# Set the working directory within the container
WORKDIR application

# Copy the dependencies, loader, snapshot dependencies, and application layers from the builder stage
COPY --from=builder extracted/dependencies/ ./
COPY --from=builder extracted/spring-boot-loader/ ./
COPY --from=builder extracted/snapshot-dependencies/ ./
COPY --from=builder extracted/application/ ./

# Expose the port that the Spring Boot app listens on
EXPOSE 8084

# Set the entry point to start the Spring Boot application
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]
