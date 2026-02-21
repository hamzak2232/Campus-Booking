# Campus Booking System

Spring Boot REST API to manage campus room bookings. Supports student registration, JWT login, role-based access, room management, and booking operations.

* * *

## Features

-   Student registration (ADMIN-only)

-   JWT-based authentication (`/api/auth/login`)

-   Role-based access control (ADMIN / STUDENT)

-   Room management (GET for ADMIN + STUDENT, write operations ADMIN-only)

-   Booking management (ADMIN + STUDENT)

-   HATEOAS links in responses (EntityModel / PagedModel)

-   Soft delete support

-   Global exception handling


* * *

## Tech Stack

-   Java 17

-   Spring Boot

-   Spring Security + JWT

-   Spring Data JPA (PostgreSQL)

-   Spring HATEOAS

-   Lombok

-   Maven


* * *

## Requirements

-   Java 17+

-   Maven

-   PostgreSQL 15+

-   IntelliJ / Eclipse (recommended)


* * *

## Setup Instructions

### 1\. Clone the Repository

git clone https://github.com/hamzak2232/Campus-Booking  
cd Campus-Booking

* * *

### 2\. Configure PostgreSQL

Create the database:

CREATE DATABASE campus\_booking;

* * *

### 3\. Configure Environment Variables (Required)

The application seeds an ADMIN and a STUDENT on startup (except when running with the `test` profile).

You must define:

-   `ADMIN_SEED_PASSWORD`

-   `STUDENT_SEED_PASSWORD`


#### Linux / macOS

export ADMIN\_SEED\_PASSWORD\=admin123456  
export STUDENT\_SEED\_PASSWORD\=pass123456

#### Windows (PowerShell)

$env:ADMIN\_SEED\_PASSWORD\="admin123456"  
$env:STUDENT\_SEED\_PASSWORD\="pass123456"

* * *

### 4\. Configure Database Connection

Edit:

src/main/resources/application.properties

Example:

spring.datasource.url=jdbc:postgresql://localhost:5432/campus\_booking  
spring.datasource.username=postgres  
spring.datasource.password=postgres  
spring.jpa.hibernate.ddl-auto=update

* * *

### 5\. Build and Run

mvn clean install  
mvn spring-boot:run

Application runs at:

http://localhost:8080

* * *

## Seeded Users

When the application starts (non-test profile), the following users are created if they do not already exist:

### ADMIN

-   studentId: `admin`

-   email: `admin@campus.com`

-   password: value of `ADMIN_SEED_PASSWORD`


### STUDENT

-   studentId: `s1`

-   email: `hamza@campus.com`

-   password: value of `STUDENT_SEED_PASSWORD`


* * *

## Authentication (JWT)

### Login

**POST** `/api/auth/login`

Request:

{  
"studentId": "s1",  
"password": "pass123456"  
}

Response:

<JWT token string>

Use token in all protected endpoints:

Authorization: Bearer <token>

* * *

## Authorization Rules (Summary)

### Actuator

-   `/actuator/**` → ADMIN only


### Students

-   `/api/students/**` → ADMIN only


### Rooms

-   GET `/api/rooms/**` → ADMIN or STUDENT

-   POST `/api/rooms/**` → ADMIN only

-   PUT `/api/rooms/**` → ADMIN only

-   DELETE `/api/rooms/**` → ADMIN only


### Bookings

-   `/api/bookings/**` → ADMIN or STUDENT


* * *

## API Endpoints

### Auth

| Method | Endpoint | Description |
| --- | --- | --- |
| POST | `/api/auth/login` | Login and receive JWT |

* * *

### Students (ADMIN only)

> Student lookup uses **studentId** (String) as path parameter.

| Method | Endpoint | Description |
| --- | --- | --- |
| GET | `/api/students` | List all students (paged) |
| GET | `/api/students/{studentId}` | Get student by studentId |
| POST | `/api/students` | Create/register student |

* * *

### Rooms

| Method | Endpoint | Description |
| --- | --- | --- |
| GET | `/api/rooms` | List rooms (paged) |
| GET | `/api/rooms/{id}` | Get room by DB id (Long) |
| POST | `/api/rooms` | Create room (ADMIN only) |

* * *

### Bookings

| Method | Endpoint | Description |
| --- | --- | --- |
| GET | `/api/bookings` | List bookings (paged) |
| GET | `/api/bookings/{id}` | Get booking by DB id (Long) |
| POST | `/api/bookings` | Create booking |

* * *

## Error Handling

Handled globally via `GlobalExceptionHandler`.

| Status | Meaning |
| --- | --- |
| 400 | Validation errors / malformed JSON |
| 401 | Invalid credentials |
| 403 | Access denied |
| 404 | Resource not found |
| 409 | Conflict (duplicate booking, etc.) |
| 500 | Unexpected server error |

* * *

## Value Objects

Student entity uses:

-   `Email` (validated and normalized)

-   `PasswordHash` (expects bcrypt-hashed password)


* * *

## Running Tests

Tests are located in:

src/test/java/com/campus/booking/

Run:

mvn test

* * *

## Development Notes

-   Entity primary keys use `Long`

-   Soft delete implemented using `@SQLDelete` and `@Where`

-   HATEOAS used for hypermedia responses

-   `api.http` file included for manual testing (IntelliJ HTTP Client)