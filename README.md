

```markdown
# Auth Service

## Overview
The Auth Service is a secure authentication system that handles user registration, login, and token validation. It provides a robust framework for managing user credentials and ensures that users can access protected resources securely through token-based authentication. This service is designed to be integrated with other applications and is built using Spring Boot.

## Features
- **User Registration**: Allows new users to register with username,  email and password.
- **User Login**: Authenticates users and generates a secure token for access.
- **Token Validation**: Validates the provided JWT tokens to ensure secure access.
- **Email Verification**: Sends a welcome email to users upon successful registration.

## Technologies Used
- Java
- Spring Boot
- Spring Security
- Jakarta EE
- JWT (JSON Web Tokens)
- BCrypt for password encryption
- PostgreSQL (for database)

## Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/AnderDEVTrying/Auth-Service.git
   ```
2. Navigate to the project directory:
   ```bash
   cd auth_service
   ```
3. Set up your database (MySQL/PostgreSQL) and update the application properties with your database credentials.
4. Build the project:
   ```bash
   ./mvnw clean install
   ```
5. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```

## Usage
- Access the API at `http://localhost:8082/auth`.
- Use tools like Postman or CURL to interact with the endpoints:
  - **POST /auth/register**: Register a new user by providing email and password in JSON format.
  - **POST /auth/login**: Log in with registered email and password to receive a JWT token.
  - **GET /auth/validate**: Validate the provided JWT token in the request header.

## Example Requests

### Register
```http
POST /auth/register
Content-Type: application/json

{
  "username": "John Doe" ,
  "email": "user@example.com",
  "password": "yourPassword"
}
```

### Login
```http
POST /auth/login
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "yourPassword"
}
```

### Validate Token
```http
GET /auth/validate
Authorization: Bearer <your_token>
```

## Contribution
Contributions are welcome! Please open an issue or submit a pull request for any features or improvements.

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Contact
For inquiries, please reach out to azango94@gmail.com
```
