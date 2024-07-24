# Group 2 Documentation

## Summary of the App

The **Record Shop Database App** is designed to manage purchases and records in a music store.

The application allows users to search for stock and make a purchase. It also stores more specific details about the records such as:
- Record Id
- Name
- Artist
- Quantity
- Price

For the purchase, it stores: 
- Purchase Id
- Customer name 
- Record Id 
- Price 
- Date 

### Application Requirements

Minimal Viable Product: 

1. **Purchase Management:**
   - Ability to create, read, and update purchases.
   - Each purchase must include:
     - Customer Name
     - Item ID
     - Price (with optional discount applied)
     - Purchase Date


2. **Record Management:**
   - Ability to create, read, and update records.
   - Each record must include:
     - Record name
     - Artist name
     - Quantity
     - Price


3. **Data Persistence:**
   - Use JPA with a relational database to store purchases and records.


4. **RESTful API:**
   - Provide endpoints to manage purchases and records.

---
## Stretch goals: 

- Add in a Service layer to manage business logic 
- Integrate Flyway to allow database management within IntelliJ
- Set up a CI/CD pipeline with Github actions

---

## How to Achieve These

### Technologies and Tools

- **Java:** Main programming language that will be utilised
- **Spring Boot / Spring Web:** Framework to create RESTful services easily.
- **Lombok:** Automatically generates common code like getters, setters, and constructors. Will reduce the amount of boilerplate code, thus making the code appear cleaner.
- **JDBC API:** It provides methods for running SQL queries, updating data, and managing connections to a database.
- **Spring Data JPA:** A project within the Spring ecosystem that simplifies working with databases. It builds on top of JPA (Java Persistence API) and helps in minimising code writing.
- **Flyway Migration:** Tool for managing changes to the database schema. It helps in applying updates and track which changes have been made, ensuring the database stays in sync across different environments.
- **Flyway-MySQL:** Allows Flyway to talk to MySQL databases. 
- **MySQL Driver:** Allows Java applications talk to MySQL databases. It handles the connection between the Java code and the MySQL database.
- **Mockito:** Java library used for unit testing.  It creates mock objects, which are dummy versions of real objects used to test specific parts of one's code.
- **POSTMAN / SwaggerUI**: Tools that can be used to test APIs and their responsiveness.

### Steps We Need to Implement:

1. **Define Entities:** Create JPA entities for `Purchase` and `Record`, including all required attributes and annotations.
2. **Create Repositories:** Set up repository interfaces for `Purchase` and `Record` to perform most of the CRUD operations.
3. **Develop Services:** Build service layers to handle the business logic for managing purchases and records.
4. **Create Controllers:** Set up RESTful endpoints that allow interaction with the service layer.
5. **Testing:** Write unit and integration tests to verify that the application functions correctly.

### Team roles: 

**Fabiola:** Will develop the `/record` endpoint, appropriate testing and docker 
**Rachel:** Will develop the `/purchase` endpoint, appropriate testing and pipeline 
**Alyssa:** Will develop documentation for the project, as well as reviewing code 

---

## Directory Structure

```

cfg_mastersplus_java_group_two [group-project]
├── .github
├── .idea
├── .mvn
├── documentation
│   ├── appFlowchart.md
│   ├── deploymentPlan.md
│   ├── manualTestPlan.md
│   ├── overview.md
│   ├── requirementsPlan.md
│   ├── testCoverage.png
│    └──userStories.md
├── src
│   ├── main
│   │   ├── java
│   │   │    └── com.example.group.project
│   │   │        ├── constant
│   │   │        │   ├── RecordParam
│   │   │        ├── controller
│   │   │        │   ├── CustomErrorController
│   │   │        │   ├── PurchaseController
│   │   │        │   └── RecordController
│   │   │        ├── exceptions
│   │   │        │   ├── InvalidParameterException
│   │   │        │   ├── ResourceNotFoundException
│   │   │        ├── model
│   │   │        │   ├── entity
│   │   │        │   │   ├── Purchase
│   │   │        │   │   └── Record
│   │   │        │   └── repository
│   │   │        │       ├── PurchaseRepository
│   │   │        │       └── RecordRepository
│   │   │        ├── service
│   │   │        │   ├── impl
│   │   │        │   │   ├── PurchaseServiceImpl
│   │   │        │   │   └── RecordServiceImpl
│   │   │        │   ├── PurchaseService
│   │   │        │   └── RecordService
│   │   │        ├── util
│   │   │        │   ├── DateUtil
│   │   │        ├── GroupProjectApplication
│   │   ├── resources
│   │       ├── db.migration
│   │       │   ├── V1_Create_Records_Table.sql
│   │       │   └── V2_Create_Purchases_Table.sql
│   │       └── application.yml
│   └── test
│       └── java
│           └── com.example.group.project
│               ├── controllerTests
│               │   ├── PurchaseControllerTests
│               │   └── RecordControllerTests
│               ├── serviceTests
│               │   ├── PurchaseServiceImplTests
│               │   └── RecordServiceImplTests
│               ├── utilTests
│               │     └── DateUtilTests
│               └── GroupProjectApplicationTests
├── target
├── .gitignore
├── docker-compose.yml
├── Dockerfile
├── mvnw
├── mvnw.cmd
├── pom.xml
└── README.md


```
