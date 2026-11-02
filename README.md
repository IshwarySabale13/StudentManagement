# Student Management System

A RESTful Student Management System built using Java, Spring Boot, Spring Data JPA, MySQL, and Spring Security with JWT authentication.

The application manages students, departments, courses, and student-course enrollments. It provides secure REST APIs with role-based authorization, input validation, pagination, sorting, and error handling.

## Technologies Used

- Java 21
- Spring Boot 4.1.1
- Spring Web MVC
- Spring Data JPA
- Spring Security
- JWT Authentication (JJWT)
- MySQL
- Maven
- Eclipse IDE
- Postman

## Key Features

### Student Management
- Create, view, update, and delete student records.
- Search students by name.
- Pagination and sorting.
- Validate student names, email addresses, and phone numbers.
- Retrieve student counts by department and course.

### Department Management
- Create, view, update, and delete departments.
- Associate students with departments.

### Course Management
- Create, view, update, and delete courses.
- View students enrolled in a course.
- Manage student-course enrollments.

### Authentication and Security
- User registration and login.
- JWT-based authentication.
- BCrypt password hashing.
- ADMIN and USER role-based authorization.
- Protected REST endpoints.
- Public registration assigns the USER role by default.

### Validation and Error Handling
- Input validation using Jakarta Bean Validation.
- Custom not-found exceptions.
- Meaningful HTTP error responses.
- Pagination and sorting validation.

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
│   │   │   ├── model/
│   │   │   ├── repository/
│   │   │   ├── security/
│   │   │   └── service/
│   │   │
│   │   └── resources/
│   │       └── application.properties
│   │
│   └── test/
│
├── .gitignore
├── mvnw
├── mvnw.cmd
└── pom.xml
```

## Database Configuration

The project uses MySQL.

Create the database:

```sql
CREATE DATABASE student_db;
```

Configure the following environment variables before starting the application:

| Variable | Description |
|---|---|
| DB_USERNAME | MySQL username |
| DB_PASSWORD | MySQL password |
| JWT_SECRET | Base64-encoded JWT signing secret |

The JWT secret should contain at least 32 random bytes before Base64 encoding. Keep all credentials private and never commit actual secret values.

## Running the Application

1. Clone or download this repository.
2. Open the project in Eclipse or another Java IDE.
3. Ensure Java 21 and Maven are installed.
4. Create the MySQL database.
5. Configure the required environment variables.
6. Update dependencies using Maven.
7. Run the Spring Boot application.

Alternatively, on Windows, run:

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

| Module | Endpoint | Operations |
|---|---|---|
| Authentication | `/auth/register` | POST |
| Authentication | `/auth/login` | POST |
| Students | `/students` | GET, POST |
| Departments | `/departments` | GET, POST |
| Courses | `/courses` | GET, POST |

## Authorization Rules

**Public endpoints:**
- User registration
- User login

**USER role:**
- Read student, department, and course information.
- Cannot create, update, or delete records.

**ADMIN role:**
- Create, update, and delete students.
- Create, update, and delete departments.
- Create, update, and delete courses.
- Manage student-course enrollments.

All protected endpoints require a valid JWT bearer token.

## API Testing

The REST APIs were manually tested using Postman.

Testing covered:
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

- Build a frontend using React or Angular.
- Add automated unit and integration tests.
- Add API documentation using Swagger/OpenAPI.
- Implement database migrations.
- Deploy the application to a cloud platform.

## Author

Developed as a Java Full Stack learning and portfolio project.

