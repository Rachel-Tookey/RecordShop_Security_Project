## Manual Test Plan

## **PurchaseControllerTests**

| Test Case ID | Description                                       | Expected Result                                                           |
|--------------|---------------------------------------------------|---------------------------------------------------------------------------|
| TC-01        | Test successful purchase with correct parameters. | Returns HTTP 200 OK with a message "Purchase successful! Purchase ID 1"   |
| TC-02        | Test purchase when customer name is missing.      | Returns HTTP 400 Bad Request with a message "Customer name not provided"  |
| TC-03        | Test purchase when customer name is too short.    | Returns HTTP 400 Bad Request with a message "Customer name too short"     |
| TC-04        | Test purchase when ID is missing.                 | Returns HTTP 400 Bad Request with a message "No ID provided"              |
| TC-05        | Test purchase with wrong type for ID.             | Returns HTTP 400 Bad Request with a message "ID must be numerical value"  |
| TC-06        | Test purchase with an invalid ID.                 | Returns HTTP 400 Bad Request with a message "This is not a valid item id" |
| TC-07        | Test purchase with an out-of-stock item.          | Returns HTTP 409 Conflict with a message "Item not in stock"              |

## **RecordControllerTests**

| Test Case ID | Description                                                                             | Expected Result                                                                                            |
|--------------|-----------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------|
| TC-08        | Test successful retrieval of records when both artist and name parameters are provided. | Returns HTTP 200 OK with records matching the artist "Michael Jackson" and name "Thriller" in the response |
| TC-09        | Test successful retrieval of records when only artist parameter is provided.            | Returns HTTP 200 OK with records matching the artist "Michael Jackson" in the response                     |
| TC-10        | Test successful retrieval of records when only name parameter is provided.              | Returns HTTP 200 OK with records matching the name "Rec1" in the response                                  |
| TC-11        | Test successful retrieval of all records when no parameters are provided.               | Returns HTTP 200 OK with all records in the response                                                       |
| TC-12        | Test retrieval of records with non-existing artist and name.                            | Returns HTTP 200 OK with a message "No record found with name Blowing in the wind and artist Bob Dylan"    |
| TC-13        | Test retrieval of records with non-existing artist.                                     | Returns HTTP 200 OK with a message "No record found having artist Bob Dylan"                               |
| TC-14        | Test retrieval of records with non-existing name.                                       | Returns HTTP 200 OK with a message "No record found with name Blowing in the wind"                         |
| TC-15        | Test retrieval of records with incorrect parameter keys.                                | Returns HTTP 200 OK with all records in the response                                                       |

## **PurchaseServiceImplTests**

| Test Case ID | Description                                                                  | Expected Result                                                                                                |
|--------------|------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------|
| TC-16        | Test successful conversion of ID to Long in `pullID`.                        | Returns Long type ID from the `pullID` method                                                                  |
| TC-17        | Test `checkStock` method returns true for in-stock item.                     | Returns true from `checkStock` method when item quantity is greater than 0                                     |
| TC-18        | Test `checkStock` method returns false for out-of-stock item.                | Returns false from `checkStock` method when item quantity is 0                                                 |
| TC-19        | Test `checkIdExists` method returns true for a valid ID.                     | Returns true from `checkIdExists` method when the ID exists in the repository                                  |
| TC-20        | Test `checkIdExists` method returns false for an invalid ID.                 | Returns false from `checkIdExists` method when the ID does not exist in the repository                         |
| TC-21        | Test `adjustPrice` method returns price with no discount for valid input.    | Returns the original price from `adjustPrice` method when no discount is applied                               |
| TC-22        | Test `adjustPrice` method applies discount correctly for valid input.        | Returns the discounted price from `adjustPrice` method when a valid discount is applied                        |
| TC-23        | Test `adjustPrice` method returns original price for invalid discount input. | Returns the original price from `adjustPrice` method when an invalid discount is applied                       |
| TC-24        | Test `commitPurchase` method returns ID and saves the purchase correctly.    | Returns the purchase ID from `commitPurchase` method and verifies that the purchase is saved in the repository |

## **DateUtilTests**

| Test Case ID | Description                                                                  | Expected Result                                                    |
|--------------|------------------------------------------------------------------------------|--------------------------------------------------------------------|
| TC-25        | Test `DateUtil.getDate()` returns the current date and is of type LocalDate. | Returns the current date and verifies that it is of type LocalDate |

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
      "id": 6
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
---
## **RecordControllerTests**

### **TC-08: Test Successful Retrieval of Records with Artist and Name Parameters**

