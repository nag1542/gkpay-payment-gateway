# GKPay Payment Gateway (Phase 1)

This repository contains two Spring Boot microservices for a simplified payment gateway:

1. **payment-service** (port `8081`) — Gateway API and payment persistence in MySQL.
2. **simulator-service** (port `8082`) — External provider simulator (70% success / 30% failed).

## Tech Stack

- Java 17
- Spring Boot
- Spring Data JPA
- MySQL
- Maven
- Lombok

## Project Structure

```text
gkpay-payment-gateway
├── pom.xml
├── payment-service
│   ├── pom.xml
│   └── src/main
│       ├── java/com/gkpay/payment
│       │   ├── client
│       │   ├── config
│       │   ├── controller
│       │   ├── dto
│       │   ├── entity
│       │   ├── exception
│       │   ├── repository
│       │   └── service
│       └── resources
│           ├── application.yml
│           └── db/schema.sql
└── simulator-service
    ├── pom.xml
    └── src/main
        ├── java/com/gkpay/simulator
        │   ├── config
        │   ├── controller
        │   ├── dto
        │   ├── exception
        │   └── service
        └── resources/application.yml
```

## payment-service APIs

### 1) Create Payment

`POST /payments`

Request:

```json
{
  "amount": 1200.5,
  "currency": "INR",
  "paymentMethod": "CARD",
  "paymentToken": "tok_123",
  "merchantOrderId": "ORD_456"
}
```

Response:

```json
{
  "paymentId": "PAY_AB12CD34EF56",
  "merchantOrderId": "ORD_456",
  "amount": 1200.5,
  "status": "SUCCESS",
  "createdAt": "2026-04-19T10:00:00"
}
```

### 2) Get Payment

`GET /payments/{paymentId}`

## simulator-service API

### Simulate Provider Payment

`POST /simulate/payment`

Request:

```json
{
  "paymentId": "PAY_AB12CD34EF56",
  "amount": 1200.5,
  "paymentMethod": "CARD"
}
```

Response:

```json
{
  "status": "SUCCESS"
}
```

## Thread-Safety and Statelessness

- No shared mutable state in services.
- `paymentId` generated with UUID-based strategy and uniqueness check.
- Transactional boundaries in payment-service with `@Transactional`.
- Simulator uses `ThreadLocalRandom` for concurrency-safe randomness.

## MySQL Schema

SQL file location:

- `payment-service/src/main/resources/db/schema.sql`

## Local Run Instructions

### Prerequisites

- Java 17
- Maven 3.9+
- MySQL running on `localhost:3306`
- MySQL credentials matching `payment-service/src/main/resources/application.yml` (default `root/root`)

### 1) Create DB and table

Run:

```bash
mysql -u root -p < payment-service/src/main/resources/db/schema.sql
```

### 2) Build both services

From repo root:

```bash
mvn clean package
```

### 3) Start simulator-service (port 8082)

```bash
mvn -pl simulator-service spring-boot:run
```

### 4) Start payment-service (port 8081)

In a second terminal:

```bash
mvn -pl payment-service spring-boot:run
```

### 5) Test via curl

Create payment:

```bash
curl -X POST http://localhost:8081/payments \
  -H "Content-Type: application/json" \
  -d '{
    "amount": 500,
    "currency": "INR",
    "paymentMethod": "UPI",
    "paymentToken": "upi_token_001",
    "merchantOrderId": "ORDER_1001"
  }'
```

Get payment by ID:

```bash
curl http://localhost:8081/payments/PAY_AB12CD34EF56
```

