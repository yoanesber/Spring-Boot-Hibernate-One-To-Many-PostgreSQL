<div style="background-color: white">
    <h1 data-start="81" data-end="119">📦 REST API Spring Boot One To Many example with Hibernate and PostgreSQL</h1>
    <h2 data-start="121" data-end="137">🚀 Overview</h2>
    <p style="text-align:justify;" data-pm-slice="1 1 []"><span>This project is a REST API built using <strong>Spring Boot v3.4.2</strong> to handle CRUD operations for <strong>Employee</strong> and <strong>Department</strong> entities. The project integrates <strong>Spring Data JPA with Hibernate</strong> as the default JPA provider and uses <strong>PostgreSQL</strong> as the database. It also includes a <strong>custom HTTP response</strong> and a <strong>custom error controller</strong> to override the default <strong>"/error"</strong> response.</span></p>
    <p style="text-align:justify;"><span>This project implements <strong>One-To-Many relationships</strong> between <strong>Employee</strong> as the parent entity and the child entities <strong>DepartmentEmployee, SalaryEmployee, and TitleEmployee</strong>. The relationship is managed using <strong>Spring Data JPA</strong> with <strong>Hibernate</strong>, and the project utilizes <strong>EmbeddedId</strong> for composite primary keys in the relationship tables.</span>.</p>
    <hr>
    <h2 data-start="561" data-end="579">✨Tech Stack</h2>
    <ul data-start="580" data-end="1052">
        <li data-start="580" data-end="600"><strong data-start="582" data-end="590">Java </strong>v21</li>
        <li data-start="601" data-end="627"><strong data-start="603" data-end="618">Spring Boot </strong>v3.4.2</li>
        <li data-start="628" data-end="662"><strong data-start="630" data-end="647">Spring Data JPA with Hibernate </strong>(simplifying database interactions)</li>
        <li data-start="628" data-end="662"><strong data-start="630" data-end="647">Spring Boot Starter Web </strong>(building RESTful APIs or web applications)</li>
        <li data-start="663" data-end="685"><strong data-start="665" data-end="679">PostgreSQL Database</strong></li>
        <li data-start="919" data-end="960"><strong data-start="921" data-end="931">Lombok</strong> (Reducing boilerplate code)</li>
    </ul>
    <hr>
    <h2 data-start="1815" data-end="1840">📋 Project Structure</h2>
    <p>The project follows a layered architecture with the following structure:<br>├── <strong>controller </strong>: Exposes REST API endpoints for handling requests and responses)</p>
    <p>├── <strong>dto </strong>: Data Transfer Objects (DTOs) for request/response payloads)&nbsp;</p>
    <p>├── <strong>entity </strong>: Entity classes representing database tables)</p>
    <p>├── <strong>repository </strong>: JPA repositories for database access)<br>├── <strong>service </strong>: Business logic layer, separating service interfaces and their implementations)</p>
    <hr>
    <h2 data-start="2291" data-end="2324">📂 Environment Configuration</h2>
    <p data-start="2325" data-end="2508">The application uses <code data-start="2346" data-end="2352">.env</code> files for different environments (<code data-start="2387" data-end="2400">development</code>, <code data-start="2402" data-end="2411">testing</code>, <code data-start="2413" data-end="2425">production</code>), which are referenced in the respective <code data-start="2467" data-end="2501">application.properties</code> files.</p>
    <p data-start="2510" data-end="2544"><strong data-start="2510" data-end="2542">Example </strong><code data-start="2520" data-end="2526"><strong data-start="2510" data-end="2542">.env.development</strong></code><strong data-start="2510" data-end="2542"> file content:</strong></p>
    <div style="background-color:#23272e;color:#abb2bf;font-family:Consolas, 'Courier New', monospace;font-size:14px;font-weight:normal;line-height:19px;white-space:pre;">
        <div><span style="color:#7f848e;"># application</span></div>
        <div><span style="color:#e06c75;">APP_PORT</span><span style="color:#56b6c2;">=</span><span style="color:#98c379;">8081</span></div>
        <div><span style="color:#e06c75;">SPRING_PROFILES_ACTIVE</span><span style="color:#56b6c2;">=</span><span style="color:#98c379;">development</span></div>
        <div>&nbsp;</div>
    </div>
    <div style="background-color:#23272e;color:#abb2bf;font-family:Consolas, 'Courier New', monospace;font-size:14px;font-weight:normal;line-height:19px;white-space:pre;">
        <div><span style="color:#7f848e;"># postgres</span></div>
        <div><span style="color:#e06c75;">SPRING_DATASOURCE_PORT</span><span style="color:#56b6c2;">=</span><span style="color:#98c379;">5432</span></div>
        <div><span style="color:#e06c75;">SPRING_DATASOURCE_USERNAME</span><span style="color:#56b6c2;">=myusername</span></div>
        <div><span style="color:#e06c75;">SPRING_DATASOURCE_PASSWORD</span><span style="color:#56b6c2;">=mypassword</span></div>
        <div><span style="color:#e06c75;">SPRING_DATASOURCE_DB</span><span style="color:#56b6c2;">=</span><span style="color:#98c379;">employees_development</span></div>
    </div>
    <p>&nbsp;</p>
    <p data-start="2510" data-end="2544"><strong data-start="2510" data-end="2542">Example </strong><code data-start="2520" data-end="2526"><strong data-start="2510" data-end="2542">application.properties</strong></code><strong data-start="2510" data-end="2542"> file content:</strong></p>
    <div style="background-color:#23272e;color:#abb2bf;font-family:Consolas, 'Courier New', monospace;font-size:14px;font-weight:normal;line-height:19px;white-space:pre;">
        <p>spring.application.name=one-to-many-postgresql<br>server.port=${APP_PORT}<br>spring.profiles.active=${SPRING_PROFILES_ACTIVE}</p>
        <p>&nbsp;</p>
        <p>## datasource <br>spring.datasource.url=jdbc:postgresql://localhost:${SPRING_DATASOURCE_PORT}/${SPRING_DATASOURCE_DB}?currentSchema=employees<br>spring.datasource.username=${SPRING_DATASOURCE_USERNAME}<br>spring.datasource.password=${SPRING_DATASOURCE_PASSWORD}</p>
        <p>&nbsp;</p>
        <p>## hibernate <br>spring.jpa.show-sql=true<br>spring.jpa.hibernate.ddl-auto=update<br>spring.jpa.hibernate.naming.implicit-strategy=org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl<br>spring.jpa.properties.hibernate.format_sql=true<br>spring.jpa.open-in-view=true</p>
        <p>&nbsp;</p>
        <p>## server error handling<br>server.error.whitelabel.enabled=false<br>server.error.include-message=always</p>
    </div>
    <hr>
    <h2 data-start="2753" data-end="2781">💾 Database Schema</h2>
    <p>The database schema consists of the following tables:</p>
    <p>&nbsp;</p>
    <p data-start="2786" data-end="2812"><strong data-start="2786" data-end="2810">Schema Employees</strong></p>
    <p data-start="2786" data-end="2812">Database schema.</p>
    <div style="background-color:#23272e;color:#abb2bf;font-family:Consolas, 'Courier New', monospace;font-size:14px;font-weight:normal;line-height:19px;white-space:pre;">
        <p>CREATE SCHEMA employees;</p>
    </div>
    <p>&nbsp;</p>
    <p data-start="2786" data-end="2812"><strong data-start="2786" data-end="2810">Employee Table (employees.employee)</strong></p>
    <p data-start="2786" data-end="2812">Stores employee details.</p>
    <div style="background-color:#23272e;color:#abb2bf;font-family:Consolas, 'Courier New', monospace;font-size:14px;font-weight:normal;line-height:19px;white-space:pre;">
        <p>CREATE TABLE employees.employee (<br> id bigint NOT NULL,<br> birth_date date NOT NULL,<br> first_name character varying(20) NOT NULL,<br> last_name character varying(20),<br> gender character varying(1) NOT NULL,<br> hire_date date NOT NULL,<br> active boolean DEFAULT false NOT NULL,<br> created_by bigint NOT NULL,<br> created_date timestamp with time zone DEFAULT now() NOT NULL,<br> updated_by bigint NOT NULL,<br> updated_date timestamp with time zone DEFAULT now() NOT NULL<br>);</p>
        <p>&nbsp;</p>
        <p>ALTER TABLE ONLY employees.employee<br> ADD CONSTRAINT idx_16988_primary PRIMARY KEY (id);<br><br>CREATE SEQUENCE employees.id_employee_seq<br> START WITH 1<br> INCREMENT BY 1<br> NO MINVALUE<br> NO MAXVALUE<br> CACHE 1;</p>
        <p>&nbsp;</p>
        <p>ALTER SEQUENCE employees.id_employee_seq OWNED BY employees.employee.id;</p>
        <p>ALTER TABLE ONLY employees.employee ALTER COLUMN id SET DEFAULT nextval('employees.id_employee_seq'::regclass);</p>
        <p>SELECT pg_catalog.setval('employees.id_employee_seq', 1, true);</p>
    </div>
    <p>&nbsp;</p>
    <p data-start="2786" data-end="2812"><strong data-start="2922" data-end="2966">Department Table (employees.department)</strong><br>Stores department details.</p>
    <div style="background-color:#23272e;color:#abb2bf;font-family:Consolas, 'Courier New', monospace;font-size:14px;font-weight:normal;line-height:19px;white-space:pre;">
        <p>CREATE TABLE employees.department (<br> id character varying(4) NOT NULL,<br> dept_name character varying(40) NOT NULL,<br> active boolean NOT NULL,<br> created_by bigint NOT NULL,<br> created_date timestamp with time zone DEFAULT now() NOT NULL,<br> updated_by bigint NOT NULL,<br> updated_date timestamp with time zone DEFAULT now() NOT NULL<br>);<br><br>ALTER TABLE ONLY employees.department<br> ADD CONSTRAINT idx_16979_primary PRIMARY KEY (id);</p>
    </div>
    <p>&nbsp;</p>
    <p data-start="2786" data-end="2812"><strong data-start="3099" data-end="3147">DepartmentEmployee Table (employees.department_employee)</strong><br>Maps employees to departments.</p>
    <div style="background-color:#23272e;color:#abb2bf;font-family:Consolas, 'Courier New', monospace;font-size:14px;font-weight:normal;line-height:19px;white-space:pre;">
        <p>CREATE TABLE employees.department_employee (<br> employee_id bigint NOT NULL,<br> department_id character varying(255) NOT NULL,<br> from_date date NOT NULL,<br> to_date date NOT NULL<br>);</p>
        <p>&nbsp;</p>
        <p>ALTER TABLE ONLY employees.department_employee<br> ADD CONSTRAINT idx_16982_primary PRIMARY KEY (employee_id, department_id);</p>
        <p>&nbsp;</p>
        <p>ALTER TABLE ONLY employees.department_employee<br> ADD CONSTRAINT dept_emp_ibfk_1 FOREIGN KEY (employee_id) REFERENCES employees.employee(id) ON UPDATE RESTRICT ON DELETE CASCADE;</p>
        <p>&nbsp;</p>
        <p>ALTER TABLE ONLY employees.department_employee<br> ADD CONSTRAINT dept_emp_ibfk_2 FOREIGN KEY (department_id) REFERENCES employees.department(id) ON UPDATE RESTRICT ON DELETE CASCADE;</p>
    </div>
    <p>&nbsp;</p>
    <p data-start="2786" data-end="2812"><strong data-start="2786" data-end="2810">Salary Table (employees.salary)</strong><br>Stores employee salaries.</p>
    <div style="background-color:#23272e;color:#abb2bf;font-family:Consolas, 'Courier New', monospace;font-size:14px;font-weight:normal;line-height:19px;white-space:pre;">
        <p>CREATE TABLE employees.salary (<br> employee_id bigint NOT NULL,<br> amount bigint NOT NULL,<br> from_date date NOT NULL,<br> to_date date NOT NULL<br>);</p>
        <p>&nbsp;</p>
        <p>ALTER TABLE ONLY employees.salary<br> ADD CONSTRAINT idx_16991_primary PRIMARY KEY (employee_id, from_date);</p>
        <p>&nbsp;</p>
        <p>ALTER TABLE ONLY employees.salary<br> ADD CONSTRAINT salaries_ibfk_1 FOREIGN KEY (employee_id) REFERENCES employees.employee(id) ON UPDATE RESTRICT ON DELETE CASCADE;</p>
    </div>
    <p>&nbsp;</p>
    <p data-start="2786" data-end="2812"><strong data-start="2786" data-end="2810">Title Table (employees.title)</strong><br>Stores employee titles.</p>
    <div style="background-color:#23272e;color:#abb2bf;font-family:Consolas, 'Courier New', monospace;font-size:14px;font-weight:normal;line-height:19px;white-space:pre;">
        <p>CREATE TABLE employees.title (<br> employee_id bigint NOT NULL,<br> title character varying(50) NOT NULL,<br> from_date date NOT NULL,<br> to_date date<br>);</p>
        <p>&nbsp;</p>
        <p>ALTER TABLE ONLY employees.title<br> ADD CONSTRAINT idx_16994_primary PRIMARY KEY (employee_id, title, from_date);</p>
        <p>&nbsp;</p>
        <p>ALTER TABLE ONLY employees.title<br> ADD CONSTRAINT titles_ibfk_1 FOREIGN KEY (employee_id) REFERENCES employees.employee(id) ON UPDATE RESTRICT ON DELETE CASCADE;</p>
    </div>
    <p>&nbsp;</p>
    <p data-start="2786" data-end="2812"><strong data-start="2786" data-end="2810">Relationships</strong></p>
    <p data-start="2786" data-end="2812">The following is the relationship between tables:</p>
    <ul>
        <li>
            <p data-start="2786" data-end="2812">Department ↔ DepartmentEmployee (One-to-Many)</p>
        </li>
        <li>
            <p data-start="2786" data-end="2812">Employee ↔ DepartmentEmployee (One-to-Many)</p>
        </li>
        <li>Employee ↔ SalaryEmployee (One-to-Many)</li>
        <li>Employee ↔ TitleEmployee (One-to-Many)</li>
    </ul>
    <p data-start="2786" data-end="2812">&nbsp;</p>
    <p data-start="2786" data-end="2812"><strong data-start="2786" data-end="2810">EmbeddedId</strong><br>These tables are managed using EmbeddedId to define composite primary keys:</p>
    <ul>
        <li>
            <p data-start="2786" data-end="2812">employees.department_employee (employee_id, department_id)</p>
        </li>
        <li>
            <p data-start="2786" data-end="2812">employees.salary (employee_id, from_date)</p>
        </li>
        <li>
            <p data-start="2786" data-end="2812">employees.title (employee_id, title, from_date)</p>
        </li>
    </ul>
    <p>&nbsp;</p>
    <hr>
    <h2 data-start="2753" data-end="2781">📜 Custom Handler</h2>
    <p data-start="2786" data-end="2812"><strong data-start="2786" data-end="2810">Custom HTTP Response</strong></p>
    <p data-start="2786" data-end="2812">This project includes a CustomHttpResponse entity to standardize API responses, ensuring consistency across all endpoints.</p>
    <div style="background-color:#23272e;color:#abb2bf;font-family:Consolas, 'Courier New', monospace;font-size:14px;font-weight:normal;line-height:19px;white-space:pre;">
        <div style="background-color:#23272e;color:#abb2bf;font-family:Consolas, 'Courier New', monospace;font-size:14px;font-weight:normal;line-height:19px;white-space:pre;">
            <div><span style="color:#c678dd;">public</span><span style="color:#e06c75;"> </span><span style="color:#c678dd;">class</span><span style="color:#e06c75;"> </span><span style="color:#e5c07b;">CustomHttpResponse</span><span style="color:#e06c75;"> </span><span style="color:#abb2bf;">{</span></div>
            <div><span style="color:#e06c75;">&nbsp; &nbsp; </span><span style="color:#c678dd;">private</span><span style="color:#e06c75;"> </span><span style="color:#e5c07b;">Integer</span><span style="color:#e06c75;"> statusCode</span><span style="color:#abb2bf;">;</span></div>
            <div><span style="color:#e06c75;">&nbsp; &nbsp; </span><span style="color:#c678dd;">private</span><span style="color:#e06c75;"> </span><span style="color:#e5c07b;">LocalDateTime</span><span style="color:#e06c75;"> timestamp </span><span style="color:#56b6c2;">=</span><span style="color:#e06c75;"> </span><span style="color:#e5c07b;">LocalDateTime</span><span style="color:#abb2bf;">.</span><span style="color:#61afef;">now</span><span style="color:#abb2bf;">();</span></div>
            <div><span style="color:#e06c75;">&nbsp; &nbsp; </span><span style="color:#c678dd;">private</span><span style="color:#e06c75;"> </span><span style="color:#e5c07b;">String</span><span style="color:#e06c75;"> message</span><span style="color:#abb2bf;">;</span></div>
            <div><span style="color:#e06c75;">&nbsp; &nbsp; </span><span style="color:#c678dd;">private</span><span style="color:#e06c75;"> </span><span style="color:#e5c07b;">Object</span><span style="color:#e06c75;"> data</span><span style="color:#abb2bf;">;</span></div>
        </div>
        <p>&nbsp;</p>
        <div style="background-color:#23272e;color:#abb2bf;font-family:Consolas, 'Courier New', monospace;font-size:14px;font-weight:normal;line-height:19px;white-space:pre;">
            <div><span style="color:#e06c75;">&nbsp; &nbsp; </span><span style="color:#c678dd;">public</span><span style="color:#61afef;"> CustomHttpResponse</span><span style="color:#abb2bf;">(</span><span style="color:#e5c07b;">Integer</span><span style="color:#abb2bf;"> </span><span style="color:#e06c75;">statusCode</span><span style="color:#abb2bf;">, </span><span style="color:#e5c07b;">String</span><span style="color:#abb2bf;"> </span><span style="color:#e06c75;">message</span><span style="color:#abb2bf;">, </span><span style="color:#e5c07b;">Object</span><span style="color:#abb2bf;"> </span><span style="color:#e06c75;">data</span><span style="color:#abb2bf;">)</span><span style="color:#61afef;"> </span><span style="color:#abb2bf;">{</span></div>
            <div><span style="color:#abb2bf;">&nbsp; &nbsp; &nbsp; &nbsp; </span><span style="color:#e5c07b;">this</span><span style="color:#abb2bf;">.</span><span style="color:#e5c07b;">statusCode</span><span style="color:#abb2bf;"> </span><span style="color:#56b6c2;">=</span><span style="color:#abb2bf;"> statusCode;</span></div>
            <div><span style="color:#abb2bf;">&nbsp; &nbsp; &nbsp; &nbsp; </span><span style="color:#e5c07b;">this</span><span style="color:#abb2bf;">.</span><span style="color:#e5c07b;">message</span><span style="color:#abb2bf;"> </span><span style="color:#56b6c2;">=</span><span style="color:#abb2bf;"> message;</span></div>
            <div><span style="color:#abb2bf;">&nbsp; &nbsp; &nbsp; &nbsp; </span><span style="color:#e5c07b;">this</span><span style="color:#abb2bf;">.</span><span style="color:#e5c07b;">data</span><span style="color:#abb2bf;"> </span><span style="color:#56b6c2;">=</span><span style="color:#abb2bf;"> data;</span></div>
            <div><span style="color:#abb2bf;">&nbsp; &nbsp; }</span></div>
            <div><span style="color:#abb2bf;">}</span></div>
        </div>
    </div>
    <p><br>&nbsp;<strong data-start="2786" data-end="2810">Custom Error Handling</strong></p>
    <p data-start="2786" data-end="2812">The default "/error" response is overridden using a CustomErrorController, which provides a structured error response format.</p>
    <div style="background-color:#23272e;color:#abb2bf;font-family:Consolas, 'Courier New', monospace;font-size:14px;font-weight:normal;line-height:19px;white-space:pre;">
        <div style="background-color:#23272e;color:#abb2bf;font-family:Consolas, 'Courier New', monospace;font-size:14px;font-weight:normal;line-height:19px;white-space:pre;">
            <div style="background-color:#23272e;color:#abb2bf;font-family:Consolas, 'Courier New', monospace;font-size:14px;font-weight:normal;line-height:19px;white-space:pre;">
                <div><span style="color:#c678dd;">import</span><span style="color:#e06c75;"> </span><span style="color:#e5c07b;">com.yoanesber.spring.hibernate.one_to_many_postgresql.entity.CustomHttpResponse</span><span style="color:#abb2bf;">;</span></div>
            </div>
            <p>&nbsp;</p>
            <div style="background-color:#23272e;color:#abb2bf;font-family:Consolas, 'Courier New', monospace;font-size:14px;font-weight:normal;line-height:19px;white-space:pre;">
                <div><span style="color:#abb2bf;">@</span><span style="color:#e5c07b;">RestController</span></div>
                <div><span style="color:#abb2bf;">@</span><span style="color:#e5c07b;">RequestMapping</span><span style="color:#e06c75;">(</span><span style="color:#98c379;">"/error"</span><span style="color:#e06c75;">)</span></div>
                <div><span style="color:#c678dd;">public</span><span style="color:#e06c75;"> </span><span style="color:#c678dd;">class</span><span style="color:#e06c75;"> </span><span style="color:#e5c07b;">CustomErrorController</span><span style="color:#e06c75;"> </span><span style="color:#c678dd;">implements</span><span style="color:#e06c75;"> </span><span style="color:#e5c07b;">ErrorController</span><span style="color:#e06c75;"> </span><span style="color:#abb2bf;">{</span></div>
            </div>
            <p>&nbsp;</p>
            <div style="background-color:#23272e;color:#abb2bf;font-family:Consolas, 'Courier New', monospace;font-size:14px;font-weight:normal;line-height:19px;white-space:pre;">
                <div><span style="color:#e06c75;">&nbsp; &nbsp; </span><span style="color:#abb2bf;">@</span><span style="color:#e5c07b;">RequestMapping</span></div>
                <div><span style="color:#e06c75;">&nbsp; &nbsp; </span><span style="color:#c678dd;">public</span><span style="color:#61afef;"> </span><span style="color:#e5c07b;">ResponseEntity</span><span style="color:#abb2bf;">&lt;</span><span style="color:#e5c07b;">CustomHttpResponse</span><span style="color:#abb2bf;">&gt;</span><span style="color:#61afef;"> handleError</span><span style="color:#abb2bf;">(</span><span style="color:#e5c07b;">HttpServletRequest</span><span style="color:#abb2bf;"> </span><span style="color:#e06c75;">request</span><span style="color:#abb2bf;">)</span><span style="color:#61afef;"> </span><span style="color:#abb2bf;">{</span></div>
                <div><span style="color:#abb2bf;">&nbsp; &nbsp; &nbsp; &nbsp; </span><span style="color:#e5c07b;">CustomHttpResponse</span><span style="color:#abb2bf;"> </span><span style="color:#e06c75;">errorDetails</span><span style="color:#abb2bf;"> </span><span style="color:#56b6c2;">=</span><span style="color:#abb2bf;"> </span><span style="color:#c678dd;">new</span><span style="color:#abb2bf;"> </span><span style="color:#61afef;">CustomHttpResponse</span><span style="color:#abb2bf;">();</span></div>
                <div><span style="color:#abb2bf;">&nbsp; &nbsp; &nbsp; &nbsp; </span><span style="color:#e5c07b;">errorDetails</span><span style="color:#abb2bf;">.</span><span style="color:#61afef;">setTimestamp</span><span style="color:#abb2bf;">(</span><span style="color:#e5c07b;">LocalDateTime</span><span style="color:#abb2bf;">.</span><span style="color:#61afef;">now</span><span style="color:#abb2bf;">());</span></div>
            </div>
            <p>&nbsp;</p>
            <div style="background-color:#23272e;color:#abb2bf;font-family:Consolas, 'Courier New', monospace;font-size:14px;font-weight:normal;line-height:19px;white-space:pre;">
                <div><span style="color:#abb2bf;">&nbsp; &nbsp; &nbsp; &nbsp; </span><span style="color:#e5c07b;">Integer</span><span style="color:#abb2bf;"> </span><span style="color:#e06c75;">statusCode</span><span style="color:#abb2bf;"> </span><span style="color:#56b6c2;">=</span><span style="color:#abb2bf;"> (Integer) </span><span style="color:#e5c07b;">request</span><span style="color:#abb2bf;">.</span><span style="color:#61afef;">getAttribute</span><span style="color:#abb2bf;">(</span><span style="color:#e5c07b;">RequestDispatcher</span><span style="color:#abb2bf;">.</span><span style="color:#e5c07b;">ERROR_STATUS_CODE</span><span style="color:#abb2bf;">);</span></div>
                <div><span style="color:#abb2bf;">&nbsp; &nbsp; &nbsp; &nbsp; </span><span style="color:#e5c07b;">String</span><span style="color:#abb2bf;"> </span><span style="color:#e06c75;">message</span><span style="color:#abb2bf;"> </span><span style="color:#56b6c2;">=</span><span style="color:#abb2bf;"> (String) </span><span style="color:#e5c07b;">request</span><span style="color:#abb2bf;">.</span><span style="color:#61afef;">getAttribute</span><span style="color:#abb2bf;">(</span><span style="color:#e5c07b;">RequestDispatcher</span><span style="color:#abb2bf;">.</span><span style="color:#e5c07b;">ERROR_MESSAGE</span><span style="color:#abb2bf;">);</span></div>
            </div>
            <p>&nbsp;</p>
            <div style="background-color:#23272e;color:#abb2bf;font-family:Consolas, 'Courier New', monospace;font-size:14px;font-weight:normal;line-height:19px;white-space:pre;">
                <div><span style="color:#abb2bf;">&nbsp; &nbsp; &nbsp; &nbsp; </span><span style="color:#c678dd;">if</span><span style="color:#abb2bf;"> (statusCode </span><span style="color:#56b6c2;">==</span><span style="color:#abb2bf;"> </span><span style="color:#d19a66;">null</span><span style="color:#abb2bf;">) {</span></div>
                <div><span style="color:#abb2bf;">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; statusCode </span><span style="color:#56b6c2;">=</span><span style="color:#abb2bf;"> </span><span style="color:#e5c07b;">HttpStatus</span><span style="color:#abb2bf;">.</span><span style="color:#e5c07b;">INTERNAL_SERVER_ERROR</span><span style="color:#abb2bf;">.</span><span style="color:#61afef;">value</span><span style="color:#abb2bf;">();</span></div>
                <div><span style="color:#abb2bf;">&nbsp; &nbsp; &nbsp; &nbsp; } </span></div>
                <div><span style="color:#abb2bf;">&nbsp; &nbsp; &nbsp; &nbsp; </span></div>
                <div><span style="color:#abb2bf;">&nbsp; &nbsp; &nbsp; &nbsp; </span><span style="color:#c678dd;">if</span><span style="color:#abb2bf;"> (statusCode </span><span style="color:#56b6c2;">==</span><span style="color:#abb2bf;"> </span><span style="color:#e5c07b;">HttpStatus</span><span style="color:#abb2bf;">.</span><span style="color:#e5c07b;">NOT_FOUND</span><span style="color:#abb2bf;">.</span><span style="color:#61afef;">value</span><span style="color:#abb2bf;">()) {</span></div>
                <div><span style="color:#abb2bf;">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; message </span><span style="color:#56b6c2;">=</span><span style="color:#abb2bf;"> </span><span style="color:#98c379;">"The requested resource was not found"</span><span style="color:#abb2bf;">;</span></div>
                <div><span style="color:#abb2bf;">&nbsp; &nbsp; &nbsp; &nbsp; } </span><span style="color:#c678dd;">else</span><span style="color:#abb2bf;"> </span><span style="color:#c678dd;">if</span><span style="color:#abb2bf;"> (statusCode </span><span style="color:#56b6c2;">==</span><span style="color:#abb2bf;"> </span><span style="color:#e5c07b;">HttpStatus</span><span style="color:#abb2bf;">.</span><span style="color:#e5c07b;">FORBIDDEN</span><span style="color:#abb2bf;">.</span><span style="color:#61afef;">value</span><span style="color:#abb2bf;">()) {</span></div>
                <div><span style="color:#abb2bf;">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; message </span><span style="color:#56b6c2;">=</span><span style="color:#abb2bf;"> </span><span style="color:#98c379;">"You don't have permission to access this resource"</span><span style="color:#abb2bf;">;</span></div>
                <div><span style="color:#abb2bf;">&nbsp; &nbsp; &nbsp; &nbsp; } </span><span style="color:#c678dd;">else</span><span style="color:#abb2bf;"> </span><span style="color:#c678dd;">if</span><span style="color:#abb2bf;"> (statusCode </span><span style="color:#56b6c2;">==</span><span style="color:#abb2bf;"> </span><span style="color:#e5c07b;">HttpStatus</span><span style="color:#abb2bf;">.</span><span style="color:#e5c07b;">BAD_REQUEST</span><span style="color:#abb2bf;">.</span><span style="color:#61afef;">value</span><span style="color:#abb2bf;">()) {</span></div>
                <div><span style="color:#abb2bf;">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; message </span><span style="color:#56b6c2;">=</span><span style="color:#abb2bf;"> </span><span style="color:#98c379;">"The request was invalid or cannot be served"</span><span style="color:#abb2bf;">;</span></div>
                <div><span style="color:#abb2bf;">&nbsp; &nbsp; &nbsp; &nbsp; } </span><span style="color:#c678dd;">else</span><span style="color:#abb2bf;"> </span><span style="color:#c678dd;">if</span><span style="color:#abb2bf;"> (statusCode </span><span style="color:#56b6c2;">==</span><span style="color:#abb2bf;"> </span><span style="color:#e5c07b;">HttpStatus</span><span style="color:#abb2bf;">.</span><span style="color:#e5c07b;">UNAUTHORIZED</span><span style="color:#abb2bf;">.</span><span style="color:#61afef;">value</span><span style="color:#abb2bf;">()) {</span></div>
                <div><span style="color:#abb2bf;">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; message </span><span style="color:#56b6c2;">=</span><span style="color:#abb2bf;"> </span><span style="color:#98c379;">"You need to authenticate to access this resource"</span><span style="color:#abb2bf;">;</span></div>
                <div><span style="color:#abb2bf;">&nbsp; &nbsp; &nbsp; &nbsp; } </span><span style="color:#c678dd;">else</span><span style="color:#abb2bf;"> {</span></div>
                <div><span style="color:#abb2bf;">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; message </span><span style="color:#56b6c2;">=</span><span style="color:#abb2bf;"> (message </span><span style="color:#56b6c2;">!=</span><span style="color:#abb2bf;"> </span><span style="color:#d19a66;">null</span><span style="color:#abb2bf;">) </span><span style="color:#c678dd;">?</span><span style="color:#abb2bf;"> message </span><span style="color:#c678dd;">:</span><span style="color:#abb2bf;"> </span><span style="color:#98c379;">"Unexpected error occurred"</span><span style="color:#abb2bf;">;</span></div>
                <div><span style="color:#abb2bf;">&nbsp; &nbsp; &nbsp; &nbsp; }</span></div>
            </div>
            <p>&nbsp;</p>
            <div style="background-color:#23272e;color:#abb2bf;font-family:Consolas, 'Courier New', monospace;font-size:14px;font-weight:normal;line-height:19px;white-space:pre;">
                <div><span style="color:#abb2bf;">&nbsp; &nbsp; &nbsp; &nbsp; </span><span style="color:#e5c07b;">errorDetails</span><span style="color:#abb2bf;">.</span><span style="color:#61afef;">setStatusCode</span><span style="color:#abb2bf;">(statusCode);</span></div>
                <div><span style="color:#abb2bf;">&nbsp; &nbsp; &nbsp; &nbsp; </span><span style="color:#e5c07b;">errorDetails</span><span style="color:#abb2bf;">.</span><span style="color:#61afef;">setMessage</span><span style="color:#abb2bf;">(message);</span></div>
                <div><span style="color:#abb2bf;">&nbsp; &nbsp; &nbsp; &nbsp; </span><span style="color:#e5c07b;">errorDetails</span><span style="color:#abb2bf;">.</span><span style="color:#61afef;">setData</span><span style="color:#abb2bf;">(</span><span style="color:#d19a66;">null</span><span style="color:#abb2bf;">);</span></div>
            </div>
            <p>&nbsp;</p>
            <div style="background-color:#23272e;color:#abb2bf;font-family:Consolas, 'Courier New', monospace;font-size:14px;font-weight:normal;line-height:19px;white-space:pre;">
                <div><span style="color:#abb2bf;">&nbsp; &nbsp; &nbsp; &nbsp; </span><span style="color:#c678dd;">return</span><span style="color:#abb2bf;"> </span><span style="color:#c678dd;">new</span><span style="color:#abb2bf;"> </span><span style="color:#e5c07b;">ResponseEntity</span><span style="color:#abb2bf;">&lt;&gt;(errorDetails, </span><span style="color:#e5c07b;">HttpStatus</span><span style="color:#abb2bf;">.</span><span style="color:#61afef;">valueOf</span><span style="color:#abb2bf;">(statusCode));</span></div>
                <div><span style="color:#abb2bf;">&nbsp; &nbsp; }</span></div>
                <div><span style="color:#abb2bf;">}</span><br> </div>
            </div>
        </div>
    </div>
    <hr>
    <h2 data-start="2753" data-end="2781">🛠 Installation &amp; Setup</h2>
    <ol data-start="2783" data-end="3346">
        <li data-start="2783" data-end="2917">
            <p data-start="2786" data-end="2812"><strong data-start="2786" data-end="2810">Clone the repository</strong></p>
            <p data-start="2786" data-end="2812">git clone https://github.com/yoanesber/spring-boot-hibernate-one-to-many-postgresql.git<br>&nbsp;</p>
        </li>
        <li data-start="2783" data-end="2917">
            <p data-start="2786" data-end="2812"><strong data-start="2922" data-end="2966">Set up PostgreSQL&nbsp;</strong><br>Configure the PostgreSQL database connection in application.properties.<br>&nbsp;</p>
        </li>
        <li data-start="2783" data-end="2917">
            <p data-start="2786" data-end="2812"><strong data-start="3099" data-end="3147">Run the application locally</strong><br>Make sure PostgreSQL is running, then execute: <i>mvn spring-boot:run&nbsp;</i><br>The API will be available at <i>http://localhost:8081/</i>&nbsp;</p>
        </li>
    </ol>
    <hr>
    <h2 data-start="3920" data-end="3941">🔗 API Endpoints</h2>
    <p data-start="2786" data-end="2812"><strong data-start="2786" data-end="2810">Department API Endpoints:</strong></p>
    <p data-pm-slice="1 1 [&quot;list&quot;,{&quot;spread&quot;:false,&quot;start&quot;:4578,&quot;end&quot;:5100},&quot;regular_list_item&quot;,{&quot;start&quot;:4578,&quot;end&quot;:4648}]"><code><span>GET http://localhost:8081/api/v1/departments</span></code><span> - Get all departments</span></p>
    <p data-pm-slice="1 1 [&quot;list&quot;,{&quot;spread&quot;:false,&quot;start&quot;:4578,&quot;end&quot;:5100},&quot;regular_list_item&quot;,{&quot;start&quot;:4578,&quot;end&quot;:4648}]">&nbsp;</p>
    <p data-pm-slice="1 1 [&quot;list&quot;,{&quot;spread&quot;:false,&quot;start&quot;:4578,&quot;end&quot;:5100},&quot;regular_list_item&quot;,{&quot;start&quot;:4649,&quot;end&quot;:4839}]"><code><span>POST http://localhost:8081/api/v1/departments</span></code> - Create a new department with body request:</p>
    <div style="background-color:#23272e;color:#abb2bf;font-family:Consolas, 'Courier New', monospace;font-size:14px;font-weight:normal;line-height:19px;white-space:pre;">
        <div style="background-color:#23272e;color:#abb2bf;font-family:Consolas, 'Courier New', monospace;font-size:14px;font-weight:normal;line-height:19px;white-space:pre;">
            <div style="background-color:#212121;color:#f8f8f2;font-family:IBMPlexMono, 'Courier New', monospace, Consolas, 'Courier New', monospace;font-size:12px;font-weight:normal;line-height:18px;white-space:pre;">
                <div><span style="color:#dcdcdc;">{</span></div>
                <div><span style="color:#f8f8f2;">&nbsp; &nbsp; </span><span style="color:#9cdcfe;">"id"</span><span style="color:#dcdcdc;">:</span><span style="color:#f8f8f2;"> </span><span style="color:#ce9178;">"d011"</span><span style="color:#dcdcdc;">,</span></div>
                <div><span style="color:#f8f8f2;">&nbsp; &nbsp; </span><span style="color:#9cdcfe;">"deptName"</span><span style="color:#dcdcdc;">:</span><span style="color:#f8f8f2;"> </span><span style="color:#ce9178;">"Security"</span><span style="color:#dcdcdc;">,</span></div>
                <div><span style="color:#f8f8f2;">&nbsp; &nbsp; </span><span style="color:#9cdcfe;">"active"</span><span style="color:#dcdcdc;">:</span><span style="color:#f8f8f2;"> </span><span style="color:#ce9178;">"true"</span><span style="color:#dcdcdc;">,</span></div>
                <div><span style="color:#f8f8f2;">&nbsp; &nbsp; </span><span style="color:#9cdcfe;">"createdBy"</span><span style="color:#dcdcdc;">:</span><span style="color:#f8f8f2;"> </span><span style="color:#b5cea8;">1</span><span style="color:#dcdcdc;">,</span></div>
                <div><span style="color:#f8f8f2;">&nbsp; &nbsp; </span><span style="color:#9cdcfe;">"updatedBy"</span><span style="color:#dcdcdc;">:</span><span style="color:#f8f8f2;"> </span><span style="color:#b5cea8;">1</span></div>
                <div><span style="color:#dcdcdc;">}</span></div>
            </div>
        </div>
    </div>
    <p>&nbsp;</p>
    <p data-pm-slice="1 1 [&quot;list&quot;,{&quot;spread&quot;:false,&quot;start&quot;:4578,&quot;end&quot;:5100},&quot;regular_list_item&quot;,{&quot;start&quot;:4840,&quot;end&quot;:5014}]"><code><span>PUT http://localhost:8081/api/v1/departments/d011</span></code> - Update existing department with body request:</p>
    <div style="background-color:#23272e;color:#abb2bf;font-family:Consolas, 'Courier New', monospace;font-size:14px;font-weight:normal;line-height:19px;white-space:pre;">
        <div style="background-color:#23272e;color:#abb2bf;font-family:Consolas, 'Courier New', monospace;font-size:14px;font-weight:normal;line-height:19px;white-space:pre;">
            <div style="background-color:#212121;color:#f8f8f2;font-family:IBMPlexMono, 'Courier New', monospace, Consolas, 'Courier New', monospace;font-size:12px;font-weight:normal;line-height:18px;white-space:pre;">
                <div><span style="color:#dcdcdc;">{</span></div>
                <div><span style="color:#f8f8f2;">&nbsp; &nbsp; </span><span style="color:#9cdcfe;">"id"</span><span style="color:#dcdcdc;">:</span><span style="color:#f8f8f2;"> </span><span style="color:#ce9178;">"d011"</span><span style="color:#dcdcdc;">,</span></div>
                <div><span style="color:#f8f8f2;">&nbsp; &nbsp; </span><span style="color:#9cdcfe;">"deptName"</span><span style="color:#dcdcdc;">:</span><span style="color:#f8f8f2;"> </span><span style="color:#ce9178;">"Operation"</span><span style="color:#dcdcdc;">,</span></div>
                <div><span style="color:#f8f8f2;">&nbsp; &nbsp; </span><span style="color:#9cdcfe;">"active"</span><span style="color:#dcdcdc;">:</span><span style="color:#f8f8f2;"> </span><span style="color:#ce9178;">"false"</span><span style="color:#dcdcdc;">,</span></div>
                <div><span style="color:#f8f8f2;">&nbsp; &nbsp; </span><span style="color:#9cdcfe;">"updatedBy"</span><span style="color:#dcdcdc;">:</span><span style="color:#f8f8f2;"> </span><span style="color:#b5cea8;">2</span></div>
                <div><span style="color:#dcdcdc;">}</span></div>
            </div>
        </div>
    </div>
    <p>&nbsp;</p>
    <p data-pm-slice="1 1 [&quot;list&quot;,{&quot;spread&quot;:false,&quot;start&quot;:4578,&quot;end&quot;:5100},&quot;regular_list_item&quot;,{&quot;start&quot;:5015,&quot;end&quot;:5100}]"><code><span>DELETE http://localhost:8081/api/v1/departments/d011</span></code><span> - Delete existing department</span></p>
    <p data-pm-slice="1 1 [&quot;list&quot;,{&quot;spread&quot;:false,&quot;start&quot;:4578,&quot;end&quot;:5100},&quot;regular_list_item&quot;,{&quot;start&quot;:5015,&quot;end&quot;:5100}]">&nbsp;</p>
    <p data-start="2786" data-end="2812"><strong data-start="2786" data-end="2810">Employee API Endpoints:</strong></p>
    <p data-pm-slice="1 1 [&quot;list&quot;,{&quot;spread&quot;:false,&quot;start&quot;:5135,&quot;end&quot;:6162},&quot;regular_list_item&quot;,{&quot;start&quot;:5135,&quot;end&quot;:5201}]"><code><span>GET http://localhost:8081/api/v1/employees</span></code><span> - Get all employees</span></p>
    <p data-pm-slice="1 1 [&quot;list&quot;,{&quot;spread&quot;:false,&quot;start&quot;:5135,&quot;end&quot;:6162},&quot;regular_list_item&quot;,{&quot;start&quot;:5135,&quot;end&quot;:5201}]">&nbsp;</p>
    <p data-pm-slice="1 1 [&quot;list&quot;,{&quot;spread&quot;:false,&quot;start&quot;:5135,&quot;end&quot;:6162},&quot;regular_list_item&quot;,{&quot;start&quot;:5202,&quot;end&quot;:5280}]"><code><span>GET http://localhost:8081/api/v1/employees/10036</span></code><span> - Get a specific employee</span></p>
    <p data-pm-slice="1 1 [&quot;list&quot;,{&quot;spread&quot;:false,&quot;start&quot;:4578,&quot;end&quot;:5100},&quot;regular_list_item&quot;,{&quot;start&quot;:4578,&quot;end&quot;:4648}]">&nbsp;</p>
    <p data-pm-slice="1 1 [&quot;list&quot;,{&quot;spread&quot;:false,&quot;start&quot;:5135,&quot;end&quot;:6162},&quot;regular_list_item&quot;,{&quot;start&quot;:5281,&quot;end&quot;:5718}]"><code><span>POST http://localhost:8081/api/v1/employees</span></code> - Create a new employees with body request:</p>
    <div style="background-color:#23272e;color:#abb2bf;font-family:Consolas, 'Courier New', monospace;font-size:14px;font-weight:normal;line-height:19px;white-space:pre;">
        <div style="background-color:#23272e;color:#abb2bf;font-family:Consolas, 'Courier New', monospace;font-size:14px;font-weight:normal;line-height:19px;white-space:pre;">
            <div style="background-color:#212121;color:#f8f8f2;font-family:IBMPlexMono, 'Courier New', monospace, Consolas, 'Courier New', monospace;font-size:12px;font-weight:normal;line-height:18px;white-space:pre;">
                <div style="background-color:#212121;color:#f8f8f2;font-family:IBMPlexMono, 'Courier New', monospace, Consolas, 'Courier New', monospace;font-size:12px;font-weight:normal;line-height:18px;white-space:pre;">
                    <div><span style="color:#dcdcdc;">{</span></div>
                    <div><span style="color:#f8f8f2;">&nbsp; &nbsp; </span><span style="color:#9cdcfe;">"birthDate"</span><span style="color:#dcdcdc;">:</span><span style="color:#f8f8f2;"> </span><span style="color:#ce9178;">"1953-09-02"</span><span style="color:#dcdcdc;">,</span></div>
                    <div><span style="color:#f8f8f2;">&nbsp; &nbsp; </span><span style="color:#9cdcfe;">"firstName"</span><span style="color:#dcdcdc;">:</span><span style="color:#f8f8f2;"> </span><span style="color:#ce9178;">"YOANES"</span><span style="color:#dcdcdc;">,</span></div>
                    <div><span style="color:#f8f8f2;">&nbsp; &nbsp; </span><span style="color:#9cdcfe;">"lastName"</span><span style="color:#dcdcdc;">:</span><span style="color:#f8f8f2;"> </span><span style="color:#ce9178;">"BERCHMANS"</span><span style="color:#dcdcdc;">,</span></div>
                    <div><span style="color:#f8f8f2;">&nbsp; &nbsp; </span><span style="color:#9cdcfe;">"gender"</span><span style="color:#dcdcdc;">:</span><span style="color:#f8f8f2;"> </span><span style="color:#ce9178;">"M"</span><span style="color:#dcdcdc;">,</span></div>
                    <div><span style="color:#f8f8f2;">&nbsp; &nbsp; </span><span style="color:#9cdcfe;">"hireDate"</span><span style="color:#dcdcdc;">:</span><span style="color:#f8f8f2;"> </span><span style="color:#ce9178;">"1986-06-27"</span><span style="color:#dcdcdc;">,</span></div>
                    <div><span style="color:#f8f8f2;">&nbsp; &nbsp; </span><span style="color:#9cdcfe;">"activeStatus"</span><span style="color:#dcdcdc;">:</span><span style="color:#f8f8f2;"> </span><span style="color:#ce9178;">true</span><span style="color:#dcdcdc;">,</span></div>
                    <div><span style="color:#f8f8f2;">&nbsp; &nbsp; </span><span style="color:#9cdcfe;">"createdBy"</span><span style="color:#dcdcdc;">:</span><span style="color:#f8f8f2;"> </span><span style="color:#b5cea8;">1</span><span style="color:#dcdcdc;">,</span></div>
                    <div><span style="color:#f8f8f2;">&nbsp; &nbsp; </span><span style="color:#9cdcfe;">"updatedBy"</span><span style="color:#dcdcdc;">:</span><span style="color:#f8f8f2;"> </span><span style="color:#b5cea8;">1</span><span style="color:#dcdcdc;">,</span></div>
                    <div><span style="color:#f8f8f2;">&nbsp; &nbsp; </span><span style="color:#9cdcfe;">"departments"</span><span style="color:#dcdcdc;">:</span><span style="color:#f8f8f2;"> </span><span style="color:#dcdcdc;">[</span></div>
                    <div><span style="color:#f8f8f2;">&nbsp; &nbsp; &nbsp; &nbsp; </span><span style="color:#dcdcdc;">{</span></div>
                    <div><span style="color:#f8f8f2;">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; </span><span style="color:#9cdcfe;">"departmentId"</span><span style="color:#dcdcdc;">:</span><span style="color:#f8f8f2;"> </span><span style="color:#ce9178;">"d002"</span><span style="color:#dcdcdc;">,</span></div>
                    <div><span style="color:#f8f8f2;">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; </span><span style="color:#9cdcfe;">"fromDate"</span><span style="color:#dcdcdc;">:</span><span style="color:#f8f8f2;"> </span><span style="color:#ce9178;">"2024-01-01"</span><span style="color:#dcdcdc;">,</span></div>
                    <div><span style="color:#f8f8f2;">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; </span><span style="color:#9cdcfe;">"toDate"</span><span style="color:#dcdcdc;">:</span><span style="color:#f8f8f2;"> </span><span style="color:#ce9178;">"2024-01-31"</span></div>
                    <div><span style="color:#f8f8f2;">&nbsp; &nbsp; &nbsp; &nbsp; </span><span style="color:#dcdcdc;">},</span></div>
                    <div><span style="color:#f8f8f2;">&nbsp; &nbsp; &nbsp; &nbsp; </span><span style="color:#dcdcdc;">{</span></div>
                    <div><span style="color:#f8f8f2;">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; </span><span style="color:#9cdcfe;">"departmentId"</span><span style="color:#dcdcdc;">:</span><span style="color:#f8f8f2;"> </span><span style="color:#ce9178;">"d001"</span><span style="color:#dcdcdc;">,</span></div>
                    <div><span style="color:#f8f8f2;">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; </span><span style="color:#9cdcfe;">"fromDate"</span><span style="color:#dcdcdc;">:</span><span style="color:#f8f8f2;"> </span><span style="color:#ce9178;">"2024-02-01"</span><span style="color:#dcdcdc;">,</span></div>
                    <div><span style="color:#f8f8f2;">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; </span><span style="color:#9cdcfe;">"toDate"</span><span style="color:#dcdcdc;">:</span><span style="color:#f8f8f2;"> </span><span style="color:#ce9178;">"2024-02-29"</span></div>
                    <div><span style="color:#f8f8f2;">&nbsp; &nbsp; &nbsp; &nbsp; </span><span style="color:#dcdcdc;">}</span></div>
                    <div><span style="color:#f8f8f2;">&nbsp; &nbsp; </span><span style="color:#dcdcdc;">],</span></div>
                    <div><span style="color:#f8f8f2;">&nbsp; &nbsp; </span><span style="color:#9cdcfe;">"salaries"</span><span style="color:#dcdcdc;">:</span><span style="color:#f8f8f2;"> </span><span style="color:#dcdcdc;">[</span></div>
                    <div><span style="color:#f8f8f2;">&nbsp; &nbsp; &nbsp; &nbsp; </span><span style="color:#dcdcdc;">{</span></div>
                    <div><span style="color:#f8f8f2;">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; </span><span style="color:#9cdcfe;">"fromDate"</span><span style="color:#dcdcdc;">:</span><span style="color:#f8f8f2;"> </span><span style="color:#ce9178;">"1986-06-26"</span><span style="color:#dcdcdc;">,</span></div>
                    <div><span style="color:#f8f8f2;">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; </span><span style="color:#9cdcfe;">"amount"</span><span style="color:#dcdcdc;">:</span><span style="color:#f8f8f2;"> </span><span style="color:#b5cea8;">60116</span><span style="color:#dcdcdc;">,</span></div>
                    <div><span style="color:#f8f8f2;">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; </span><span style="color:#9cdcfe;">"toDate"</span><span style="color:#dcdcdc;">:</span><span style="color:#f8f8f2;"> </span><span style="color:#ce9178;">"1987-06-27"</span></div>
                    <div><span style="color:#f8f8f2;">&nbsp; &nbsp; &nbsp; &nbsp; </span><span style="color:#dcdcdc;">}</span></div>
                    <div><span style="color:#f8f8f2;">&nbsp; &nbsp; </span><span style="color:#dcdcdc;">],</span></div>
                    <div><span style="color:#f8f8f2;">&nbsp; &nbsp; </span><span style="color:#9cdcfe;">"titles"</span><span style="color:#dcdcdc;">:</span><span style="color:#f8f8f2;"> </span><span style="color:#dcdcdc;">[</span></div>
                    <div><span style="color:#f8f8f2;">&nbsp; &nbsp; &nbsp; &nbsp; </span><span style="color:#dcdcdc;">{</span></div>
                    <div><span style="color:#f8f8f2;">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; </span><span style="color:#9cdcfe;">"title"</span><span style="color:#dcdcdc;">:</span><span style="color:#f8f8f2;"> </span><span style="color:#ce9178;">"Senior Engineer"</span><span style="color:#dcdcdc;">,</span></div>
                    <div><span style="color:#f8f8f2;">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; </span><span style="color:#9cdcfe;">"fromDate"</span><span style="color:#dcdcdc;">:</span><span style="color:#f8f8f2;"> </span><span style="color:#ce9178;">"1986-06-26"</span><span style="color:#dcdcdc;">,</span></div>
                    <div><span style="color:#f8f8f2;">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; </span><span style="color:#9cdcfe;">"toDate"</span><span style="color:#dcdcdc;">:</span><span style="color:#f8f8f2;"> </span><span style="color:#ce9178;">"1986-06-30"</span></div>
                    <div><span style="color:#f8f8f2;">&nbsp; &nbsp; &nbsp; &nbsp; </span><span style="color:#dcdcdc;">},</span></div>
                    <div><span style="color:#f8f8f2;">&nbsp; &nbsp; &nbsp; &nbsp; </span><span style="color:#dcdcdc;">{</span></div>
                    <div><span style="color:#f8f8f2;">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; </span><span style="color:#9cdcfe;">"title"</span><span style="color:#dcdcdc;">:</span><span style="color:#f8f8f2;"> </span><span style="color:#ce9178;">"Engineer Manager"</span><span style="color:#dcdcdc;">,</span></div>
                    <div><span style="color:#f8f8f2;">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; </span><span style="color:#9cdcfe;">"fromDate"</span><span style="color:#dcdcdc;">:</span><span style="color:#f8f8f2;"> </span><span style="color:#ce9178;">"1986-07-01"</span><span style="color:#dcdcdc;">,</span></div>
                    <div><span style="color:#f8f8f2;">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; </span><span style="color:#9cdcfe;">"toDate"</span><span style="color:#dcdcdc;">:</span><span style="color:#f8f8f2;"> </span><span style="color:#ce9178;">"1986-07-31"</span></div>
                    <div><span style="color:#f8f8f2;">&nbsp; &nbsp; &nbsp; &nbsp; </span><span style="color:#dcdcdc;">}</span></div>
                    <div><span style="color:#f8f8f2;">&nbsp; &nbsp; </span><span style="color:#dcdcdc;">]</span></div>
                    <div><span style="color:#dcdcdc;">}</span></div>
                </div>
            </div>
        </div>
    </div>
    <p>&nbsp;</p>
    <p data-pm-slice="1 1 [&quot;list&quot;,{&quot;spread&quot;:false,&quot;start&quot;:5135,&quot;end&quot;:6162},&quot;regular_list_item&quot;,{&quot;start&quot;:5719,&quot;end&quot;:6085}]"><code><span>PUT http://localhost:8081/api/v1/employees/10036</span></code> - Update existing employee with body request:</p>
    <div style="background-color:#23272e;color:#abb2bf;font-family:Consolas, 'Courier New', monospace;font-size:14px;font-weight:normal;line-height:19px;white-space:pre;">
        <div style="background-color:#23272e;color:#abb2bf;font-family:Consolas, 'Courier New', monospace;font-size:14px;font-weight:normal;line-height:19px;white-space:pre;">
            <div style="background-color:#212121;color:#f8f8f2;font-family:IBMPlexMono, 'Courier New', monospace, Consolas, 'Courier New', monospace;font-size:12px;font-weight:normal;line-height:18px;white-space:pre;">
                <div style="background-color:#212121;color:#f8f8f2;font-family:IBMPlexMono, 'Courier New', monospace, Consolas, 'Courier New', monospace;font-size:12px;font-weight:normal;line-height:18px;white-space:pre;">
                    <div><span style="color:#dcdcdc;">{</span></div>
                    <div><span style="color:#f8f8f2;">&nbsp; &nbsp; </span><span style="color:#9cdcfe;">"birthDate"</span><span style="color:#dcdcdc;">:</span><span style="color:#f8f8f2;"> </span><span style="color:#ce9178;">"1953-09-02"</span><span style="color:#dcdcdc;">,</span></div>
                    <div><span style="color:#f8f8f2;">&nbsp; &nbsp; </span><span style="color:#9cdcfe;">"firstName"</span><span style="color:#dcdcdc;">:</span><span style="color:#f8f8f2;"> </span><span style="color:#ce9178;">"YOANES"</span><span style="color:#dcdcdc;">,</span></div>
                    <div><span style="color:#f8f8f2;">&nbsp; &nbsp; </span><span style="color:#9cdcfe;">"lastName"</span><span style="color:#dcdcdc;">:</span><span style="color:#f8f8f2;"> </span><span style="color:#ce9178;">"BERCHMANS"</span><span style="color:#dcdcdc;">,</span></div>
                    <div><span style="color:#f8f8f2;">&nbsp; &nbsp; </span><span style="color:#9cdcfe;">"gender"</span><span style="color:#dcdcdc;">:</span><span style="color:#f8f8f2;"> </span><span style="color:#ce9178;">"M"</span><span style="color:#dcdcdc;">,</span></div>
                    <div><span style="color:#f8f8f2;">&nbsp; &nbsp; </span><span style="color:#9cdcfe;">"hireDate"</span><span style="color:#dcdcdc;">:</span><span style="color:#f8f8f2;"> </span><span style="color:#ce9178;">"1986-07-27"</span><span style="color:#dcdcdc;">,</span></div>
                    <div><span style="color:#f8f8f2;">&nbsp; &nbsp; </span><span style="color:#9cdcfe;">"activeStatus"</span><span style="color:#dcdcdc;">:</span><span style="color:#f8f8f2;"> </span><span style="color:#ce9178;">true</span><span style="color:#dcdcdc;">,</span></div>
                    <div><span style="color:#f8f8f2;">&nbsp; &nbsp; </span><span style="color:#9cdcfe;">"updatedBy"</span><span style="color:#dcdcdc;">:</span><span style="color:#f8f8f2;"> </span><span style="color:#b5cea8;">2</span><span style="color:#dcdcdc;">,</span></div>
                    <div><span style="color:#f8f8f2;">&nbsp; &nbsp; </span><span style="color:#9cdcfe;">"departments"</span><span style="color:#dcdcdc;">:</span><span style="color:#f8f8f2;"> </span><span style="color:#dcdcdc;">[</span></div>
                    <div><span style="color:#f8f8f2;">&nbsp; &nbsp; &nbsp; &nbsp; </span><span style="color:#dcdcdc;">{</span></div>
                    <div><span style="color:#f8f8f2;">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; </span><span style="color:#9cdcfe;">"departmentId"</span><span style="color:#dcdcdc;">:</span><span style="color:#f8f8f2;"> </span><span style="color:#ce9178;">"d002"</span><span style="color:#dcdcdc;">,</span></div>
                    <div><span style="color:#f8f8f2;">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; </span><span style="color:#9cdcfe;">"fromDate"</span><span style="color:#dcdcdc;">:</span><span style="color:#f8f8f2;"> </span><span style="color:#ce9178;">"2024-01-01"</span><span style="color:#dcdcdc;">,</span></div>
                    <div><span style="color:#f8f8f2;">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; </span><span style="color:#9cdcfe;">"toDate"</span><span style="color:#dcdcdc;">:</span><span style="color:#f8f8f2;"> </span><span style="color:#ce9178;">"2024-01-31"</span></div>
                    <div><span style="color:#f8f8f2;">&nbsp; &nbsp; &nbsp; &nbsp; </span><span style="color:#dcdcdc;">}</span></div>
                    <div><span style="color:#f8f8f2;">&nbsp; &nbsp; </span><span style="color:#dcdcdc;">],</span></div>
                    <div><span style="color:#f8f8f2;">&nbsp; &nbsp; </span><span style="color:#9cdcfe;">"salaries"</span><span style="color:#dcdcdc;">:</span><span style="color:#f8f8f2;"> </span><span style="color:#dcdcdc;">[</span></div>
                    <div><span style="color:#f8f8f2;">&nbsp; &nbsp; &nbsp; &nbsp; </span><span style="color:#dcdcdc;">{</span></div>
                    <div><span style="color:#f8f8f2;">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; </span><span style="color:#9cdcfe;">"fromDate"</span><span style="color:#dcdcdc;">:</span><span style="color:#f8f8f2;"> </span><span style="color:#ce9178;">"1986-06-26"</span><span style="color:#dcdcdc;">,</span></div>
                    <div><span style="color:#f8f8f2;">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; </span><span style="color:#9cdcfe;">"amount"</span><span style="color:#dcdcdc;">:</span><span style="color:#f8f8f2;"> </span><span style="color:#b5cea8;">60116</span><span style="color:#dcdcdc;">,</span></div>
                    <div><span style="color:#f8f8f2;">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; </span><span style="color:#9cdcfe;">"toDate"</span><span style="color:#dcdcdc;">:</span><span style="color:#f8f8f2;"> </span><span style="color:#ce9178;">"1987-06-27"</span></div>
                    <div><span style="color:#f8f8f2;">&nbsp; &nbsp; &nbsp; &nbsp; </span><span style="color:#dcdcdc;">}</span></div>
                    <div><span style="color:#f8f8f2;">&nbsp; &nbsp; </span><span style="color:#dcdcdc;">],</span></div>
                    <div><span style="color:#f8f8f2;">&nbsp; &nbsp; </span><span style="color:#9cdcfe;">"titles"</span><span style="color:#dcdcdc;">:</span><span style="color:#f8f8f2;"> </span><span style="color:#dcdcdc;">[</span></div>
                    <div><span style="color:#f8f8f2;">&nbsp; &nbsp; &nbsp; &nbsp; </span><span style="color:#dcdcdc;">{</span></div>
                    <div><span style="color:#f8f8f2;">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; </span><span style="color:#9cdcfe;">"title"</span><span style="color:#dcdcdc;">:</span><span style="color:#f8f8f2;"> </span><span style="color:#ce9178;">"Senior Engineer"</span><span style="color:#dcdcdc;">,</span></div>
                    <div><span style="color:#f8f8f2;">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; </span><span style="color:#9cdcfe;">"fromDate"</span><span style="color:#dcdcdc;">:</span><span style="color:#f8f8f2;"> </span><span style="color:#ce9178;">"1986-06-26"</span><span style="color:#dcdcdc;">,</span></div>
                    <div><span style="color:#f8f8f2;">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; </span><span style="color:#9cdcfe;">"toDate"</span><span style="color:#dcdcdc;">:</span><span style="color:#f8f8f2;"> </span><span style="color:#ce9178;">"1986-06-30"</span></div>
                    <div><span style="color:#f8f8f2;">&nbsp; &nbsp; &nbsp; &nbsp; </span><span style="color:#dcdcdc;">}</span></div>
                    <div><span style="color:#f8f8f2;">&nbsp; &nbsp; </span><span style="color:#dcdcdc;">]</span></div>
                    <div><span style="color:#dcdcdc;">}</span></div>
                </div>
            </div>
        </div>
    </div>
    <p>&nbsp;</p>
    <p data-pm-slice="1 1 [&quot;list&quot;,{&quot;spread&quot;:false,&quot;start&quot;:5135,&quot;end&quot;:6162},&quot;regular_list_item&quot;,{&quot;start&quot;:6086,&quot;end&quot;:6162}]"><code><span>DELETE http://localhost:8081/api/v1/employees/10037</span></code><span> - Delete an employee</span></p>
</div>