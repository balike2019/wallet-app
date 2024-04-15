
API Documentation
=================

http://localhost:8080/wallet-app/swagger-ui/index.html

# Wallet App

This is a simple wallet application that allows users to create an account, deposit money, withdraw money, transfer money to other users, and view their transaction history.

## Technologies Used

- Java 17
- Spring Boot
- Spring Data JPA
- H2 Database
- Swagger
- Lombok
- Postgres
- Docker
- Docker Compose
- Maven

## How to run the application

### Running the application locally using Maven

1. Clone the repository
2. Navigate to the project directory
3. Run the following command to start the application:

```bash 
mvn spring-boot:run
```

### Running the application using Docker

1. Clone the repository
2. Navigate to the project directory
3. Run the following command to build the Docker image:

```bash
docker-compose up -d
```

### API Documentation

The API documentation can be accessed at http://localhost:8080/wallet-app/swagger-ui/index.html
