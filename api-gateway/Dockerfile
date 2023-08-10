## Use the official OpenJDK image as a base image
#FROM openjdk:17
#
## Set the working directory inside the container
#WORKDIR /app
#
## Copy the executable JAR file from the build context into the container
#COPY target/*.jar /app/app.jar
#
## Expose the port on which your application will run (replace '8080' with your actual port)
##EXPOSE 8080
#
## Command to run your Spring Boot application when the container starts
#CMD ["java", "-jar", "app.jar"]


#FROM eclipse-temurin:17.0.4.1_1-jre as builder
#WORKDIR extracted
#ADD target/*.jar app.jar
#RUN java -Djarmode=layertools -jar app.jar extract


FROM eclipse-temurin:17.0.4.1_1-jre as builder
WORKDIR extracted
ADD target/*.jar app.jar
RUN java -Djarmode=layertools -jar app.jar extract

FROM eclipse-temurin:17.0.4.1_1-jre
WORKDIR application
COPY --from=builder extracted/dependencies/ ./
COPY --from=builder extracted/spring-boot-loader/ ./
COPY --from=builder extracted/snapshot-dependencies/ ./
COPY --from=builder extracted/application/ ./
EXPOSE 8080
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]
