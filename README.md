# Gallery Management System - Spring Boot Backend

## Features
- Java 17 Spring Boot backend
- JWT Authentication & Role-based security
- User, Admin, Gallery CRUD endpoints
- MySQL with JPA/Hibernate

---

## Prerequisites
- Java 17+
- Maven 3.6+
- MySQL 8+
- VS Code (recommended for editing)

## Setup Steps
1. **Clone/Unzip the project. Open in VS Code.**
2. **Install dependencies:**
   ```sh
   mvn clean install
   ```
3. **Create MySQL database**
   ```sql
   CREATE DATABASE gallery_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
   CREATE USER 'gallery_user'@'localhost' IDENTIFIED BY 'strong_password';
   GRANT ALL PRIVILEGES ON gallery_db.* TO 'gallery_user'@'localhost';
   FLUSH PRIVILEGES;
   ```
4. **Configure your DB settings:**
   Edit `src/main/resources/application.properties`:
   ```
   spring.datasource.url=jdbc:mysql://localhost:3306/gallery_db
   spring.datasource.username=gallery_user
   spring.datasource.password=strong_password
   jwt.secret=generate_a_random_secret_here
   ```
   Generate JWT secret (example):
   ```sh
   openssl rand -hex 32
   ```
5. **Run the App:**
   ```sh
   mvn spring-boot:run
   ```
   App will run on [http://localhost:8080](http://localhost:8080)

## How to Test APIs
- Import `launch.json` or use a REST client (like Postman/Thunder Client in VS Code)
- Sample endpoints:
  - `POST /api/auth/signup`
  - `POST /api/auth/login`
  - `GET /api/galleries` (USER/ADMIN)
  - `POST /api/galleries` (ADMIN)

Example signup/login:
```json
POST /api/auth/signup
{
  "username": "testuser",
  "email":"test@domain.com",
  "password": "123456"
}

POST /api/auth/login
{
  "username": "testuser",
  "password": "123456"
}
```
Response: `{ "token": "<jwt>" }`

Then use `Authorization: Bearer <jwt>` in your requests.

---

## Customization
- Change roles/user seeds in data.sql if needed
- Extend Gallery API as needed

---

**Enjoy!**
