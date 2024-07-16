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

- Go to [application_template.yml](src/main/resources/application.yml)
- Make sure to rename `application_template.yml` to `application.yml`
- Put your mySQL's database password in `password`

3. **Ensure the proper dependencies are installed. Please refer to `pom.xml` in the file for your reference**

## How to Run the Application

1. **Start the application**:

    In the project's root directory, run [GroupProjectApplication](src/main/java/com/example/group/project/GroupProjectApplication.java)


2. **API Endpoints (Fetch requests to be updated)**:

    Once the application is running, you can access the following end points:


   | Endpoint URL | Method | Description | Example Request |
|--------------|--------|-------------|-----------------|
| `http://localhost:8080/makePurchase` | POST | Endpoint to make a purchase. | ```json { "customer": "John Doe", "price": 9.99, "date": "2024-07-15", "recordLink": { "id": 3 } }``` |
| `http://localhost:8080/getRecord?artist={ARTISTNAME}&name={ALBUM}` | GET | Endpoint to retrieve the information on a specific artist. | http://localhost:8080/getRecord?artist=Michael%20Jackson&name=Thriller |


### API Spec:

This will help in generating an interactive API documentation so you can play around with the API calls. Please see [here](http://localhost:8080/swagger-ui/index.html).

### Developers + Github profiles:

[Rachel](https://github.com/Tookles)

[Fabiola](https://github.com/Fabi-P)

[Alyssa](https://github.com/lyscodes)
