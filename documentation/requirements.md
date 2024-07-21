# Group 2 Documentation

### Summary of the App

The **Record Shop App** is designed to manage purchases and records in a music store.

The application allows users to track customer purchases and manage inventory records, it also stores more specific details about the records such as:
- Record ID
- Name
- Artist
- Quantity
- Price

### Application Requirements


1. **Purchase Management:**
   - Ability to create, read, and update purchases.
   - Each purchase must include:
     - Purchase ID
     - Customer Name
     - Item ID
     - Price
     - Purchase Date
     - Discount code (optional)


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

## How to Achieve These

### Technologies and Tools

- **Java:** Main programming language that will be utilised
- **Spring Boot / Spring Web:** Framework to create RESTful services easily.
- **Lombok:** Automatically generates common code like getters, setters, and constructors. Will reduce the amount of boilerplate code, thus making the code appear cleaner.
- **JDBC API:** It provides methods for running SQL queries, updating data, and managing connections to a database.
- **Spring Data JPA:** A project within the Spring ecosystem that simplifies working with databases. It builds on top of JPA (Java Persistence API) and helps in minimising code writing.
- **Flyway Migration:** Tool for managing changes to the database schema. It helps in applying updates and track which changes have been made, ensuring the database stays in sync across different environments.
- **MySQL Driver:** Allows Java applications talk to MySQL databases. It handles the connection between the Java code and the MySQL database.
- **Mockito:** Java library used for unit testing.  It creates mock objects, which are dummy versions of real objects used to test specific parts of one's code.

### Steps We Need to Implement:

1. **Define Entities:** Create JPA entities for `Purchase` and `Record`, including all required attributes and annotations.
2. **Create Repositories:** Set up repository interfaces for `Purchase` and `Record` to perform most of the CRUD operations.
3. **Develop Services:** Build service layers to handle the business logic for managing purchases and records.
4. **Create Controllers:** Set up RESTful endpoints that allow interaction with the service layer.
5. **Testing:** Write unit and integration tests to verify that the application functions correctly.

---

## Manual Test Plan

## **PurchaseControllerTests**

| Test Case ID | Description                                                                                                  | Expected Result                                                                                               |
|--------------|--------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------|
| TC-01        | Test successful purchase with correct parameters.                                                          | Returns HTTP 200 OK with a message "Purchase successful! Purchase ID 1"                                      |
| TC-02        | Test purchase when customer name is missing.                                                               | Returns HTTP 400 Bad Request with a message "Customer name not provided"                                      |
| TC-03        | Test purchase when customer name is too short.                                                             | Returns HTTP 400 Bad Request with a message "Customer name too short"                                          |
| TC-04        | Test purchase when ID is missing.                                                                          | Returns HTTP 400 Bad Request with a message "No ID provided"                                                   |
| TC-05        | Test purchase with wrong type for ID.                                                                      | Returns HTTP 400 Bad Request with a message "ID must be numerical value"                                       |
| TC-06        | Test purchase with an invalid ID.                                                                          | Returns HTTP 400 Bad Request with a message "This is not a valid item id"                                      |
| TC-07        | Test purchase with an out-of-stock item.                                                                    | Returns HTTP 409 Conflict with a message "Item not in stock"                                                    |

## **RecordControllerTests**

| Test Case ID | Description                                                                                                  | Expected Result                                                                                               |
|--------------|--------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------|
| TC-08        | Test successful retrieval of records when both artist and name parameters are provided.                     | Returns HTTP 200 OK with records matching the artist "Michael Jackson" and name "Thriller" in the response    |
| TC-09        | Test successful retrieval of records when only artist parameter is provided.                               | Returns HTTP 200 OK with records matching the artist "Michael Jackson" in the response                         |
| TC-10        | Test successful retrieval of records when only name parameter is provided.                                 | Returns HTTP 200 OK with records matching the name "Rec1" in the response                                       |
| TC-11        | Test successful retrieval of all records when no parameters are provided.                                  | Returns HTTP 200 OK with all records in the response                                                            |
| TC-12        | Test retrieval of records with non-existing artist and name.                                                 | Returns HTTP 200 OK with a message "No record found with name Blowing in the wind and artist Bob Dylan"        |
| TC-13        | Test retrieval of records with non-existing artist.                                                          | Returns HTTP 200 OK with a message "No record found having artist Bob Dylan"                                   |
| TC-14        | Test retrieval of records with non-existing name.                                                            | Returns HTTP 200 OK with a message "No record found with name Blowing in the wind"                             |
| TC-15        | Test retrieval of records with incorrect parameter keys.                                                      | Returns HTTP 200 OK with all records in the response                                                            |

