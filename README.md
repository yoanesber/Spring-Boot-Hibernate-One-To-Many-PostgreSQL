# REST API Spring Boot One To Many example with Hibernate and PostgreSQL  

Implement JPA/Hibernate **One-To-Many** mapping with **Hibernate** in a Spring Boot CRUD example using `@OneToMany` annotation.  

## 📖 Overview  

This project is a REST API built using **Spring Boot** to handle CRUD operations for **Employee** and **Department** entities. The project integrates **Spring Data JPA with Hibernate** as the default JPA provider and uses **PostgreSQL** as the database. It also includes a custom HTTP response and a **custom error controller** to override the default `/error` response.  

This project implements `One-To-Many` relationships between **Employee** as the parent entity and the child entities **DepartmentEmployee, SalaryEmployee, and TitleEmployee**. The relationship is managed using Spring Data JPA with Hibernate, and the project utilizes `EmbeddedId` for `composite primary keys` in the relationship tables.  

---

## 🤖 Tech Stack  

The technology used in this project are:  

- `Spring Boot Starter Web` – Building RESTful APIs or web applications
- `PostgreSQL` – Database for persisting Netflix Shows
- `Hibernate` – Simplifying database interactions
- `Lombok` – Reducing boilerplate code

---

## 🏗️ Project Structure  

The project is organized into the following package structure:  

```bash
one-to-many-postgresql/
│── src/main/java/com/yoanesber/spring/one_to_many_postgresql/
│   ├── 📂controller/            # REST controllers handling API requests
│   ├── 📂dto/                   # Data Transfer Objects for requests and responses
│   ├── 📂entity/                # Entity classes representing database tables
│   ├── 📂repository/            # JPA repositories for database access
│   ├── 📂service/               # Business logic layer
│   │   ├── 📂impl/              # Implementation of services
```
---

## ⚙ Environment Configuration  

Configuration values are stored in `.env.development` and referenced in `application.properties`.  
Example `.env.development` file content:  

```properties
# Application properties
APP_PORT=8081
SPRING_PROFILES_ACTIVE=development
 
# Database properties
SPRING_DATASOURCE_PORT=5432
SPRING_DATASOURCE_USERNAME=your_username
SPRING_DATASOURCE_PASSWORD=your_password
SPRING_DATASOURCE_DB=your_db
SPRING_DATASOURCE_SCHEMA=your_schema
```

Example `application.properties` file content:  

```properties
# Application properties
spring.application.name=one-to-many-postgresql
server.port=${APP_PORT}
spring.profiles.active=${SPRING_PROFILES_ACTIVE}

# Database properties
spring.datasource.url=jdbc:postgresql://localhost:${SPRING_DATASOURCE_PORT}/${SPRING_DATASOURCE_DB}?currentSchema==${SPRING_DATASOURCE_SCHEMA}
spring.datasource.username=${SPRING_DATASOURCE_USERNAME}
spring.datasource.password=${SPRING_DATASOURCE_PASSWORD}

## Server error properties
server.error.whitelabel.enabled=false
server.error.include-message=always
```
---

## 💾 Database Schema (DDL – PostgreSQL)  

The following is the database schema for the PostgreSQL database used in this project:  

