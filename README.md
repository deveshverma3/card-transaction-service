Card Transactions Service
=========================

A Spring Boot REST API for managing cardholder accounts and financial transactions.

The service applies **automatic amount normalization** based on the operation type, ensuring business rules are consistently enforced at the application layer.

🚀 Features
-----------

*   ✅ Create and retrieve cardholder accounts

*   ✅ Register financial transactions

*   ✅ Automatic transaction amount normalization

*   ✅ Swagger/OpenAPI documentation

*   ✅ Dockerized environment

*   ✅ Integration tests 


🛠 Tech Stack
-------------

*   **Java 22**

*   **Spring Boot 3**

*   **Spring Data JPA (Hibernate)**

*   **PostgreSQL**

*   **H2 (Dev profile)**

*   **Docker & Docker Compose**

*   **MapStruct**

*   **Lombok**

*   **JUnit 5**

▶️ Running the Application
--------------------------

Using Run Script

*  chmod +x run
*  ./run

Application will be available at: http://localhost:8081

H2 Console: http://localhost:8081/h2-console

JDBC URL: jdbc:h2:mem:carddb

📖 API Documentation
--------------------

Swagger UI available at: http://localhost:8081/swagger-ui/index.html
