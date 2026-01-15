# Samya IRCTC Backend

A Java-based backend application that simulates core functionalities of an IRCTC-like railway ticket booking system.

## Tech Stack
- Java
- Java Servlets
- Gradle
- Apache Tomcat
- JWT Authentication

## Features
- User registration and login
- JWT-based authentication and authorization
- Train listing
- Ticket booking
- View booking history
- Health check API

## Project Structure
src/
 └── main/
     ├── java/
     │   └── com/samya/irctc/
     │       ├── controller/
     │       ├── service/
     │       ├── repository/
     │       ├── model/
     │       ├── filter/
     │       ├── util/
     │       └── exception/
     ├── resources/
     │   └── application.properties
     └── webapp/
         └── WEB-INF/
             └── web.xml

## How to Run Locally
1. Clone the repository
2. Open the project in IntelliJ IDEA or Eclipse
3. Build the project using Gradle
4. Deploy the generated WAR file on Apache Tomcat
5. Access APIs using browser or Postman

## API Example
Health Check:
GET /health

## Deployment
This backend is configured and ready for deployment on Render.

## Author
Samya Sarkar
