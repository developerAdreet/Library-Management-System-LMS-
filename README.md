Overview
A backend system built with Spring Boot to manage library operations. It provides REST APIs for user registration, book borrow/return workflows, and library management. The project demonstrates clean layered architecture, DTO validation, centralized exception handling, and API documentation with Swagger.

 Tech Stack
- Backend: Spring Boot, Hibernate/JPA
- Database: MySQL
- Utilities: Lombok, ModelMapper, Jakarta Validation
- API Documentation: Swagger/OpenAPI (springdoc)
- Testing: JUnit, Mockito, Postman
- Server: Embedded Tomcat 10.1
- Build Tool: Maven
  
  Features
- CRUD operations for User, Library, Book, and Address modules
- Borrow/return workflows with timestamp tracking
- DTO validation and ModelMapper for clean data transfer
- Centralized exception handling with standardized API responses
- Interactive API documentation via Swagger UI
- Tested endpoints using Postman
