## Application Architecture

This project demonstrates a microservices architecture using Spring Boot with two distinct inter-service communication approaches: **REST with Protocol Buffers** and **gRPC**. The same business logic (Account and Customer management) is implemented in parallel for hands-on comparison.

### Microservices Overview

The repository contains **5 Spring Boot applications** and one service discovery component:

#### Service Discovery
- **discovery-service**  
  - **Port:** 8761  
  - **Role:** Netflix Eureka server for service registration and discovery  
  - **Dashboard URL:** http://localhost:8761  

#### Business Services (REST + Protocol Buffers)
- **account-service**  
  - **Port:** 2222 (configurable via `PORT` env var)  
  - **Communication:** HTTP + Protobuf  
  - **Proto file:** `account-service/src/main/proto/account.proto`  

- **customer-service**  
  - **Port:** 3333 (configurable via `PORT` env var)  
  - **Communication:** HTTP + Protobuf  
  - **Proto file:** `customer-service/src/main/proto/customer.proto`  

#### Business Services (gRPC)
- **account-service-grpc**  
  - **HTTP Port:** 8081  
  - **gRPC Port:** 9091  
  - **Library:** `grpc-server-spring-boot-starter`  
  - **Services:** `FindByNumber`, `FindByCustomer`, `FindAll`, `AddAccount`

- **customer-service-grpc**  
  - **HTTP Port:** 8081  
  - **gRPC Port:** 9091  
  - **Services:** `FindByPesel`, `FindById`, `FindAll`, `AddCustomer`  
  - **Discovery:** Registers with Eureka

### Technology Stack

- **Java 21**  
- **Spring Boot 3.4.5**  
- **Spring Cloud 2024.0.1** (Eureka, LoadBalancer, OpenFeign)  
- **Protocol Buffers 4.31.1**  
- **gRPC 1.63.0**  
- **Maven 3.8+**  
- **Docker & Docker Compose** (optional)

### Communication Patterns

#### REST + Protobuf

```
Client → API Gateway → Service Discovery → Customer Service (HTTP:3333, Protobuf) → Account Service (HTTP:2222, Protobuf)
```

#### gRPC

```
Client → gRPC Client → Service Discovery → Customer Service gRPC (9091) → Account Service gRPC (9091)
```

#### Service Discovery Flow

1. Start **discovery-service** (8761).  
2. Each microservice registers with Eureka.  
3. Clients and services discover each other by querying Eureka.  
4. Spring Cloud LoadBalancer handles service-side load balancing.  
5. Health checks via Spring Boot Actuator endpoints.

## Running Applications Locally

### Prerequisites

- **Java 21+** (`java --version`)  
- **Maven 3.8+** (`mvn --version`)  
- **Git 2+** (`git --version`)  
- Optional: **Docker & Docker Compose**  

### Building the Applications

1. Clone the repo:
   ```bash
   git clone https://github.com/piomin/sample-microservices-protobuf.git
   cd sample-microservices-protobuf
   ```
2. Compile (includes Protobuf & gRPC codegen):
   ```bash
   mvn clean compile
   ```
3. Package JARs (skip tests for speed):
   ```bash
   mvn clean package -DskipTests
   ```

### Service Startup Order

> **Important:** Always start in this sequence to ensure proper registration.

1. **Discovery Service**  
   ```bash
   cd discovery-service  
   mvn spring-boot:run
   ```
2. **REST + Protobuf Approach**  
   ```bash
   # In parallel terminals:
   cd account-service && mvn spring-boot:run
   cd customer-service && mvn spring-boot:run
   ```
   **OR**  
   **gRPC Approach**  
   ```bash
   cd account-service-grpc && mvn spring-boot:run
   cd customer-service-grpc && mvn spring-boot:run
   ```

### Running with JARs

```bash
# Discovery
java -jar discovery-service/target/discovery-service-*.jar
# Choose one approach per microservice:
java -jar account-service/target/*.jar
java -jar customer-service/target/*.jar
# OR for gRPC:
java -jar account-service-grpc/target/*.jar
java -jar customer-service-grpc/target/*.jar
```

### Verification & Testing

- **Eureka Dashboard:** http://localhost:8761  
- **Health Checks:**  
  ```bash
  curl http://localhost:8761/actuator/health
  curl http://localhost:2222/actuator/health
  curl http://localhost:3333/actuator/health
  curl http://localhost:9091/actuator/health
  ```
- **REST API Example:**  
  ```bash
  curl -H "Accept: application/json" http://localhost:3333/customers
  ```
- **gRPC Example (using grpcurl):**  
  ```bash
  grpcurl -plaintext -d '{}' localhost:9091 model.CustomersService/FindAll
  ```

### Port Reference

| Service                   | HTTP Port | gRPC Port | Env Var | Protocol        |
|---------------------------|-----------|-----------|---------|-----------------|
| discovery-service         | 8761      | –         | –       | HTTP            |
| account-service           | 2222      | –         | PORT    | HTTP + Protobuf |
| customer-service          | 3333      | –         | PORT    | HTTP + Protobuf |
| account-service-grpc      | 8081      | 9091      | –       | gRPC            |
| customer-service-grpc     | 8081      | 9091      | –       | gRPC            |

### Troubleshooting & Tips

- **Port Conflicts:**  
  ```bash
  lsof -ti:8761 | xargs kill -9
  ```
- **Protobuf Errors:**  
  ```bash
  mvn clean compile -X
  ```
- **Increase JVM Memory:**  
  ```bash
  export MAVEN_OPTS="-Xmx2G -Xms1G"
  mvn spring-boot:run
  ```
- **Hot Reloading:** Add DevTools dependency:
  ```xml
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <scope>runtime</scope>
    <optional>true</optional>
  </dependency>
  ```
- **gRPC Testing:** Use `grpcurl` or BloomRPC for introspection.