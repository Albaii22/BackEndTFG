# Backend Application

This project is a Spring Boot application that serves as the backend for a web application. It includes configurations for security, JWT authentication, Swagger documentation, and various services for managing users, comments, publications, and admins.

## Table of Contents

1. [Getting Started](#getting-started)
2. [Project Structure](#project-structure)
3. [Configuration](#configuration)
4. [Endpoints](#endpoints)
5. [DTOs and Entities](#dtos-and-entities)
6. [Services and Repositories](#services-and-repositories)
7. [Security](#security)

## Getting Started

### Prerequisites

- Java 11 or higher
- Maven
- A database (e.g., MySQL, PostgreSQL)

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/your-repository/backend.git
   cd backend
   
2. Update the application properties to match your database configuration.

# Project Structure

The project is structured into several packages, each serving a specific purpose:

- **configuration**: Contains classes for configuring security and application settings.
- **controllers**: Contains REST controllers for handling HTTP requests.
- **DTO**: Contains Data Transfer Objects.
- **entities**: Contains JPA entity classes.
- **jwt**: Contains classes related to JWT authentication.
- **other**: Contains utility classes and generic response objects.
- **repository**: Contains JPA repository interfaces.
- **service**: Contains service interfaces.
- **serviceIMP**: Contains service implementations.

# Configuration

### `ApplicationConfig`

Configures authentication and services needed for the application.

### `SecurityConfig`

Configures security settings, including CORS and resource handling.

### `SwaggerConfig`

Configures Swagger for API documentation.

# Endpoints

### `AdminController`

- `POST /api/admins`: Creates a new admin.
- `GET /api/admins`: Retrieves all admins.
- `GET /api/admins/{id}`: Retrieves an admin by ID.
- `PUT /api/admins/{id}`: Updates an admin.
- `DELETE /api/admins/{id}`: Deletes an admin.

### `AuthController`

- `POST /auth/login`: Logs in a user.
- `POST /auth/register`: Registers a new user.

### `CommentsController`

- `POST /api/comments/{userId}`: Creates a new comment.
- `GET /api/comments`: Retrieves all comments.
- `GET /api/comments/{id}`: Retrieves a comment by ID.
- `PUT /api/comments/{id}`: Updates a comment.
- `DELETE /api/comments/{id}`: Deletes a comment.

### `PublicationsController`

- `POST /api/publications/{userId}`: Creates a new publication.
- `GET /api/publications`: Retrieves all publications.
- `GET /api/publications/{id}`: Retrieves a publication by ID.
- `PUT /api/publications/{id}`: Updates a publication.
- `DELETE /api/publications/{id}`: Deletes a publication.
- `GET /api/publications/user/{userId}`: Retrieves publications by user ID.
- `POST /api/publications/{publicationId}/toggle-like`: Toggles like on a publication.

### `UserController`

- `GET /api/users`: Retrieves all users.
- `GET /api/users/{id}`: Retrieves a user by ID.
- `POST /api/users`: Creates a new user.
- `PUT /api/users/{id}`: Updates a user.
- `PUT /api/users/{id}/aboutMe`: Updates the aboutMe field of a user.
- `DELETE /api/users/{id}`: Deletes a user.
- `GET /api/users/username/{username}/id`: Retrieves user ID by username.
- `POST /api/users/{id}/uploadProfileImage`: Uploads a profile image.

# DTOs and Entities

### DTOs

- **AdminDTO**: Data Transfer Object for admin data.
- **CommentsDTO**: Data Transfer Object for comment data.
- **PublicationsDTO**: Data Transfer Object for publication data.
- **UserDTO**: Data Transfer Object for user data.

### Entities

- **Admin**: Entity representing an admin.
- **Comments**: Entity representing a comment.
- **Publications**: Entity representing a publication.
- **User**: Entity representing a user.
- **Role**: Enum representing user roles (ADMIN, USER).

# Services and Repositories

### Services

- **AdminService**: Interface for managing admins.
- **CommentsService**: Interface for managing comments.
- **PublicationsService**: Interface for managing publications.
- **UserService**: Interface for managing users.

### Service Implementations

- **AdminServiceIMP**: Implementation of AdminService.
- **CommentsServiceIMP**: Implementation of CommentsService.
- **PublicationServiceIMP**: Implementation of PublicationsService.
- **UserServiceIMP**: Implementation of UserService.

### Repositories

- **AdminRepository**: JPA repository for Admin.
- **CommentsRepository**: JPA repository for Comments.
- **PublicationsRepository**: JPA repository for Publications.
- **UserRepository**: JPA repository for User.

# Security

### JWT

- **JwtService**: Handles JWT operations, such as generating and validating tokens.
- **JwtAuthenticationFilter**: Filter for authenticating requests using JWT.

### Authentication and Authorization

- Uses Spring Security for managing authentication and authorization.
- Custom authentication provider and user details service are configured to handle user credentials and roles.

# Running the Application

To start the application, execute the following command:

```bash
mvn spring-boot:run
```

The application will be available at http://localhost:8082.

Swagger Documentation
Swagger UI is available at http://localhost:8082/swagger-ui.html for API documentation and testing.


