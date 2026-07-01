# Account Management System

## Features

- User Registration
- Login with OTP
- JWT Authentication
- Refresh Token
- Spring Security
- PostgreSQL Database

## Technologies Used

 - Java
- Spring Boot
- Spring Security
- Maven
- PostgreSQL

## API Endpoints

### Register
POST /user-auth/register

### Login
POST /user-auth/login

### Verify OTP
POST /user-auth/verify/otp

### GET All users
GET /user-auth

### POST generate access token
POST /user_auth/refreshKey/{refreshKey}

### POST forgot password
POST /user-auth/forgot/password/{email}

### POST Verify Reset Otp
POST /user-auth/verify/reset/otp

### POST Change Password
POST /user-auth/change/password

### POST Signout
POST /user-auth/signout

### POST Add location
POST /user-auth/add/location

### PUT Update location
PUT /user-auth/update/location/id/{id}

### DELETE Location
DELETE /auth-user/delete/location/id/{id}

### Get All locations
GET /auth-user/location
### Swagger URL
http://localhost:8080/swagger-ui/index.html