## **PurchaseServiceImplTests**

| Test Case ID | Description                                                                                                  | Expected Result                                                                                               |
|--------------|--------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------|
| TC-16        | Test successful conversion of ID to Long in `pullID`.                                                        | Returns Long type ID from the `pullID` method                                                                 |
| TC-17        | Test `checkStock` method returns true for in-stock item.                                                      | Returns true from `checkStock` method when item quantity is greater than 0                                      |
| TC-18        | Test `checkStock` method returns false for out-of-stock item.                                                 | Returns false from `checkStock` method when item quantity is 0                                                  |
| TC-19        | Test `checkIdExists` method returns true for a valid ID.                                                       | Returns true from `checkIdExists` method when the ID exists in the repository                                    |
| TC-20        | Test `checkIdExists` method returns false for an invalid ID.                                                   | Returns false from `checkIdExists` method when the ID does not exist in the repository                          |
| TC-21        | Test `adjustPrice` method returns price with no discount for valid input.                                     | Returns the original price from `adjustPrice` method when no discount is applied                                 |
| TC-22        | Test `adjustPrice` method applies discount correctly for valid input.                                         | Returns the discounted price from `adjustPrice` method when a valid discount is applied                         |
| TC-23        | Test `adjustPrice` method returns original price for invalid discount input.                                 | Returns the original price from `adjustPrice` method when an invalid discount is applied                        |
| TC-24        | Test `commitPurchase` method returns ID and saves the purchase correctly.                                     | Returns the purchase ID from `commitPurchase` method and verifies that the purchase is saved in the repository    |

## **DateUtilTests**

| Test Case ID | Description                                                                                                  | Expected Result                                                                                               |
|--------------|--------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------|
| TC-25        | Test `DateUtil.getDate()` returns the current date and is of type LocalDate.                                  | Returns the current date and verifies that it is of type LocalDate                                              |

---

### Test Execution Steps

## **PurchaseControllerTests**

### **TC-01: Test Successful Purchase with Correct Parameters**
1. **Setup**: Ensure the `PurchaseController` is correctly mocked and configured.
2. **Request**: Send a POST request to `/makePurchase` with JSON body:
    ```json
    {
      "customer": "John",
      "id": 1
    }
    ```
3. **Verify**: Check the response status code is `200 OK` and the body contains:
    ```
    Purchase successful! Purchase ID 1
    ```

### **TC-02: Test Purchase When Customer Name is Missing**
1. **Setup**: Ensure the `PurchaseController` is correctly mocked and configured.
2. **Request**: Send a POST request to `/makePurchase` with JSON body:
    ```json
    {
      "id": 1
    }
    ```
3. **Verify**: Check the response status code is `400 Bad Request` and the body contains:
    ```
    Customer name not provided
    ```

### **TC-03: Test Purchase When Customer Name is Too Short**
1. **Setup**: Ensure the `PurchaseController` is correctly mocked and configured.
2. **Request**: Send a POST request to `/makePurchase` with JSON body:
    ```json
    {
      "customer": "Jo",
      "id": 1
    }
    ```
3. **Verify**: Check the response status code is `400 Bad Request` and the body contains:
    ```
    Customer name too short
    ```

### **TC-04: Test Purchase When ID is Missing**
1. **Setup**: Ensure the `PurchaseController` is correctly mocked and configured.
2. **Request**: Send a POST request to `/makePurchase` with JSON body:
    ```json
    {
      "customer": "John"
    }
    ```
3. **Verify**: Check the response status code is `400 Bad Request` and the body contains:
    ```
    No ID provided
    ```

### **TC-05: Test Purchase with Wrong Type for ID**
1. **Setup**: Ensure the `PurchaseController` is correctly mocked and configured.
2. **Request**: Send a POST request to `/makePurchase` with JSON body:
    ```json
    {
      "customer": "John",
      "id": "three"
    }
    ```
3. **Verify**: Check the response status code is `400 Bad Request` and the body contains:
    ```
    ID must be numerical value
    ```

### **TC-06: Test Purchase with Invalid ID**
1. **Setup**: Ensure the `PurchaseController` is correctly mocked and configured.
2. **Request**: Send a POST request to `/makePurchase` with JSON body:
    ```json
    {
      "customer": "John",
      "id": 1
    }
    ```
