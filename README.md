# ⚡REST API Spring Boot One To Many example with Hibernate and PostgreSQL
Implement JPA/Hibernate One-To-Many mapping with Hibernate in a Spring Boot CRUD example using `@OneToMany` annotation

## 🚀 Overview
This project is a REST API built using `Spring Boot v3.4.2` to handle CRUD operations for `Employee` and `Department` entities. The project integrates `Spring Data JPA` with `Hibernate` as the default JPA provider and uses `PostgreSQL` as the database. It also includes a custom HTTP response and a **custom error controller** to override the default `/error` response.
This project implements `One-To-Many` relationships between `Employee` as the parent entity and the child entities` DepartmentEmployee, SalaryEmployee, and TitleEmployee`. The relationship is managed using Spring Data JPA with Hibernate, and the project utilizes `EmbeddedId` for 1composite primary keys` in the relationship tables.

---

## ✨Tech Stack
The technology used in this project are:
- `Spring Boot 3.4.2` : Framework for building RESTful APIs
- `Spring Data JPA with Hibernate` : Simplifying database interactions
- `Spring Boot Starter Web` : Building RESTful APIs or web applications
- `PostgreSQL` : Database for persisting Netflix Shows
- `Lombok` : Reducing boilerplate code
---

## 📋 Project Structure
The project follows a layered architecture with the following structure:
```bash
one-to-many-postgresql/
│── src/main/java/com/yoanesber/spring/one_to_many_postgresql/
│   ├── controller/            # REST controllers handling API requests
│   ├── dto/                   # Data Transfer Objects for requests and responses
│   ├── entity/                # Entity classes representing database tables
│   ├── repository/            # JPA repositories for database access
│   ├── service/               # Business logic layer
│   │   ├── impl/              # Implementation of services
```
---

## 📂 Environment Configuration
Configuration values are stored in `.env.development` and referenced in `application.properties`.

Example `.env.development` file content:
```properties
# application
APP_PORT=8081
SPRING_PROFILES_ACTIVE=development
 
# postgres
SPRING_DATASOURCE_PORT=5432
SPRING_DATASOURCE_USERNAME=myusername
SPRING_DATASOURCE_PASSWORD=mypassword
SPRING_DATASOURCE_DB=employees_development
```

Example `application.properties` file content:
```properties
spring.application.name=one-to-many-postgresql
server.port=${APP_PORT}
spring.profiles.active=${SPRING_PROFILES_ACTIVE}

## datasource
spring.datasource.url=jdbc:postgresql://localhost:${SPRING_DATASOURCE_PORT}/${SPRING_DATASOURCE_DB}?currentSchema=employees
spring.datasource.username=${SPRING_DATASOURCE_USERNAME}
spring.datasource.password=${SPRING_DATASOURCE_PASSWORD}

## server error handling
server.error.whitelabel.enabled=false
server.error.include-message=always
```
---

## 💾 Database Schema & Relationships
The project uses PostgreSQL as its database, with a structured schema to store the data efficiently. Below is the DDL (Data Definition Language) used to create the database schema.

### Create Schema Employees
Database schema.
```sql
CREATE SCHEMA employees;
```

### Employee Table (employees.employee)
Stores employee details.
```sql
CREATE SEQUENCE employees.id_employee_seq
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;

