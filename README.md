### Overview
This application is to create a trading application and getting the position at a particular time

### Tech Stack
The service is [Spring Boot](https://spring.io/projects/spring-boot) application that is written in Java 8 and is using h2 as in memory data store.

### Running application locally
Start Docker in machine and run `docker-compose up -d` from the workspace
Building the application run `mvn clean package`
Running the application run `java -jar target\trading-0.0.1-SNAPSHOT.jar`

Application will listen to the topic `trading-topic` for consuming the events and store to h2.
Application also exposing a API end  point `http://localhost:8080/position` which will provide the details of position at any point
