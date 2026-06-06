# Inventory Search Management System

## Overview

Inventory Search Management System is a Spring Boot 3 application developed using Java 17 and MongoDB.

The application provides a dynamic inventory search API that allows users to search inventory records using multiple optional search criteria. All supplied filters are treated as AND conditions.

The solution demonstrates:

* REST API Design
* Dynamic MongoDB Query Construction
* Pagination
* Sorting
* Validation
* Global Exception Handling
* OpenAPI / Swagger Documentation
* Layered Architecture
* Unit Testing

---

## Technology Stack

| Technology          | Version |
| ------------------- | ------- |
| Java                | 17      |
| Spring Boot         | 3.x     |
| Spring Data MongoDB | 3.x     |
| MongoDB             | 7.x     |
| Maven               | 3.x     |
| Lombok              | Latest  |
| SpringDoc OpenAPI   | Latest  |
| JUnit 5             | Latest  |
| Mockito             | Latest  |

---

## Architecture

The application follows a layered architecture.

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
MongoTemplate
    ↓
MongoDB
```

### Controller Layer

Responsible for:

* Receiving HTTP Requests
* Returning HTTP Responses
* Delegating processing to Service Layer

### Service Layer

Responsible for:

* Business Validation
* Request Processing
* Response Transformation

### Repository Layer

Responsible for:

* Dynamic Query Creation
* Database Interaction
* Pagination and Sorting

### Validator Layer

Responsible for:

* Request Validation
* Business Rule Validation

---

## Why MongoTemplate?

All search parameters are optional.

Using Spring Data Repository method naming would lead to a large number of query combinations.

MongoTemplate with Criteria API enables dynamic query generation and keeps the implementation clean and maintainable.

Example:

```text
Category Only

Category + Seller

Category + Seller + Location

Price Range Only

Any Combination of Filters
```

---

## Features

### Dynamic Search

Supports searching using:

* Name
* Category
* Sub Category
* Seller
* Location
* Model
* Price Range
* Stock Range
* Manufacturing Date
* Expiry Date

All filters are optional.

---

### Pagination

Request:

```json
{
  "pagination": {
    "page": 0,
    "limit": 10
  }
}
```

Default values:

```text
page = 0
limit = 10
```

---

### Sorting

Request:

```json
{
  "sorting": {
    "field": "PRICE",
    "direction": "DESC"
  }
}
```

Supported Fields:

* NAME
* CATEGORY
* PRICE
* STOCK
* SELLER
* LOCATION

Supported Directions:

* ASC
* DESC

---

## Request Validation

The application validates:

### Price Range

```text
minPrice <= maxPrice
```

### Stock Range

```text
minStock <= maxStock
```

### Date Validation

```text
manufacturingDate <= expiryDate
```

### Pagination Validation

```text
page >= 0
limit > 0
limit <= 500
```

---

## Exception Handling

Global Exception Handling is implemented using:

```java
@RestControllerAdvice
```

### Validation Error

Response:

```json
{
  "errorCode": "INV-400",
  "message": "Validation Failed",
  "details": [
    "minPrice cannot exceed maxPrice"
  ]
}
```

### Internal Server Error

Response:

```json
{
  "errorCode": "INV-500",
  "message": "Unexpected Error"
}
```

---

## API Endpoint

### Search Inventory

```http
POST /api/v1/inventories/search
```

### Sample Request

```json
{
  "category": "Electronics",
  "seller": "Amazon",
  "minPrice": 10000,
  "maxPrice": 70000,
  "pagination": {
    "page": 0,
    "limit": 10
  },
  "sorting": {
    "field": "PRICE",
    "direction": "DESC"
  }
}
```

### Sample Response

```json
{
  "content": [
    {
      "id": "665f8c1c3b7f1a12d2b3c4d5",
      "name": "Dell Inspiron",
      "category": "Electronics",
      "subCategory": "Laptop",
      "price": 55000,
      "stock": 25,
      "seller": "Amazon",
      "location": "Chennai",
      "model": "DI15"
    }
  ],
  "totalElements": 1,
  "totalPages": 1,
  "size": 10,
  "number": 0
}
```

---

## Swagger Documentation

Swagger UI:

```text
http://localhost:8080/swagger-ui/index.html
```

OpenAPI YAML:

```text
http://localhost:8080/v3/api-docs.yaml
```

Generated YAML is also included as:

```text
openapi.yaml
```

---

## MongoDB Configuration

Application Properties:

```properties
spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
spring.data.mongodb.database=local
```

---

## Running the Application

### Build

```bash
mvn clean install
```

### Run

```bash
mvn spring-boot:run
```

### Access Swagger

```text
http://localhost:8080/swagger-ui/index.html
```

---

## Design Decisions

### Why POST Instead of GET?

The search request contains:

* Multiple Optional Filters
* Pagination Object
* Sorting Object

Using GET would result in a large number of query parameters.

POST provides a cleaner request structure.

---

### Why BigDecimal For Price?

Price is a monetary value.

BigDecimal avoids floating-point precision issues associated with Double and is the recommended type for financial values.

---

### Why DTOs?

DTOs separate:

* API Contract
* Database Entity

This prevents exposing internal database fields directly to clients.

---

## Future Enhancements

* JWT Authentication
* Redis Caching
* Elasticsearch Integration
* Audit Logging
* Advanced Search Filters
* Role Based Access Control

---