1. **Setup**: Ensure the `RecordController` is correctly mocked and configured.
2. **Request**: Send a GET request to `/getRecord` with parameters:
    ```
    artist=Michael Jackson
    name=Thriller
    ```
   **Link to copy:**
   ```
   http://localhost:8080/getRecord?artist=Michael%20Jackson&name=Thriller
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
   **Link to copy:**
    ```
   http://localhost:8080/getRecord?artist=Michael%20Jackson
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
   **Link to copy:**
    ```
   http://localhost:8080/getRecord?name=Thriller
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
   **Link to copy:**
    ```
   http://localhost:8080/getRecord?artist=SEVENTEEN&name=17%20Is%20Right%20Here
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
   **Link to copy:**

    ```
    http://localhost:8080/getRecord?artist=SEVENTEEN
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
   **Link to copy:**
    ```
    http://localhost:8080/getRecord?name=17%20Is%20Right%20Here
    ```
3. **Verify**: Check the response status code is `200 OK` and the response body contains:
    ```
    No record found with name 17 Is Right Here
    ```

### **TC-15: Test Retrieval of Records with Incorrect Parameter Keys**

1. **Setup**: Ensure the `RecordController` is correctly mocked and configured.
2. **Request**: Send a GET request to `/getRecord` with parameters:
    ```
    http://localhost:8080/getRecord
    ```
3. **Verify**: Check the response status code is `200 OK` and the response body contains all records.
---
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
    ```java
    assertTrue(testReturn instanceof Long);
    ```

### **TC-17: Test `checkStock` Method Returns True for In-stock Item**

1. **Setup**: Ensure the `PurchaseServiceImpl` is correctly mocked and configured.
2. **Request**: Call `checkStock` method with a map containing:
    ```json
    {
      "id": 1
    }
    ```
3. **Verify**: Ensure `checkStock` returns `true`.
    ```java
    assertTrue(checkStockTest);
    ```

### **TC-18: Test `checkStock` Method Returns False for Out-of-stock Item**

1. **Setup**: Ensure the `PurchaseServiceImpl` is correctly mocked and configured.
2. **Request**: Call `checkStock` method with a map containing:
    ```json
    {
      "id": 1
    }
    ```
3. **Verify**: Ensure `checkStock` returns `false`.
    ```java
    assertFalse(checkStockTest);
    ```

### **TC-19: Test `checkIdExists` Method Returns True for Valid ID**

1. **Setup**: Ensure the `PurchaseServiceImpl` is correctly mocked and configured.
2. **Request**: Call `checkIdExists` method with a map containing:
    ```json
    {
      "id": 2
    }
    ```
3. **Verify**: Ensure `checkIdExists` returns `true`.
    ```java
    assertTrue(checkStockTest);
    ```

### **TC-20: Test `checkIdExists` Method Returns False for Invalid ID**

1. **Setup**: Ensure the `PurchaseServiceImpl` is correctly mocked and configured.
2. **Request**: Call `checkIdExists` method with a map containing:
    ```json
    {
      "id": 2
    }
    ```
3. **Verify**: Ensure `checkIdExists` returns `false`.
    ```java
    assertFalse(checkStockTest);
    ```

### **TC-21: Test `adjustPrice` Method Returns Price with No Discount**

1. **Setup**: Ensure the `PurchaseServiceImpl` is correctly mocked and configured.
2. **Request**: Call `adjustPrice` method with a map containing:
    ```json
    {
      "id": 1
    }
    ```
3. **Verify**: Ensure `adjustPrice` returns the original price.
    ```java
    assertEquals(recordTest.getPrice(), recordPrice);
    ```

### **TC-22: Test `adjustPrice` Method Applies Discount Correctly**

1. **Setup**: Ensure the `PurchaseServiceImpl` is correctly mocked and configured.
2. **Request**: Call `adjustPrice` method with a map containing:
    ```json
    {
      "id": 1,
      "discount": "CFG"
    }
    ```
3. **Verify**: Ensure `adjustPrice` returns the discounted price.
    ```java
    assertEquals(7.99, recordPrice);
    ```

### **TC-23: Test `adjustPrice` Method Returns Original Price for Invalid Discount**

1. **Setup**: Ensure the `PurchaseServiceImpl` is correctly mocked and configured.
2. **Request**: Call `adjustPrice` method with a map containing:
    ```json
    {
      "id": 1,
      "discount": "cfg"
    }
    ```
3. **Verify**: Ensure `adjustPrice` returns the original price.
    ```java
    assertEquals(recordTest.getPrice(), recordPrice);
    ```

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
    ```java
    verify(purchaseRepository).save(purchaseTest);
    ```
---
## **DateUtilTests**

### **TC-25: Test `DateUtil.getDate()` Returns Current Date and is of Type LocalDate**

1. **Setup**: Ensure the `DateUtil` class is correctly configured.
2. **Request**: Call `DateUtil.getDate()`.
3. **Verify**: Ensure the returned date is of type `LocalDate` and matches the current date.

---