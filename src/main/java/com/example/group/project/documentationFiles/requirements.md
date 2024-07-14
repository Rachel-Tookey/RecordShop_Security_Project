# Group 2 Documentation

### Summary of the App

The **Application (name TBC)** is designed to manage purchases and records in a music store.

The application allows users to track customer purchases and manage inventory records, it also stores more specific details about the records such as:
- name
- artist
- quantity
- price

### Application Requirements
### Note: All TBC

1. **Purchase Management:**
   - Ability to create, read, update, and delete purchases.
   - Each purchase must include:
     - Customer name
     - Item ID
     - Price
     - Purchase date


2. **Record Management:**
   - Ability to create, read, update, and delete records.
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

### Technologies and Tools (TBC)

- **Java:** Main programming language that will be utilised
- **Spring Boot:** Framework to create RESTful services easily.
- **JPA (Java Persistence API):** For managing relational data.
- **MySQL:** Database for development and testing purposes.
- **Lombok:** For reducing boilerplate code in Java classes.

### Steps We Need to Implement:

1. **Define Entities:** Create JPA entities for `Purchase` and `Record`, including all required attributes and annotations.
2. **Create Repositories:** Set up repository interfaces for `Purchase` and `Record` to perform CRUD operations.
3. **Develop Services:** Build service layers to handle the business logic for managing purchases and records.
4. **Create Controllers:** Set up RESTful endpoints that allow interaction with the service layer.
5. **Testing:** Write unit and integration tests to verify that the application functions correctly.

---

## Manual Test Plan

### Test Cases

| Test Case ID | Description            | Expected Result             |
|--------------|------------------------|------------------------------|
| TC-01        | TBC                    | TBC                          |
| TC-02        | TBC                    | TBC                          |
| TC-03        | TBC                    | TBC                          |
| TC-04        | TBC                    | TBC                          |
| TC-05        | TBC                    | TBC                          |
| TC-06        | TBC                    | TBC                          |
| TC-07        | TBC                    | TBC                          |
| TC-08        | TBC                    | TBC                          |


### Test Execution Steps (examples only)

1. **Create Purchases:**
   - Send POST requests to add different purchases using valid data.
   - Verify that the new entries appear in the database.

2. **Retrieve Purchases:**
   - Use GET requests to fetch purchases by ID and check the response.

3. **Update Purchases:**
   - Send PUT requests to modify an existing purchase and ensure the changes are reflected.

4. **Delete Purchases:**
   - Use DELETE requests to remove purchases and confirm they no longer exist in the database.

5. **Create Records:**
   - Follow the same steps for adding records as you did for purchases.

6. **Retrieve, Update, and Delete Records:**
   - Perform similar operations for records to ensure all CRUD operations (Create, Read, Update, Delete) work as expected.


---

## Directory Structure

```
NOTE: not the final version - classes / packages etc. would need to be amended appropriately
as we go along. Just putting this here for visualisation purposes

/project-root
|-- /src
|   |-- /main
|   |   |-- /java
|   |   |   |-- /com
|   |   |   |   |-- /example
|   |   |   |   |   |-- /group
|   |   |   |   |   |   |-- /project
|   |   |   |   |   |   |   |-- model
|   |   |   |   |   |   |   |   |-- Purchase
|   |   |   |   |   |   |   |   |-- PurchaseRepository
|   |   |   |   |   |   |   |   |-- Record
|   |   |   |   |   |   |   |   |-- RecordRepository
|   |   |   |   |   |   |   |   |-- GroupProjectApplication.java
|   |   |-- /resources
|   |   |   |--db.migration
|   |   |   |    |--V1__Create_Records_Table.sql
|   |   |   |    |--V2__Create_Purchases_Table.sql
|   |   |   |--application.properties
|   |   |   |--application_template.yml


```