3. **Verify**: Check the response status code is `400 Bad Request` and the body contains:
    ```
    This is not a valid item id
    ```

### **TC-07: Test Purchase with Out-of-Stock Item**
1. **Setup**: Ensure the `PurchaseController` is correctly mocked and configured.
2. **Request**: Send a POST request to `/makePurchase` with JSON body:
    ```json
    {
      "customer": "John",
      "id": 1
    }
    ```
3. **Verify**: Check the response status code is `409 Conflict` and the body contains:
    ```
    Item not in stock
    ```

## **RecordControllerTests**

### **TC-08: Test Successful Retrieval of Records with Artist and Name Parameters**
1. **Setup**: Ensure the `RecordController` is correctly mocked and configured.
2. **Request**: Send a GET request to `/getRecord` with parameters:
    ```
    artist=Michael Jackson
    name=Thriller
    ```
3. **Verify**: Check the response status code is `200 OK` and the response body contains records with:
    ```
    artist: Michael Jackson
    name: Thriller
    ```

### **TC-09: Test Successful Retrieval of Records with Artist Parameter**
1. **Setup**: Ensure the `RecordController` is correctly mocked and configured.
2. **Request**: Send a GET request to `/getRecord` with parameter:
    ```
    artist=Michael Jackson
    ```
3. **Verify**: Check the response status code is `200 OK` and the response body contains records with:
    ```
    artist: Michael Jackson
    ```

### **TC-10: Test Successful Retrieval of Records with Name Parameter**
1. **Setup**: Ensure the `RecordController` is correctly mocked and configured.
2. **Request**: Send a GET request to `/getRecord` with parameter:
    ```
    name=Thriller
    ```
3. **Verify**: Check the response status code is `200 OK` and the response body contains records with:
    ```
    name: Thriller
    ```

### **TC-11: Test Successful Retrieval of All Records with No Parameters**
1. **Setup**: Ensure the `RecordController` is correctly mocked and configured.
2. **Request**: Send a GET request to `/getRecord` with no parameters.
3. **Verify**: Check the response status code is `200 OK` and the response body contains all records.

### **TC-12: Test Retrieval of Records with Non-existing Artist and Name**
1. **Setup**: Ensure the `RecordController` is correctly mocked and configured.
2. **Request**: Send a GET request to `/getRecord` with parameters:
    ```
    artist=SEVENTEEN
    name=17 Is Right Here
    ```
3. **Verify**: Check the response status code is `200 OK` and the response body contains:
    ```
    No record found with name 17 Is Right Here and artist SEVENTEEN
    ```

### **TC-13: Test Retrieval of Records with Non-existing Artist**
1. **Setup**: Ensure the `RecordController` is correctly mocked and configured.
2. **Request**: Send a GET request to `/getRecord` with parameter:
    ```
    artist=SEVENTEEN
    ```
3. **Verify**: Check the response status code is `200 OK` and the response body contains:
    ```
    No record found having artist SEVENTEEN
    ```

### **TC-14: Test Retrieval of Records with Non-existing Name**
1. **Setup**: Ensure the `RecordController` is correctly mocked and configured.
2. **Request**: Send a GET request to `/getRecord` with parameter:
    ```
    name=17 Is Right Here
    ```
3. **Verify**: Check the response status code is `200 OK` and the response body contains:
    ```
    No record found with name 17 Is Right Here
    ```

### **TC-15: Test Retrieval of Records with Incorrect Parameter Keys**
1. **Setup**: Ensure the `RecordController` is correctly mocked and configured.
2. **Request**: Send a GET request to `/getRecord` with parameters:
    ```
    [blank]
    ```
3. **Verify**: Check the response status code is `200 OK` and the response body contains all records.

## **PurchaseServiceImplTests**

### **TC-16: Test Successful Conversion of ID to Long in `pullID`**
1. **Setup**: Ensure the `PurchaseServiceImpl` is correctly mocked and configured.
2. **Request**: Call `pullID` method with a map containing:
    ```json
    {
      "customer": "John",
      "id": 1
    }
    ```
3. **Verify**: Ensure the returned type is `Long`.

### **TC-17: Test `checkStock` Method Returns True for In-stock Item**
1. **Setup**: Ensure the `PurchaseServiceImpl` is correctly mocked and configured.
2. **Request**: Call `checkStock` method with a map containing:
    ```json
    {
      "id": 1
    }
    ```