```sql
CREATE SCHEMA your_schema;

-- table your_schema.employee
CREATE TABLE IF NOT EXISTS your_schema.employee (
    id bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    birth_date date NOT NULL,
    first_name character varying(20) NOT NULL,
    last_name character varying(20),
    gender character varying(1) NOT NULL,
    hire_date date NOT NULL,
    active boolean DEFAULT false NOT NULL,
    created_by bigint NOT NULL,
    created_date timestamp with time zone DEFAULT now() NOT NULL,
    updated_by bigint NOT NULL,
    updated_date timestamp with time zone DEFAULT now() NOT NULL,
    CONSTRAINT employee_pkey PRIMARY KEY (id),
);

-- table your_schema.department
CREATE TABLE IF NOT EXISTS your_schema.department (
    id character varying(4) NOT NULL,
    dept_name character varying(40) NOT NULL,
    active boolean NOT NULL,
    created_by bigint NOT NULL,
    created_date timestamp with time zone DEFAULT now() NOT NULL,
    updated_by bigint NOT NULL,
    updated_date timestamp with time zone DEFAULT now() NOT NULL,
    CONSTRAINT department_pkey PRIMARY KEY (id)
);

-- table your_schema.department_employee
CREATE TABLE IF NOT EXISTS your_schema.department_employee (
    employee_id bigint NOT NULL,
    department_id character varying(255) NOT NULL,
    from_date date NOT NULL,
    to_date date NOT NULL,
    CONSTRAINT department_employee_pkey PRIMARY KEY (employee_id, department_id),
    CONSTRAINT department_employee_fkey1 FOREIGN KEY (employee_id) REFERENCES your_schema.employee(id) ON UPDATE RESTRICT ON DELETE CASCADE,
    CONSTRAINT department_employee_fkey2 FOREIGN KEY (department_id) REFERENCES your_schema.department(id) ON UPDATE RESTRICT ON DELETE CASCADE
);


-- table your_schema.salary
CREATE TABLE IF NOT EXISTS your_schema.salary (
    employee_id bigint NOT NULL,
    amount bigint NOT NULL,
    from_date date NOT NULL,
    to_date date NOT NULL,
    CONSTRAINT salary_pkey PRIMARY KEY (employee_id, from_date),
    CONSTRAINT salary_fkey FOREIGN KEY (employee_id) REFERENCES your_schema.employee(id) ON UPDATE RESTRICT ON DELETE CASCADE
);

-- table your_schema.title
CREATE TABLE IF NOT EXISTS your_schema.title (
    employee_id bigint NOT NULL,
    title character varying(50) NOT NULL,
    from_date date NOT NULL,
    to_date date,
    CONSTRAINT title_pkey PRIMARY KEY (employee_id, title, from_date),
    CONSTRAINT title_fkey FOREIGN KEY (employee_id) REFERENCES your_schema.employee(id) ON UPDATE RESTRICT ON DELETE CASCADE
);

```

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

## 🎛️ Custom Handler  

This project implements a Custom Handler to manage HTTP responses and error handling efficiently.  

1. Custom HTTP Response  

Custom HTTP Response structure ensures consistency across API responses, including metadata such as timestamps, status codes, and meaningful messages.  

```java
public class CustomHttpResponse {
    private Integer statusCode;
    private LocalDateTime timestamp = LocalDateTime.now();
    private String message;
    private Object data;

    public CustomHttpResponse(Integer statusCode, String message, Object data) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }
}
```

2. Custom Error Handling  

The default `/error` response is overridden using a `CustomErrorController`, which provides a structured error response format.  

```java
@RestController
@RequestMapping("/error")
public class CustomErrorController implements ErrorController {
    @RequestMapping
    public ResponseEntity<CustomHttpResponse> handleError(HttpServletRequest request) {
        CustomHttpResponse errorDetails = new CustomHttpResponse();
        errorDetails.setTimestamp(LocalDateTime.now());
 
        Integer statusCode = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        String message = (String) request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
 
        if (statusCode == null) {
            statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
        }
       
        if (statusCode == HttpStatus.NOT_FOUND.value()) {
            message = "The requested resource was not found";
        } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
            message = "You don't have permission to access this resource";
        } else if (statusCode == HttpStatus.BAD_REQUEST.value()) {
            message = "The request was invalid or cannot be served";
        } else if (statusCode == HttpStatus.UNAUTHORIZED.value()) {
            message = "You need to authenticate to access this resource";
        } else {
            message = (message != null) ? message : "Unexpected error occurred";
        }

        errorDetails.setStatusCode(statusCode);
        errorDetails.setMessage(message);
        errorDetails.setData(null);
 
        return new ResponseEntity<>(errorDetails, HttpStatus.valueOf(statusCode));
    }
}
```
---

## 🛠️ Installation & Setup  

A step by step series of examples that tell you how to get a development env running.  

1. Ensure you have **Git installed on your Windows** machine, then clone the repository to your local environment:  

```bash
git clone https://github.com/yoanesber/Spring-Boot-Hibernate-One-To-Many-PostgreSQL.git
cd Spring-Boot-Hibernate-One-To-Many-PostgreSQL
```

2. Set up PostgreSQL  

- Run the provided DDL script to set up the database schema
- Configure the connection in `.env.development` file:  

```properties
# Database properties
SPRING_DATASOURCE_PORT=5432
SPRING_DATASOURCE_USERNAME=your_username
SPRING_DATASOURCE_PASSWORD=your_password
SPRING_DATASOURCE_DB=your_db
SPRING_DATASOURCE_SCHEMA=your_schema
```

3. Run the application locally
Make sure PostgreSQL is running, then execute:  

```bash
mvn spring-boot:run
```

4. Now, API is available at:  

```bash
http://localhost:8081/ 
```

You can test the API using: Postman (Desktop/Web version) or cURL

---

## 🌐 API Endpoints  

These are APIs that we need to provide:  

### Department API Endpoints  