CREATE TABLE employees.employee (
    id bigint NOT NULL DEFAULT nextval('employees.id_employee_seq'::regclass),
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

```

### Department Table (employees.department)
Stores department details.
```sql
CREATE TABLE employees.department (
    id character varying(4) NOT NULL,
    dept_name character varying(40) NOT NULL,
    active boolean NOT NULL,
    created_by bigint NOT NULL,
    created_date timestamp with time zone DEFAULT now() NOT NULL,
    updated_by bigint NOT NULL,
    updated_date timestamp with time zone DEFAULT now() NOT NULL,
    CONSTRAINT department_pkey PRIMARY KEY (id)
);

```

### DepartmentEmployee Table (employees.department_employee)
Maps employees to departments.
```sql
CREATE TABLE employees.department_employee (
    employee_id bigint NOT NULL,
    department_id character varying(255) NOT NULL,
    from_date date NOT NULL,
    to_date date NOT NULL,
    CONSTRAINT department_employee_pkey PRIMARY KEY (employee_id, department_id)
);

ALTER TABLE ONLY employees.department_employee
ADD CONSTRAINT department_employee_fkey1 FOREIGN KEY (employee_id) REFERENCES employees.employee(id) ON UPDATE RESTRICT ON DELETE CASCADE;

ALTER TABLE ONLY employees.department_employee
ADD CONSTRAINT department_employee_fkey2 FOREIGN KEY (department_id) REFERENCES employees.department(id) ON UPDATE RESTRICT ON DELETE CASCADE;
```

### Salary Table (employees.salary)
Stores employee salaries.
```sql
CREATE TABLE employees.salary (
    employee_id bigint NOT NULL,
    amount bigint NOT NULL,
    from_date date NOT NULL,
    to_date date NOT NULL,
    CONSTRAINT salary_pkey PRIMARY KEY (employee_id, from_date)
);

ALTER TABLE ONLY employees.salary
ADD CONSTRAINT salary_fkey FOREIGN KEY (employee_id) REFERENCES employees.employee(id) ON UPDATE RESTRICT ON DELETE CASCADE;
```

### Title Table (employees.title)
Stores employee titles.
```sql
CREATE TABLE employees.title (
    employee_id bigint NOT NULL,
    title character varying(50) NOT NULL,
    from_date date NOT NULL,
    to_date date,
    CONSTRAINT title_pkey PRIMARY KEY (employee_id, title, from_date)
);

ALTER TABLE ONLY employees.title
ADD CONSTRAINT title_fkey FOREIGN KEY (employee_id) REFERENCES employees.employee(id) ON UPDATE RESTRICT ON DELETE CASCADE;
```

### Relationships
The following is the relationship between tables:
- Department ↔ DepartmentEmployee (One-to-Many)
- Employee ↔ DepartmentEmployee (One-to-Many)
- Employee ↔ SalaryEmployee (One-to-Many)
- Employee ↔ TitleEmployee (One-to-Many)

### EmbeddedId
These tables are managed using EmbeddedId to define composite primary keys:
- employees.department_employee (employee_id, department_id)
- employees.salary (employee_id, from_date)
- employees.title (employee_id, title, from_date)
---

## 📜 Custom Handler
### Custom HTTP Response
This project includes a `CustomHttpResponse` entity to standardize API responses, ensuring consistency across all endpoints.
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

### Custom Error Handling
The default `/error` response is overridden using a `CustomErrorController`, which provides a structured error response format.

```java
import com.yoanesber.spring.hibernate.one_to_many_postgresql.entity.CustomHttpResponse;

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

## 🛠 Installation & Setup
A step by step series of examples that tell you how to get a development env running.
1. Clone the repository
```bash
git clone https://github.com/yoanesber/Spring-Boot-Hibernate-One-To-Many-PostgreSQL.git
```

2. Set up PostgreSQL
- Run DDL PostgreSQL to create Database Schema
- Configure the PostgreSQL database connection in application.properties

3. Run the application locally
- Make sure PostgreSQL is running, then execute: 
```bash
mvn spring-boot:run
```
- The API will be available at http://localhost:8081/ 
---

## 🔗 API Endpoints
These are APIs that we need to provide:

### Department API Endpoints
Apis to create, retrieve, update, delete Department.
- `GET` http://localhost:8081/api/v1/departments - Get all departments
- `GET` http://localhost:8081/api/v1/departments/d001 - Get a specific department
- `POST` http://localhost:8081/api/v1/departments - Create a new department with body request:
```json
{
    "id": "d001",
    "deptName": "Information Technology",
    "active": "true",
    "createdBy": 1,
    "updatedBy": 1
}
```
- `PUT` http://localhost:8081/api/v1/departments/d001 - Update existing department with body request:
```json
{
    "id": "d001",
    "deptName": "Technology & Security",
    "active": "true",
    "updatedBy": 2
}
```
- `DELETE` http://localhost:8081/api/v1/departments/d001 - Delete existing department

### Employee API Endpoints
Apis to create, retrieve, update, delete Employee
- `GET` http://localhost:8081/api/v1/employees - Get all employees
- `GET` http://localhost:8081/api/v1/employees/1 - Get a specific employee
- `POST` http://localhost:8081/api/v1/employees - Create a new employees with body request:
```json
{
    "birthDate": "1953-09-02",
    "firstName": "YOANES",
    "lastName": "BERCHMANS",
    "gender": "M",
    "hireDate": "1986-06-27",
    "activeStatus": true,
    "createdBy": 1,
    "updatedBy": 1,
    "departments": [
        {
            "departmentId": "d002",
            "fromDate": "2024-01-01",
            "toDate": "2024-01-31"
        },
        {
            "departmentId": "d001",
            "fromDate": "2024-02-01",
            "toDate": "2024-02-29"
        }
    ],
    "salaries": [
        {
            "fromDate": "1986-06-26",
            "amount": 60116,
            "toDate": "1987-06-27"
        }
    ],
    "titles": [
        {
            "title": "Senior Engineer",
            "fromDate": "1986-06-26",
            "toDate": "1986-06-30"
        },
        {
            "title": "Engineer Manager",
            "fromDate": "1986-07-01",
            "toDate": "1986-07-31"
        }
    ]
}
```
- `PUT` http://localhost:8081/api/v1/employees/1 - Update existing employee with body request:
```json
{
    "birthDate": "1953-09-02",
    "firstName": "YOANES",
    "lastName": "BERCHMANS",
    "gender": "M",
    "hireDate": "1986-07-27",
    "activeStatus": true,
    "updatedBy": 2,
    "departments": [
        {
            "departmentId": "d002",
            "fromDate": "2024-01-01",
            "toDate": "2024-01-31"
        }
    ],
    "salaries": [
        {
            "fromDate": "1986-06-26",
            "amount": 60116,
            "toDate": "1987-06-27"
        }
    ],
    "titles": [
        {
            "title": "Senior Engineer",
            "fromDate": "1986-06-26",
            "toDate": "1986-06-30"
        }
    ]
}
```
- `DELETE` http://localhost:8081/api/v1/employees/1 - Delete an employee
---

This project follows best practices in Spring Boot development, ensuring efficiency and maintainability.