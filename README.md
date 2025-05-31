# REST API Spring Boot One To Many example with Hibernate and PostgreSQL  

Implement JPA/Hibernate **One-To-Many** mapping with **Hibernate** in a Spring Boot CRUD example using `@OneToMany` annotation.  

## 📖 Overview  

This project is a REST API built using **Spring Boot** to handle CRUD operations for **Employee** and **Department** entities. The project integrates **Spring Data JPA with Hibernate** as the default JPA provider and uses **PostgreSQL** as the database. It also includes a custom HTTP response and a **custom error controller** to override the default `/error` response.  

This project implements `One-To-Many` relationships between **Employee** as the parent entity and the child entities **DepartmentEmployee, SalaryEmployee, and TitleEmployee**. The relationship is managed using Spring Data JPA with Hibernate, and the project utilizes `EmbeddedId` for `composite primary keys` in the relationship tables.  

### 🔗 Relationships  

The following is the relationship between tables:  

- Department ↔ DepartmentEmployee (`One-to-Many`)
- Employee ↔ DepartmentEmployee (`One-to-Many`)
- Employee ↔ SalaryEmployee (`One-to-Many`)
- Employee ↔ TitleEmployee (`One-to-Many`)

### 🔢 EmbeddedId  

These tables are managed using `EmbeddedId` to define composite primary keys:  

- department_employee (employee_id, department_id)
- salary (employee_id, from_date)
- title (employee_id, title, from_date)

---

## 🤖 Tech Stack  

The technology used in this project are:  

| Dependency                      | Description                                                                 |
|---------------------------------|-----------------------------------------------------------------------------|
| **Spring Boot Starter Web**     | Building RESTful APIs or web applications                                   |
| **PostgreSQL**                  | Serves as the relational database for storing Employee and Department       |
| **Hibernate**                   | Simplifies database interactions via JPA                                    |
| **Lombok**                      | Reduces boilerplate code (e.g., getters, setters, constructors)             |


---

## 🧱 Architecture Overview  

The project is organized into the following package structure:  

```bash
📁 one-to-many-postgresql/
└── 📂src/
    └── 📂main/
        ├── 📂docker/
        │   ├── 📂app/                     # Dockerfile for Spring Boot application (runtime container)
        │   │   └── Dockerfile             # Uses base image, copies JAR/dependencies, defines ENTRYPOINT
        │   └── 📂postgres/                # Custom PostgreSQL Docker image (optional)
        │       ├── Dockerfile             # Extends from postgres:17, useful for init customization
        │       └── init.sql               # SQL script to create database, user, and grant permissions
        ├── 📂java/
        │   ├── 📂config/                  # Spring configuration classes
        │   │   └── 📂serializer/          # Custom Jackson serializers/deserializers (e.g., for `Instant`)
        │   ├── 📂controller/              # REST API endpoints (e.g., EmployeeController, DepartmentController, CustomErrorController)
        │   ├── 📂dto/                     # Data Transfer Objects for requests/responses
        │   ├── 📂entity/                  # JPA entity classes mapped to database tables
        │   ├── 📂mapper/                  # MapStruct or manual mappers between DTO and entity
        │   ├── 📂repository/              # Spring Data JPA interfaces for database access
        │   ├── 📂service/                 # Business logic layer
        │   │   └── 📂impl/                # Service implementation classes
        │   └── 📂util/                    # Utility/helper classes (e.g., response builder util)
        └── 📂resources/
            ├── application.properties     # Application configuration (DB, profiles, etc.)
            └── import.sql                 # SQL file for seeding database on startup
```

This clean separation allows the application to **scale well**, supports **test-driven development**, and adheres to best practices in **enterprise application design**.  

---


## 🛠️ Installation & Setup  


Follow these steps to set up and run the project locally:  

### ✅ Prerequisites

Make sure the following tools are installed on your system:

| Tool                                      | Description                                                                 | Required      |
|-------------------------------------------|-----------------------------------------------------------------------------|---------------|
| [Java 17+](https://adoptium.net/)         | Java Development Kit (JDK) to run the Spring application                    | ✅            |
| [PostgreSQL](https://www.postgresql.org/) | Relational database to persist application data                             | ✅            |
| [Make](https://www.gnu.org/software/make/)| Automation tool for tasks like `make run-app`                               | ✅            |
| [Docker](https://www.docker.com/)         | To run services like PostgreSQL in isolated containers                      | ⚠️ *optional* |

### ☕ 1. Install Java 17  

1. Ensure **Java 17** is installed on your system. You can verify this with:  

```bash
java --version
```  

2. If Java is not installed, follow one of the methods below based on your operating system:  

#### 🐧 Linux  

**Using apt (Ubuntu/Debian-based)**:  

```bash
sudo apt update
sudo apt install openjdk-17-jdk
```  

#### 🪟 Windows  
1. Use [https://adoptium.net](https://adoptium.net) to download and install **Java 17 (Temurin distribution recommended)**.  

2. After installation, ensure `JAVA_HOME` is set correctly and added to the `PATH`.  

3. You can check this with:  

```bash
echo $JAVA_HOME
```  

### 🐘 2. Install PostgreSQL  
1. Install PostgreSQL if it’s not already available on your machine:  
    - Use [https://www.postgresql.org/download/](https://www.postgresql.org/download/) to download PostgreSQL.  

2. Once installed, create the following databases:  
```sql
CREATE DATABASE employees;  
```  

These databases are used for development and automated testing, respectively.  

### 🧰 3. Install `make` (Optional but Recommended)  
This project uses a `Makefile` to streamline common tasks.  

Install `make` if not already available:  

#### 🐧 Linux  

Install `make` using **APT**  

```bash
sudo apt update
sudo apt install make
```  

You can verify installation with:   
```bash
make --version
```  

#### 🪟 Windows  

If you're using **PowerShell**:  

- Install [Chocolatey](https://chocolatey.org/install) (if not installed):  
```bash
Set-ExecutionPolicy Bypass -Scope Process -Force; [System.Net.ServicePointManager]::SecurityProtocol = [System.Net.SecurityProtocolType]::Tls12; iex ((New-Object System.Net.WebClient).DownloadString('https://community.chocolatey.org/install.ps1'))
```  

- Verify `Chocolatey` installation:  
```bash
choco --version
```  

- Install `make` via `Chocolatey`:  
```bash
choco install make
```  

After installation, **restart your terminal** or ensure `make` is available in your `PATH`.  

### 🔁 4. Clone the Project  

Clone the repository:  

```bash
git clone https://github.com/yoanesber/Spring-Boot-Hibernate-One-To-Many-PostgreSQL.git
cd Spring-Boot-Hibernate-One-To-Many-PostgreSQL
```  

### ⚙️ 5. Configure Application Properties  

Set up your `application.properties` in `src/main/resources`:  

```properties
# application configuration
spring.application.name=one-to-many-postgresql
server.port=8080
spring.profiles.active=development

## datasource configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/employees
spring.datasource.username=appuser
spring.datasource.password=app@123
spring.datasource.driver-class-name=org.postgresql.Driver
spring.sql.init.mode=always

## hibernate configuration
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.hibernate.naming.implicit-strategy=org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.open-in-view=true
```

- **🔐 Notes**:  Ensure that:  
  - Database URLs, username, and password are correct.  
  - `spring.datasource.username=appuser`, `spring.datasource.password=app@123`: It's strongly recommended to create a dedicated database user instead of using the default postgres superuser.


### 👤 6. Create Dedicated PostgreSQL User (Recommended)

For security reasons, it's recommended to avoid using the default postgres superuser. Use the following SQL script to create a dedicated user (`appuser`) and assign permissions:

```sql
-- Create appuser and database
CREATE USER appuser WITH PASSWORD 'app@123';

-- Allow user to connect to database
GRANT CONNECT ON DATABASE employees TO appuser;

-- Grant permissions on public schema
GRANT USAGE, CREATE ON SCHEMA public TO appuser;

-- Grant all permissions on existing tables
GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA public TO appuser;

-- Grant all permissions on sequences (if using SERIAL/BIGSERIAL ids)
GRANT USAGE, SELECT, UPDATE ON ALL SEQUENCES IN SCHEMA public TO appuser;

-- Ensure future tables/sequences will be accessible too
ALTER DEFAULT PRIVILEGES IN SCHEMA public
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO appuser;

ALTER DEFAULT PRIVILEGES IN SCHEMA public
GRANT USAGE, SELECT, UPDATE ON SEQUENCES TO appuser;
```

Update your `application.properties` accordingly:
```properties
spring.datasource.username=appuser
spring.datasource.password=app@123
```


## 🚀 7. Running the Application  

This section provides step-by-step instructions to run the application either **locally** or via **Docker containers**.

- **Notes**:  
  - All commands are defined in the `Makefile`.
  - To run using `make`, ensure that `make` is installed on your system.
  - To run the application in containers, make sure `Docker` is installed and running.


### 🔧 Run Locally (Non-containerized)

Ensure PostgreSQL are running locally, then:

```bash
make dev
```

### 🐳 Run Using Docker

To build and run all services (PostgreSQL, Spring app):

```bash
make docker-start-all
```

To stop and remove all containers:

```bash
make docker-stop-all
```

- **Notes**:  
  - Before running the application inside Docker, make sure to update your `application.properties`
    - Replace `localhost` with the appropriate **container name** for services like PostgreSQL.  
    - For example:
      - Change `localhost:5432` to `one-to-many-postgres:5432`

### 🟢 Application is Running

Now your application is accessible at:
```bash
http://localhost:8080
```

---

## 🧪 Testing Scenarios  

The REST API provides a comprehensive set of endpoints to manage departments, employees, and their related entities such as department assignments, salary histories, and job titles. These endpoints support full CRUD (Create, Read, Update, Delete) operations and follow RESTful design principles, exchanging data in JSON format.  

### 🏢 Department API  

#### 1. Create new departments with valid data  

**Endpoint:**  

```bash
POST http://localhost:8080/api/v1/departments
Content-Type: application/json
```  

**Request Body:**  

```json
{
    "id": "d011",
    "deptName": "Security",
    "active": true,
    "createdBy": 1
}
```

**Successful Response:**  

```json
{
    "message": "Record created successfully",
    "error": null,
    "path": "/api/v1/departments",
    "status": 201,
    "data": {
        "id": "d011",
        "deptName": "Security",
        "active": true,
        "createdBy": 1,
        "updatedBy": null
    },
    "timestamp": "2025-05-30T15:36:37.362899700Z"
}
```

#### 2. Create new departments with existing data   

**Endpoint:**  

```bash
POST http://localhost:8080/api/v1/departments
Content-Type: application/json
```  

**Request Body:**  

```json
{
    "id": "d011",
    "deptName": "Security",
    "active": true,
    "createdBy": 2
}
```

**Successful Response:**  

```json
{
    "message": "Record created successfully",
    "error": null,
    "path": "/api/v1/departments",
    "status": 201,
    "data": {
        "id": "d011",
        "deptName": "Security",
        "active": true,
        "createdBy": 1,
        "updatedBy": 2
    },
    "timestamp": "2025-05-30T15:37:24.823772800Z"
}
```

**📝 Note**:
Although this request uses the `POST` method, the system internally handles cases where the provided department `id` already exists in the database:
- The system will fetch the existing department record with the given ID.
- It will then update the existing record's details using the values provided in the JSON request (`deptName`, `active`, etc.).
- The `updatedBy` field will be set using the value of `createdBy` from the request.
- If the ID **does not exist**, a new record will be created as usual.

This approach provides a form of idempotent upsert behavior, where a POST request can act as either a create or update depending on the presence of the record.

#### 3. Retrieve all or specific departments by ID  

**Endpoint:**  

```bash
GET http://localhost:8080/api/v1/departments/d011
```  

**Successful Response:**  

```json
{
    "message": "Record retrieved successfully",
    "error": null,
    "path": "/api/v1/departments/d011",
    "status": 200,
    "data": {
        "id": "d011",
        "deptName": "Security",
        "active": true,
        "createdBy": 1,
        "updatedBy": 2
    },
    "timestamp": "2025-05-30T15:43:00.079103Z"
}
```

#### 4. Update department details  

**Endpoint:**  

```bash
PUT http://localhost:8080/api/v1/departments/d011
Content-Type: application/json
```  

**Request Body:**  

```json
{
    "id": "d011",
    "deptName": "Operation",
    "active": false,
    "updatedBy": 2
}
```

**Successful Response:**  

```json
{
    "message": "Record updated successfully",
    "error": null,
    "path": "/api/v1/departments/d011",
    "status": 200,
    "data": {
        "id": "d011",
        "deptName": "Operation",
        "active": false,
        "createdBy": 1,
        "updatedBy": 2
    },
    "timestamp": "2025-05-30T15:43:12.119589700Z"
}
```

#### 5. Delete departments  

**Endpoint:**  

```bash
DELETE http://localhost:8080/api/v1/departments/d011
```  

**Successful Response:**  

```json
{
    "message": "Record deleted successfully",
    "error": null,
    "path": "/api/v1/departments/d011",
    "status": 200,
    "data": null,
    "timestamp": "2025-05-30T15:43:51.426958100Z"
}
```


### 👨‍💼👩‍💼 Employee API  

#### 1. Create a new employee with initial department, salary, and title  

**Endpoint:**  

```bash
POST http://localhost:8080/api/v1/employees
Content-Type: application/json
```  

**Request Body:**  

```json
{
    "birthDate": "1990-08-01",
    "firstName": "Michael",
    "lastName": "jordan",
    "gender": "M",
    "hireDate": "2000-01-01",
    "active": true,
    "createdBy": 1,
    "departments": [
        {
            "departmentId": "d002",
            "fromDate": "2000-01-01",
            "toDate": "2005-12-31"
        }
    ],
    "salaries": [
        {
            "fromDate": "2000-01-01",
            "amount": 60116,
            "toDate": "2005-12-31"
        }
    ],
    "titles": [
        {
            "title": "Senior Engineer",
            "fromDate": "2000-01-01",
            "toDate": "2005-12-31"
        }
    ]
}
```

**Successful Response:**  

```json
{
    "message": "Record created successfully",
    "error": null,
    "path": "/api/v1/employees",
    "status": 201,
    "data": {
        "id": 11,
        "birthDate": "1990-08-01",
        "firstName": "Michael",
        "lastName": "jordan",
        "gender": "M",
        "hireDate": "2000-01-01",
        "active": true,
        "createdBy": 1,
        "updatedBy": null,
        "departments": [
            {
                "departmentId": "d002",
                "fromDate": "2000-01-01",
                "toDate": "2005-12-31"
            }
        ],
        "salaries": [
            {
                "fromDate": "2000-01-01",
                "amount": 60116,
                "toDate": "2005-12-31"
            }
        ],
        "titles": [
            {
                "title": "Senior Engineer",
                "fromDate": "2000-01-01",
                "toDate": "2005-12-31"
            }
        ]
    },
    "timestamp": "2025-05-30T15:45:55.870738100Z"
}
```

#### 2. Retrieve an employee by ID and check embedded child collections  

**Endpoint:**  

```bash
GET http://localhost:8080/api/v1/employees/11
```  

**Successful Response:**  

```json
{
    "message": "Record retrieved successfully",
    "error": null,
    "path": "/api/v1/employees/11",
    "status": 200,
    "data": {
        "id": 11,
        "birthDate": "1990-08-01",
        "firstName": "Michael",
        "lastName": "jordan",
        "gender": "M",
        "hireDate": "2000-01-01",
        "active": true,
        "createdBy": 1,
        "updatedBy": null,
        "departments": [
            {
                "departmentId": "d002",
                "fromDate": "2000-01-01",
                "toDate": "2005-12-31"
            }
        ],
        "salaries": [
            {
                "fromDate": "2000-01-01",
                "amount": 60116,
                "toDate": "2005-12-31"
            }
        ],
        "titles": [
            {
                "title": "Senior Engineer",
                "fromDate": "2000-01-01",
                "toDate": "2005-12-31"
            }
        ]
    },
    "timestamp": "2025-05-30T15:46:39.400776400Z"
}
```

#### 3. Update an employee’s personal info and modify department/salary/title histories  

**Endpoint:**  

```bash
PUT http://localhost:8080/api/v1/employees/11
Content-Type: application/json
```  

**Request Body:**  

```json
{
    "birthDate": "1990-08-01",
    "firstName": "Michael",
    "lastName": "jordan",
    "gender": "M",
    "hireDate": "2000-01-01",
    "active": true,
    "updatedBy": 2,
    "departments": [
        {
            "departmentId": "d003",
            "fromDate": "2001-01-01",
            "toDate": "2006-12-31"
        },
        {
            "departmentId": "d004",
            "fromDate": "2007-01-01",
            "toDate": "2008-12-31"
        }
    ],
    "salaries": [
        {
            "fromDate": "2001-01-01",
            "amount": 60116,
            "toDate": "2006-12-31"
        }
    ],
    "titles": [
        {
            "title": "Senior Engineer",
            "fromDate": "2001-01-01",
            "toDate": "2006-12-31"
        }
    ]
}
```

**Successful Response:**  

```json
{
    "message": "Record updated successfully",
    "error": null,
    "path": "/api/v1/employees/11",
    "status": 200,
    "data": {
        "id": 11,
        "birthDate": "1990-08-01",
        "firstName": "Michael",
        "lastName": "jordan",
        "gender": "M",
        "hireDate": "2000-01-01",
        "active": true,
        "createdBy": 1,
        "updatedBy": 2,
        "departments": [
            {
                "departmentId": "d004",
                "fromDate": "2007-01-01",
                "toDate": "2008-12-31"
            },
            {
                "departmentId": "d003",
                "fromDate": "2001-01-01",
                "toDate": "2006-12-31"
            }
        ],
        "salaries": [
            {
                "fromDate": "2001-01-01",
                "amount": 60116,
                "toDate": "2006-12-31"
            }
        ],
        "titles": [
            {
                "title": "Senior Engineer",
                "fromDate": "2001-01-01",
                "toDate": "2006-12-31"
            }
        ]
    },
    "timestamp": "2025-05-30T15:47:26.323244900Z"
}
```

**📝 Note**:
In this test scenario, the employee's personal information and associated historical records — including `departments`, `salaries`, and `titles` — are updated in a single request. Specifically for the departments, **the request includes two entries**, which results in the system **adding a new department (`d004`) to the employee’s history** alongside the existing one (`d003`).

This demonstrates that the update operation supports **replacing or extending the employee’s historical associations**, ensuring that each department, salary, and title entry is fully synchronized based on the payload. If a department previously linked to the employee is missing from the incoming list, it will be removed; if a new one appears, it will be added — enabling full control of historical tracking via the API.

This behavior supports **idempotent synchronization** and avoids duplicates or stale associations by **matching entries based on their composite keys** and performing precise insert/update/delete operations accordingly.

#### 4. Remove one child entity (e.g., one salary history record) during update and ensure proper orphan removal  

**Endpoint:**  

```bash
PUT http://localhost:8080/api/v1/employees/11
Content-Type: application/json
```  

**Request Body:**  

```json
{
    "birthDate": "1990-08-01",
    "firstName": "Michael",
    "lastName": "jordan",
    "gender": "M",
    "hireDate": "2000-01-01",
    "active": true,
    "updatedBy": 2,
    "departments": [
        {
            "departmentId": "d003",
            "fromDate": "2001-01-01",
            "toDate": "2006-12-31"
        }
    ],
    "salaries": [
        {
            "fromDate": "2001-01-01",
            "amount": 60116,
            "toDate": "2006-12-31"
        }
    ],
    "titles": [
        {
            "title": "Senior Engineer",
            "fromDate": "2001-01-01",
            "toDate": "2006-12-31"
        }
    ]
}
```

**Successful Response:**  

```json
{
    "message": "Record updated successfully",
    "error": null,
    "path": "/api/v1/employees/11",
    "status": 200,
    "data": {
        "id": 11,
        "birthDate": "1990-08-01",
        "firstName": "Michael",
        "lastName": "jordan",
        "gender": "M",
        "hireDate": "2000-01-01",
        "active": true,
        "createdBy": 1,
        "updatedBy": 2,
        "departments": [
            {
                "departmentId": "d003",
                "fromDate": "2001-01-01",
                "toDate": "2006-12-31"
            }
        ],
        "salaries": [
            {
                "fromDate": "2001-01-01",
                "amount": 60116,
                "toDate": "2006-12-31"
            }
        ],
        "titles": [
            {
                "title": "Senior Engineer",
                "fromDate": "2001-01-01",
                "toDate": "2006-12-31"
            }
        ]
    },
    "timestamp": "2025-05-30T15:47:54.316136400Z"
}
```

**📝 Note**:
In this test, the update request intentionally **omits the department `d004`** from the list of associated departments for the employee. As a result, the system correctly detects that `d004` is no longer part of the current request and proceeds to **remove the corresponding `DepartmentEmployee` relationship** from the database.

This showcases the system’s support for **orphan removal** during update operations. By comparing the current state of child entities with the updated payload, the system performs a **precise synchronization**—retaining only those child records that are explicitly provided and removing those that are no longer referenced.

The same logic applies to other associated entities like salaries and titles, making it easy to manage historical records through a consistent and intuitive update mechanism.


#### 5. Attempt to assign employee to non-existent department (should return validation error)  

**Endpoint:**  

```bash
PUT http://localhost:8080/api/v1/employees/11
Content-Type: application/json
```  

**Request Body:**  

```json
{
    "birthDate": "1990-08-01",
    "firstName": "Michael",
    "lastName": "jordan",
    "gender": "M",
    "hireDate": "2000-01-01",
    "active": true,
    "updatedBy": 2,
    "departments": [
        {
            "departmentId": "INVALID_DEPARTMENT",
            "fromDate": "2001-01-01",
            "toDate": "2006-12-31"
        }
    ],
    "salaries": [
        {
            "fromDate": "2001-01-01",
            "amount": 60116,
            "toDate": "2006-12-31"
        }
    ],
    "titles": [
        {
            "title": "Senior Engineer",
            "fromDate": "2001-01-01",
            "toDate": "2006-12-31"
        }
    ]
}
```

**Successful Response:**  

```json
{
    "message": "Record not found",
    "error": "Department not found: INVALID_DEPARTMENT",
    "path": "/api/v1/employees/11",
    "status": 404,
    "data": null,
    "timestamp": "2025-05-30T15:57:48.685584500Z"
}
```

#### 6. Delete an employee and verify cascading deletion of related child records  

**Endpoint:**  

```bash
DELETE http://localhost:8080/api/v1/employees/11
```  

**Successful Response:**  

```json
{
    "message": "Record deleted successfully",
    "error": null,
    "path": "/api/v1/employees/11",
    "status": 200,
    "data": null,
    "timestamp": "2025-05-30T15:58:27.085359900Z"
}
```

---

## 📝 Notes & Future Enhancements  

### Current Notes  

- This project demonstrates a **RESTful API implementation using Spring Boot** to model and manage a **One-to-Many relationship** using **Spring Data JPA** and **Hibernate**.  
- The underlying database is **PostgreSQL**, with schema relationships mapped via JPA annotations (e.g., `@OneToMany`, `@ManyToOne`, `@JoinColumn`).  
- The API provides full **CRUD operations** on parent and child entities using Spring Boot's REST controller support.  
- This is a foundational project focused on relational data modeling and persistence — **no authentication or authorization mechanism** is implemented yet.  


### Planned Enhancements  

- **Implement Security Layer** – Add **Spring Security** or **JWT-based authentication** to secure REST endpoints and restrict access to sensitive data.  

---

## 🔗 Related Repositories  

- REST API + JWT Authentication Repository, check out [Netflix Shows REST API with JWT Authentication](https://github.com/yoanesber/Spring-Boot-JWT-Auth-PostgreSQL).  
- Graphql API + PostgreSQL Repository, check out [Spring Boot GraphQL API for Employee & Department Management](https://github.com/yoanesber/Spring-Boot-Graphql-Employee-Management).  