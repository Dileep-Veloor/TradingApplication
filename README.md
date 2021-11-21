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


### Options considered
Use Concurrent HashMap 
Use Redis cache for data store(For this i might need more time, since not used earlier)
Use H2 Memory DB 

Store only Events in the table and calculate positions from the events details, every time call is made for getting the positions.
Store both events and current positions in the data store. -> Opted for this. Since once position is stored , this could be used for separate purposes in future. Seems more scalable, rather than calculating each time.

### Assumptions
We need to Buy securities first before we even sell the securities
Even if same event id comes for Buy/Sell or multiple events available, currently check is not done to avoid that. Depending on the requirement confirmation we need to change that.
Exceptions handled for SELL and CANCEL events and logged as ERRORS for alerting. Also have not categorized as events which are retryable and non-retryable. All the events which throw exceptions will be retired 3 times and discarded.
Could add DLQ(Dead letter queue) to store the discarded message and have some processing logic based on those.
Simple GET API is provided to get the position. As data grows this may be less performant, we can have either filter or paging introduced to reduced the volumes fetched. Or could have request based on account identifier as path variable.


### Code improvements
Could move the logic around calculating quality to factory class. Introduce an interface and have 3 separate classes of Buy/Sell/Cancel and implement common method. By doing this if we have complicated logic, this can be moved out from the service class.
Could add integration test with Embedded kafka for testing the listener.
