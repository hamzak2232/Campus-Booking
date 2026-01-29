# Campus Booking System
A Spring Boot application to manage campus room bookings. Supports student registration, room management, and booking operations.

---

## Features
- Student registration, login, and role-based access
- Room management (CRUD for rooms)
- Booking management (students can book rooms)
- Admin management of students and rooms
- RESTful API endpoints for integration/testing

---

## Requirements
- Java 17+
- Maven
- PostgreSQL 15+
- IDE (IntelliJ/Eclipse recommended)

---

## Setup Instructions

### 1. Clone the repository
```bash
git clone <https://github.com/hamzak2232/Campus-Booking>
cd campus-booking
```
### 2. Configure PostgreSQL
- Install PostgreSQL and create a database named `campus_booking`.
```bashsql
CREATE DATABASE campus_booking;
```
- Update src/main/resources/application.properties:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/campus_booking
spring.datasource.username=<your_db_user>
spring.datasource.password=<your_db_password>
spring.jpa.hibernate.ddl-auto=update
```
### 3. Build and Run the Application
```bash
mvn clean install
mvn spring-boot:run
```
The application will start on http://localhost:8080.

---

## API Endpoints
### 1. Student Endpoints
| Method | Endpoint             | Description          |
| ------ | -------------------- | -------------------- |
| GET    | `/api/students`      | List all students    |
| GET    | `/api/students/{id}` | Get a student by ID  |
| POST   | `/api/students`      | Create a new student |

### 2. Room Endpoints
| Method | Endpoint          | Description       |
| ------ | ----------------- | ----------------- |
| GET    | `/api/rooms`      | List all rooms    |
| GET    | `/api/rooms/{id}` | Get room by ID    |
| POST   | `/api/rooms`      | Create a new room |
### 3. Booking Endpoints
| Method | Endpoint        | Description          |
| ------ | --------------- | -------------------- |
| GET    | `/api/bookings` | List all bookings    |
| POST   | `/api/bookings` | Create a new booking |

---

## Running Unit Tests
- Unit tests are located in src/test/java/com/campus/booking/. Run them using:
```bash
mvn test
```

---

- api.http file also included