# GROUP 2 PROJECT
## CD Store Database App

## Introduction

This project is a Spring Boot application that manages purchases and records of a music shop that sells CDs. It utilizes JPA for database interactions and Lombok to reduce boilerplate code. The application consists of two main classes: `Purchase` and `Record`, each mapped to corresponding database tables.

The application should have the following:

| Functionality  | Description                                     |
|----------------|-------------------------------------------------|
| **Inserting**  | Add purchases to the `Purchases` table          |
| **Updating**   | Update the quantity of records after a purchase |
| **Retrieving** | Find specific record by name                    |
|                | Get list of records by artist                   |
|                | Find record by name and artist                  |


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
    - Rename `application_template.yml` to `application.yml`
    - Put your MySQL database password in `password`
    - Deploy the database to your SQL by clicking on the database icon. Alternatively, click on the lightbulb icon when the cursor is on line 6 and select `create or open an existing datasource`.

3. **Ensure the proper dependencies are installed. Please refer to `pom.xml` in the file for your reference**

## How to Run the Application

1. **Start the application**:
    In the project's root directory, run [GroupProjectApplication](src/main/java/com/example/group/project/GroupProjectApplication.java)

2. **API Endpoints**:

    Once the application is running, you can access the following endpoints:

    | Endpoint URL                                                       | Method | Description                                                                                                                                 | Example Request                                                           |
    |--------------------------------------------------------------------|--------|---------------------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------|
    | `http://localhost:8080/makePurchase`                               | POST   | Endpoint to make a purchase.<br><br>**Please note:** Discount is optional, and the customer field must be contain more than two characters. | ```{"customer": "John",```<br>```"id": 3,```<br>```"discount": "CFG" }``` |
    |                                                                    |        |                                                                                                                                             |                                                                           |     |
    | `http://localhost:8080/getRecord?artist={ARTISTNAME}&name={ALBUM}` | GET    | Endpoint to retrieve the information on a specific artist.                                                                                  | http://localhost:8080/getRecord?artist=Michael%20Jackson&name=Thriller    |

### API Spec:

This will help in generating interactive API documentation so you can play around with the API calls. Please see Open API: [here](http://localhost:8080/swagger-ui/index.html).

### Developers + Github profiles:

- [Rachel](https://github.com/Tookles)
- [Fabiola](https://github.com/Fabi-P)
- [Alyssa](https://github.com/lyscodes)
