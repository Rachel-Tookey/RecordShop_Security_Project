# GROUP 2 PROJECT
## CD Store Database App

## Introduction 

This project is a Spring Boot application that manages purchases and records of a music shop that sells CDs. It utilises JPA for database interactions and Lombok to reduce boilerplate code. The application consists of two main classes: `Purchase` and `Record`, each mapped to corresponding database tables.

The application should have the following:

| Functionality  | Description                                     |
|----------------|-------------------------------------------------|
| **Inserting**  | Add records to the `Records` table              |
|                | Add purchases to the `Purchases` table          |
| **Updating**   | Update the quantity of records after a purchase   |
|                | Update the quantity of records when re-stocking   |
| **Retrieving** | Find specific record by name                     |
|                | Get list of records by artist                    |
|                | Get records sorted in specific order             |
|                | Get purchases from a date                        |
|                | Get purchases of a specific customer             |
| **Deleting**   | Remove records                                   |

## Prerequisites

Before you begin, please ensure you have the following:

- Java Development Kit (JDK) 17 or higher
- The latest version of Maven
- An SQL database (e.g., MySQL)
- IntelliJ Community / Ultimate

## Setup Instructions

1. **Clone the repository**.

2. **Configure the database**:

- Go to [application_template.yml](src/main/resources/application_template.yml)
- Put your mySQL's database password in `password`

3. **Ensure the proper dependencies are installed. Please refer to `pom.xml`**

## How to Run the Application

1. **Start the application**:

    In the project's root directory, run [GroupProjectApplication](src/main/java/com/example/group/project/GroupProjectApplication.java)

2. **Access the application (Fetch requests to be updated)**:

    Once the application is running, you can access it at `http://localhost:8080`.

```
Post body data:
{
"customer": "John Doe",
"price": 9.99,
"date": "2024-07-15",
"recordLink": {
"id": 3
}
}
```

### Developers + Github profiles:

[Rachel](https://github.com/Tookles)

[Fabiola](https://github.com/Fabi-P)

[Alyssa](https://github.com/lyscodes)