Apis to create, retrieve, update, delete Department.  

- `GET` http://localhost:8081/api/v1/departments - Get all departments.
- `GET` http://localhost:8081/api/v1/departments/d001 - Get a specific department.  

**Successful Response:**  

```json
{
    "statusCode": 200,
    "timestamp": "2025-03-31T10:30:15.8322985",
    "message": "Department fetched successfully",
    "data": {
        "id": "d001",
        "deptName": "Operation",
        "active": true,
        "createdBy": 1001,
        "createdDate": "2025-03-20T03:00:00.000+00:00",
        "updatedBy": 1001,
        "updatedDate": "2025-03-20T03:00:00.000+00:00"
    }
}
```

- `POST` http://localhost:8081/api/v1/departments - Create a new department.  

**Request Body:**  

```json
{
    "id": "d001",
    "deptName": "Information Technology",
    "active": "true",
    "createdBy": 1,
    "updatedBy": 1
}
```

**Successful Response:**  

```json
{
    "statusCode": 201,
    "timestamp": "2025-03-31T10:34:20.8317982",
    "message": "Department saved successfully",
    "data": null
}
```

- `PUT` http://localhost:8081/api/v1/departments/d001 - Update existing department.  

**Request Body:**  

```json
{
    "deptName": "Technology & Security",
    "active": "true",
    "updatedBy": 2
}
```

**Successful Response:**  

```json
{
    "statusCode": 200,
    "timestamp": "2025-03-31T10:34:59.9032581",
    "message": "Department updated successfully",
    "data": {
        "id": "d001",
        "deptName": "Technology & Security",
        "active": true,
        "createdBy": 1,
        "createdDate": "2025-03-31T03:34:20.792+00:00",
        "updatedBy": 2,
        "updatedDate": "2025-03-31T03:34:59.891+00:00"
    }
}
```

- `DELETE` http://localhost:8081/api/v1/departments/d001 - Delete existing department.  

**Successful Response:**  

```json
{
    "statusCode": 200,
    "timestamp": "2025-03-31T10:31:39.148143",
    "message": "Department deleted successfully",
    "data": null
}
```

### Employee API Endpoints  

Apis to create, retrieve, update, delete Employee.  

- `GET` http://localhost:8081/api/v1/employees - Get all employees.  
- `GET` http://localhost:8081/api/v1/employees/1 - Get a specific employee.  

**Successful Response:**  

```json
{
    "statusCode": 200,
    "timestamp": "2025-03-31T10:37:05.9835263",
    "message": "Employee fetched successfully",
    "data": {
        "id": 1,
        "birthDate": "1990-08-01",
        "firstName": "YOANES",
        "lastName": "BERCHMANS",
        "gender": "M",
        "hireDate": "2000-01-01",
        "activeStatus": true,
        "createdBy": 1,
        "createdDate": "2025-02-27T13:50:50.779+00:00",
        "updatedBy": 2,
        "updatedDate": "2025-02-27T13:51:19.361+00:00",
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
}
```

- `POST` http://localhost:8081/api/v1/employees - Create a new employees.  

**Request Body:**  

```json
{
    "birthDate": "1990-08-01",
    "firstName": "YOANES",
    "lastName": "BERCHMANS",
    "gender": "M",
    "hireDate": "2000-01-01",
    "activeStatus": true,
    "createdBy": 1,
    "updatedBy": 1,
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
    "statusCode": 201,
    "timestamp": "2025-03-31T10:46:07.7919509",
    "message": "Employee saved successfully",
    "data": null
}
```

- `PUT` http://localhost:8081/api/v1/employees/1 - Update existing employee.  

**Request Body:**  

```json
{
    "birthDate": "1990-08-01",
    "firstName": "YOANES",
    "lastName": "BERCHMANS",
    "gender": "M",
    "hireDate": "2000-01-01",
    "activeStatus": true,
    "updatedBy": 2,
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
    "statusCode": 200,
    "timestamp": "2025-03-31T10:47:43.8114794",
    "message": "Employee updated successfully",
    "data": {
        "id": 1,
        "birthDate": "1990-08-01",
        "firstName": "YOANES",
        "lastName": "BERCHMANS",
        "gender": "M",
        "hireDate": "2000-01-01",
        "activeStatus": true,
        "updatedBy": 2,
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
}
```

- `DELETE` http://localhost:8081/api/v1/employees/1 - Delete an employee.  

**Successful Response:**  

```json
{
    "statusCode": 200,
    "timestamp": "2025-03-31T10:50:45.6541365",
    "message": "Employee deleted successfully",
    "data": null
}
```