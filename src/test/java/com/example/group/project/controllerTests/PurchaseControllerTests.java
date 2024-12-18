package com.example.group.project.controllerTests;

import com.example.group.project.controller.PurchaseController;
import com.example.group.project.model.entity.Purchase;
import com.example.group.project.model.entity.Record;

import com.example.group.project.service.impl.PurchaseServiceImpl;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
public class PurchaseControllerTests {

    @Mock
    private PurchaseServiceImpl purchaseServiceImpl; // Mock the service

    @InjectMocks
    private PurchaseController purchaseController;

    @BeforeEach
    public void setUpMockPurchaseController() { RestAssuredMockMvc.standaloneSetup(purchaseController); }

    @Test
    public void purchase_CorrectParameters_Success () {
        Record recordTest = Record.builder()
                .id(1L)
                .name("Thriller")
                .artist("Michael Jackson")
                .quantity(1)
                .price(9.99)
                .build();

        Purchase purchaseTest = Purchase.builder()
                .id(1L)
                .customer("John")
                .price(9.99)
                .date(LocalDate.parse("2019-07-03"))
                .recordLink(recordTest)
                .build();

        when(purchaseServiceImpl.checkIdExists(any(String.class))).thenReturn(true);
        when(purchaseServiceImpl.checkStock(any(String.class))).thenReturn(true);
        when(purchaseServiceImpl.commitPurchase(any(Map.class))).thenReturn(purchaseTest.getId());

        Map<String, Object> userPurchase = new HashMap<>();
        userPurchase.put("customer", "John");
        userPurchase.put("id", recordTest.getId());

        String expectedString = "Purchase successful! Purchase Id " + purchaseTest.getId();

        RestAssuredMockMvc
                .given()
                .contentType("application/json")
                .body(userPurchase)
                .when()
                .post("/purchase")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body(equalTo(expectedString));

    }

    @Test
    public void purchase_MissingCustomer_ReturnsBadRequest () {

        Map<String, Object> userPurchase = new HashMap<>();
        userPurchase.put("id", 1L);

        RestAssuredMockMvc
                .given()
                .contentType("application/json")
                .body(userPurchase)
                .when()
                .post("/purchase")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body(equalTo("Customer name not provided"));
    }

    @Test
    public void purchase_TooShortCustomer_ReturnsBadRequest () {

        Map<String, Object> userPurchase = new HashMap<>();
        userPurchase.put("customer", "Jo");
        userPurchase.put("id", 1L);

        RestAssuredMockMvc
                .given()
                .contentType("application/json")
                .body(userPurchase)
                .when()
                .post("/purchase")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body(equalTo("Customer name too short"));
    }

    @Test
    public void purchase_MissingID_ReturnsBadRequest () {

        Map<String, Object> userPurchase = new HashMap<>();
        userPurchase.put("customer", "John");

        RestAssuredMockMvc
                .given()
                .contentType("application/json")
                .body(userPurchase)
                .when()
                .post("/purchase")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body(equalTo("No Id provided"));
    }

    @Test
    public void purchase_WrongType_ReturnsBadRequest () {

        Map<String, Object> userPurchase = new HashMap<>();
        userPurchase.put("customer", "John");
        userPurchase.put("id", "three");

        RestAssuredMockMvc
                .given()
                .contentType("application/json")
                .body(userPurchase)
                .when()
                .post("/purchase")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body(equalTo("Id must be numerical value"));
    }


    @Test
    public void purchase_InvalidID_ReturnsBadRequest () {

        Map<String, Object> userPurchase = new HashMap<>();
        userPurchase.put("customer", "John");
        userPurchase.put("id", 1L);

        RestAssuredMockMvc
                .given()
                .contentType("application/json")
                .body(userPurchase)
                .when()
                .post("/purchase")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body(equalTo("This is not a valid item Id"));
    }

    @Test
    public void purchase_OutOfStock_ReturnsConflict () {

        Record recordTest = Record.builder()
                .id(1L)
                .name("Thriller")
                .artist("Michael Jackson")
                .quantity(0)
                .price(9.99)
                .build();

        Map<String, Object> userPurchase = new HashMap<>();
        userPurchase.put("customer", "John");
        userPurchase.put("id", recordTest.getId());

        when(purchaseServiceImpl.checkIdExists(any(String.class))).thenReturn(true);


        RestAssuredMockMvc
                .given()
                .contentType("application/json")
                .body(userPurchase)
                .when()
                .post("/purchase")
                .then()
                .statusCode(HttpStatus.CONFLICT.value())
                .body(equalTo("Item not in stock"));
    }
}
