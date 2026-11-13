# Student Management System

A RESTful Student Management System developed using **Java 21, Spring Boot, Spring Data JPA, MySQL, and Spring Security with JWT authentication**.

The application manages students, departments, courses, and student-course enrollments. It provides secure REST APIs with role-based authorization, input validation, pagination, sorting, and centralized exception handling.

## Technologies Used

- Java 21
- Spring Boot 4.1.1
- Spring Web MVC
- Spring Data JPA
- Spring Security
- JWT Authentication (JJWT)
- MySQL
- Maven
- JUnit 5
- Mockito
- JaCoCo
- Eclipse IDE
- Postman

## Key Features

### 1. Student Management
- Create, view, update, and delete student records.
- Search students by name.
- Pagination and sorting.
- Validate student names, email addresses, and phone numbers.
- Retrieve student counts by department and course.

### 2. Department Management
- Create, view, update, and delete departments.
- Associate students with departments.

### 3. Course Management
- Create, view, update, and delete courses.
- View students enrolled in a course.
- Manage student-course enrollments.
- Support many-to-many student-course relationships.

### 4. Authentication and Security
- User registration and login.
- JWT-based authentication.
- BCrypt password hashing.
- ADMIN and USER role-based authorization.
- Protected REST endpoints.
- Public registration assigns the USER role by default.

### 5. Validation and Exception Handling
- Input validation using Jakarta Bean Validation.
- Custom not-found exceptions.
- Meaningful HTTP error responses.
- Pagination and sorting validation.

### 6. Dashboard
- View student, department, and course statistics.
- Identify the most popular course.

## Project Structure

```text
studentmanagement/
│
├── src/
│   ├── main/
│   │   ├── java/com/student/studentmanagement/
│   │   │   ├── config/
│   │   │   ├── controller/
│   │   │   ├── dto/
│   │   │   ├── exception/
│   │   │   ├── model/
│   │   │   ├── repository/
│   │   │   ├── security/
│   │   │   ├── service/
│   │   │   └── StudentmanagementApplication.java
│   │   │
│   │   └── resources/
│   │       └── application.properties
│   │
│   └── test/
│       └── java/com/student/studentmanagement/
│
├── .gitignore
├── mvnw
├── mvnw.cmd
├── pom.xml
└── README.md
```

## Database Configuration

The application uses MySQL.

Create the database:

```sql
CREATE DATABASE student_db;
```

Configure the following environment variables:

| Variable | Description |
|---|---|
| DB_USERNAME | MySQL username |
| DB_PASSWORD | MySQL password |
| JWT_SECRET | Base64-encoded JWT signing secret |

The JWT signing secret must contain at least 32 random bytes before Base64 encoding.

**Important:** Never commit actual database credentials or JWT secrets to GitHub.

## Running the Application

### Prerequisites
- Java 21
- MySQL
- Maven (or the included Maven Wrapper)
- Eclipse IDE or another Java IDE

### Setup Instructions

1. Clone the GitHub repository.
2. Open the project in Eclipse or another Java IDE.
3. Configure the MySQL database.
4. Set the required environment variables.
5. Update Maven dependencies.
6. Run the Spring Boot application.

Alternatively, on Windows, use:

```bash
mvnw.cmd spring-boot:run
```

The application runs at:

```text
http://localhost:8081
```

## API Overview

Base URL:

```text
http://localhost:8081
```

| Module         | Endpoint             | Operations |
|---|---|---|
| Authentication | `/auth/register`  | POST |
| Authentication | `/auth/login`      | POST |
| Students       | `/students`        | GET, POST |
| Departments    | `/departments`    | GET, POST |
| Courses        | `/courses`         | GET, POST |

Additional endpoints support individual record retrieval, updating, deletion, searching, pagination, sorting, and student-course relationships.

## Authorization Rules

### Public Access
- User registration.
- User login.

### USER Role
- Read student, department, and course information.
- Cannot create, update, or delete records.

### ADMIN Role
- Create, update, and delete students.
- Create, update, and delete departments.
- Create, update, and delete courses.
- Manage student-course enrollments.

All protected endpoints require a valid JWT Bearer token.

## Testing and Code Coverage

The project includes automated unit tests using **JUnit 5 and Mockito**.

Testing covers:
- Service layer business logic.
- Controller layer functionality.
- Repository layer operations.
- Authentication and JWT services.
- Validation and exception scenarios.
- Dashboard functionality.

### Test Results

| Metric | Result |
|---|---:|
| Total tests executed | 108 |
| Overall JaCoCo instruction coverage | 81% |
| Maven verification | BUILD SUCCESS |

### Running Tests

Execute the following command:

```bash
mvnw.cmd clean verify
```

JaCoCo generates an HTML coverage report at:

```text
target/site/jacoco/index.html
```

## API Testing

REST APIs were manually tested using Postman.

Testing included:
- User registration and login.
- JWT authentication.
- ADMIN and USER permissions.
- CRUD operations.
- Student search.
- Pagination and sorting.
- Input validation.
- Student-course relationships.
- Error handling and HTTP status codes.

## Future Improvements

- Develop a frontend using React or Angular.
- Add integration tests for complete application workflows.
- Implement API documentation using Swagger/OpenAPI.
- Introduce database migrations.
- Deploy the application to a cloud platform.

## Author

Developed as a Java Full Stack learning and portfolio project.