3. **Verify**: Ensure `checkStock` returns `true`.

### **TC-18: Test `checkStock` Method Returns False for Out-of-stock Item**
1. **Setup**: Ensure the `PurchaseServiceImpl` is correctly mocked and configured.
2. **Request**: Call `checkStock` method with a map containing:
    ```json
    {
      "id": 1
    }
    ```
3. **Verify**: Ensure `checkStock` returns `false`.

### **TC-19: Test `checkIdExists` Method Returns True for Valid ID**
1. **Setup**: Ensure the `PurchaseServiceImpl` is correctly mocked and configured.
2. **Request**: Call `checkIdExists` method with a map containing:
    ```json
    {
      "id": 2
    }
    ```
3. **Verify**: Ensure `checkIdExists` returns `true`.

### **TC-20: Test `checkIdExists` Method Returns False for Invalid ID**
1. **Setup**: Ensure the `PurchaseServiceImpl` is correctly mocked and configured.
2. **Request**: Call `checkIdExists` method with a map containing:
    ```json
    {
      "id": 2
    }
    ```
3. **Verify**: Ensure `checkIdExists` returns `false`.

### **TC-21: Test `adjustPrice` Method Returns Price with No Discount**
1. **Setup**: Ensure the `PurchaseServiceImpl` is correctly mocked and configured.
2. **Request**: Call `adjustPrice` method with a map containing:
    ```json
    {
      "price": 100.00
    }
    ```
3. **Verify**: Ensure `adjustPrice` returns `100.00`.

### **TC-22: Test `adjustPrice` Method Applies Discount Correctly**
1. **Setup**: Ensure the `PurchaseServiceImpl` is correctly mocked and configured.
2. **Request**: Call `adjustPrice` method with a map containing:
    ```json
    {
      "price": 100.00,
      "discount": 10
    }
    ```
3. **Verify**: Ensure `adjustPrice` returns `90.00`.

### **TC-23: Test `adjustPrice` Method Returns Original Price for Invalid Discount**
1. **Setup**: Ensure the `PurchaseServiceImpl` is correctly mocked and configured.
2. **Request**: Call `adjustPrice` method with a map containing:
    ```json
    {
      "price": 100.00,
      "discount": -10
    }
    ```
3. **Verify**: Ensure `adjustPrice` returns `100.00`.

### **TC-24: Test `commitPurchase` Method Returns ID and Saves the Purchase**
1. **Setup**: Ensure the `PurchaseServiceImpl` is correctly mocked and configured.
2. **Request**: Call `commitPurchase` method with a map containing:
    ```json
    {
      "customer": "John",
      "id": 1
    }
    ```
3. **Verify**: Ensure the method returns the purchase ID and the purchase is saved in the repository.

## **DateUtilTests**

### **TC-25: Test `DateUtil.getDate()` Returns Current Date and is of Type LocalDate**
1. **Setup**: Ensure the `DateUtil` class is correctly configured.
2. **Request**: Call `DateUtil.getDate()`.
3. **Verify**: Ensure the returned date is of type `LocalDate` and matches the current date.


---

## Directory Structure

```
NOTE: not the final version - classes / packages etc. would need to be amended appropriately
as we go along. Just putting this here for visualisation purposes

cfg_mastersplus_java_group_two [group-project]
├── .idea
├── .mvn
├── documentation
│   ├── appFlowchart.md
│   ├── requirements.md
│   └── requirementsPlan.md
├── src
│   └── main
│       └── java
│           └── com.example.group.project
│               ├── controller
│               │   ├── CustomErrorController
│               │   ├── PurchaseController
│               │   └── RecordController
│               ├── model
│               │   ├── entity
│               │   │   ├── Purchase
│               │   │   └── Record
│               │   └── repository
│               │       ├── PurchaseRepository
│               │       └── RecordRepository
│               ├── service
│               │   ├── impl
│               │   │   ├── PurchaseServiceImpl
│               │   └── PurchaseService
│               └── util
│               └── GroupProjectApplication
├── resources
│   ├── db.migration
│   ├── application.properties
│   └── application.yml
├── test
│   └── java
│       └── com.example.group.project
│           ├── controllerTests
│           ├── serviceTests
│           └── utilTests
│           └── GroupProjectApplicationTests
├── target
├── .gitignore
├── mvnw
├── mvnw.cmd
├── pom.xml
└── README.md

```
