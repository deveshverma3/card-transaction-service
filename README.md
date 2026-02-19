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
==========================

1️⃣ Clone the Repository
------------------------

git clone https://github.com//card-transactions-service.git

2️⃣ Navigate to the Project Folder
----------------------------------

cd card-transactions-service

3️⃣ Run the Application
-----------------------

### Option A — Using Run Script
* chmod +x run
* ./run

### ⚙️ What the run Script Does
The run script internally uses **Docker** and **Docker Compose** to:
*   🐳 Build the application image
*   🐳 Start the Spring Boot container
*   🔗 Configure networking between services
*   🚀 Expose the application on port 8081
* ⚠️ Prerequisite: Docker must be installed and running on your machine.

### Option B — Using gradle (Run Without Docker)
* ./gradlew clean bootRun

📖 API Documentation
--------------------
Swagger UI available at: http://localhost:8081/swagger-ui/index.html

🗄 Database Configuration
-------------------------

This application uses an in-memory **H2** database for development and testing purposes.

The database starts automatically when the application runs and requires no external setup.

🔎 H2 Console Access
--------------------

You can access the H2 web console to inspect data and execute SQL queries: http://localhost:8081/h2-console

### Connection Details

Use the following configuration to connect:

*   **JDBC URL:** jdbc:h2:mem:carddb

*   **Username:** sa

*   **Password:** _(leave empty unless configured otherwise)_

*   **Driver Class:** org.h2.Driver

