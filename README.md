# circuit-breaker

There are multiple challenges when working with distributed systems. One of the challenges is to deal with failures by isolating them from the rest of the system. Resilient system can help isolate failures and stop cascading it to the rest of the system and it should remain healthy.

This repository contains a very simple implementation of Circuit Breaker with following requirement.

#### Requirement

1. Create two services (simple java bean) Service A and Service B

2. Service A calls Service B after every 5 seconds, service B simply responds "OK"

3. While service B is healthy, make it un-healthy by pressing 'u'.

4. Service A should stop calling Service B for next 1 minute (configurable) this means that circuit is open. Service A should throw some runtime exception and log in console every 5 seconds, the scheduler should never stop and keep calling every 5 seconds.

5. Press 'h' to bring Service B up again and become healthy.

6. When Service A tries again after 1 minute and if Service B is still un-healthy it should again wait for 1 minute before retrying, or else if Service B is healthy again then continue by calling it after 5 seconds (circuit breaker is Closed).

#### Working
To run this, clone this repository and run
```
mvn clean package
```
and then run
```
java -jar target\circuit-breaker.jar
```