# User Stories

### 1. Error Handling
- **As a record shop customer, when I ping an invalid endpoint, I should receive a clear error message explaining the issue.**
  - **Endpoint**: `@ExceptionHandler(Exception.class)`
  - **Expected Response**:
    ```json
    {
      "You attempted to access the following URL": "<requested URL>",
      "Further details": "<error message>"
    }
    ```
---
## 2. Making Purchases
- **As a record shop customer, when I send a POST request to `/purchase` with valid purchase details, I should be able to successfully complete my purchase and receive a confirmation with a purchase ID.**
  - **Endpoint**: `@PostMapping("/purchase")`
  - **Expected Response**:
    ```json
    {
      "message": "Purchase successful! Purchase Id <purchaseId>"
    }
    ```

- **As a record shop customer, when I send a POST request to `/purchase` without providing my name, I should receive an error message indicating that the customer name is required.**
  - **Endpoint**: `@PostMapping("/purchase")`
  - **Expected Response**:
    ```json
    {
      "error": "Customer name not provided"
    }
    ```

- **As a record shop customer, when I send a POST request to `/purchase` with a name shorter than 3 characters, I should receive an error message indicating that the customer name is too short.**
  - **Endpoint**: `@PostMapping("/purchase")`
  - **Expected Response**:
    ```json
    {
      "error": "Customer name too short"
    }
    ```

- **As a record shop customer, when I send a POST request to `/purchase` without providing an item ID, I should receive an error message indicating that the item ID is required.**
  - **Endpoint**: `@PostMapping("/purchase")`
  - **Expected Response**:
    ```json
    {
      "error": "No Id provided"
    }
    ```

- **As a record shop customer, when I send a POST request to `/purchase` with a non-numeric item ID, I should receive an error message indicating that the item ID must be a numerical value.**
  - **Endpoint**: `@PostMapping("/purchase")`
  - **Expected Response**:
    ```json
    {
      "error": "Id must be numerical value"
    }
    ```

- **As a record shop customer, when I send a POST request to `/purchase` with an invalid item ID, I should receive an error message indicating that the item ID is not valid.**
  - **Endpoint**: `@PostMapping("/purchase")`
  - **Expected Response**:
    ```json
    {
      "error": "This is not a valid item Id"
    }
    ```

- **As a record shop customer, when I send a POST request to `/purchase` for an out-of-stock item, I should receive an error message indicating that the item is not in stock.**
  - **Endpoint**: `@PostMapping("/purchase")`
  - **Expected Response**:
    ```json
    {
      "error": "Item not in stock"
    }
    ```

- **As a record shop customer, when I send a POST request to `/purchase` and an unknown error occurs, I should receive an error message indicating that the purchase could not be completed and to try again later.**
  - **Endpoint**: `@PostMapping("/purchase")`
  - **Expected Response**:
    ```json
    {
      "error": "Unable to complete purchase. Please try again later"
    }
    ```
---
### 3. Fetching Records
- **As a record shop customer, when I send a GET request to `/records` with valid parameters, I should receive a list of records matching my search criteria.**
  - **Endpoint**: `@GetMapping("/records")`
  - **Expected Response**:
    ```json
    {
      "records": [/* array of matching records */]
    }
    ```

- **As a record shop customer, when I send a GET request to `/records` with invalid parameters, I should receive an error message indicating that the parameters are invalid.**
  - **Endpoint**: `@GetMapping("/records")`
  - **Expected Response**:
    ```json
    {
      "error": "Invalid Parameters used in the request"
    }
    ```

- **As a record shop customer, when I send a GET request to `/records` with parameters that do not match any records, I should receive an error message indicating that no records were found.**
  - **Endpoint**: `@GetMapping("/records")`
  - **Expected Response**:
    ```json
    {
      "error": "No record found having artist / name {}  "
    }
    ```
