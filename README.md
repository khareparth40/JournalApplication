# Journal Application – Spring Boot Backend

## Project Overview

The Journal Application is a secure backend system that allows users to create, manage, and access personal journal entries through REST APIs. This project demonstrates real-world backend architecture, authentication and authorization, and performance optimization using caching.

The application showcases how modern backend systems handle:

- Secure user access  
- Data isolation between users  
- Stateless authentication  
- Integration with external services  
- Scalable design patterns  

---

## Project Objectives

- Build a secure RESTful backend using Spring Boot  
- Implement JWT-based authentication and role-based authorization  
- Ensure data privacy so users can only access their own journal entries  
- Improve performance using Redis caching  
- Integrate an external API to demonstrate third-party service communication  
- Follow clean layered architecture used in real-world backend systems  

---

## Tech Stack

Backend  
- Java  
- Spring Boot  

Security  
- Spring Security  
- JWT (JSON Web Token) Authentication  

Database  
- MySQL  

Caching  
- Redis  

API and Documentation  
- REST APIs  
- Swagger / OpenAPI  

Build Tool  
- Maven  

---

## Core Features

### Authentication and Authorization
- Users log in using credentials  
- JWT token is generated after successful authentication  
- All protected APIs require a valid token  
- Role-based access control (User/Admin)

### Journal Management
- Create journal entries  
- View personal journal entries  
- Update entries  
- Delete entries  

Each journal entry is linked to a specific user to ensure data isolation.

### Redis Caching
- Frequently accessed data is cached  
- Reduces database load  
- Improves API response performance  

### External API Integration
- Demonstrates how backend services interact with third-party APIs  
- Handles API calls and response mapping  

### API Documentation
- Swagger UI is available for testing endpoints  
- Helps verify APIs without external tools  

---

## Application Architecture

The project follows a layered architecture:

Controller → Service → Repository → Database  
                     ↓  
                External Services  
                (Redis, External API)

- Controller Layer handles HTTP requests  
- Service Layer contains business logic  
- Repository Layer manages database interaction using JPA  
- Security Layer handles JWT filter and authentication flow  

---

## How to Run the Project

### Prerequisites
- Java 8 or higher  
- Maven  
- MySQL running locally  
- Redis running locally  

### Configuration

Create the file:

src/main/resources/application.properties

Add your local configuration:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/journaldb
spring.datasource.username=your_username
spring.datasource.password=your_password

jwt.secret=your_jwt_secret
jwt.expiration=86400000

spring.redis.host=localhost
spring.redis.port=6379
