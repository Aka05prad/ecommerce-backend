# ecommerce-backend
Production-grade E-Commerce Backend built using Spring Boot, MySQL, JWT Security and REST APIs.
# E-Commerce Backend System

## Project Overview

This project is a production-grade backend system for an E-Commerce platform built using Java and Spring Boot.

It manages:

- Users
- Products
- Shopping Cart
- Orders
- Payments
- Inventory

The backend exposes REST APIs which can be consumed by web or mobile applications.

---

## Tech Stack

- Java 17
- Spring Boot 3
- Spring Security (JWT Authentication)
- Spring Data JPA / Hibernate
- MySQL
- Maven
- Swagger UI
- JUnit & Mockito
- SLF4J Logging

---

## Project Architecture

The project follows a layered architecture:

Controller → Service → Repository → Entity

Packages used:

- controller
- service
- repository
- entity
- dto
- exception
- config
- security
- utils

---

## Features

### User Management

- User registration
- Login using JWT authentication
- Update user profile
- Role-based access (ADMIN, CUSTOMER)

### Product Management

- Add products (Admin)
- Update products (Admin)
- Delete products (Admin)
- View product list
- Pagination support

### Shopping Cart

- Add product to cart
- Update quantity
- Remove product
- View cart

### Order Management

- Checkout
- View order history
- Update order status

### Payment Simulation

- Simulated payment success or failure

### Inventory Management

- Automatic stock reduction after order
- Prevent ordering when stock is unavailable

---

## How to Run the Project

### Step 1: Clone the repository

git clone https://github.com/yourusername/ecommerce-backend.git

### Step 2: Create MySQL Database

CREATE DATABASE ecommerce_db;

### Step 3: Configure application.properties

spring.datasource.username=root  
spring.datasource.password=<>

### Step 4: Run the application

mvn spring-boot:run

---

## API Documentation

Swagger UI:

http://localhost:8080/swagger-ui.html

---

## Testing

Unit tests implemented using:

- JUnit 5
- Mockito

---

## Author

Akankshya Pradhan 
Trainee