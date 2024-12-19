package com.example.group.project.controllerTests;

import com.example.group.project.controller.PurchaseController;
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
import static org.mockito.Mockito.when;

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

        Map<String, String> userPurchase = new HashMap<>();
        userPurchase.put("customer", "John");
        userPurchase.put("id", "1");


        when(purchaseServiceImpl.checkIdExists("1")).thenReturn(true);
        when(purchaseServiceImpl.checkStock("1")).thenReturn(true);
        when(purchaseServiceImpl.commitPurchase(userPurchase)).thenReturn(1L);


        String expectedString = "Purchase successful! Purchase Id 1";

        RestAssuredMockMvc
                .given()
                .contentType("application/json")
                .body(userPurchase)
                .when()
                .post("/auth/purchase")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body(equalTo(expectedString));

    }

    @Test
    public void purchase_MissingCustomer_ReturnsBadRequest () {

        Map<String, String> userPurchase = new HashMap<>();
        userPurchase.put("id", "1");

        RestAssuredMockMvc
                .given()
                .contentType("application/json")
                .body(userPurchase)
                .when()
                .post("/auth/purchase")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body(equalTo("Customer name not provided"));
    }

    @Test
    public void purchase_TooShortCustomer_ReturnsBadRequest () {

        Map<String, String> userPurchase = new HashMap<>();
        userPurchase.put("customer", "Jo");
        userPurchase.put("id", "1");

        RestAssuredMockMvc
                .given()
                .contentType("application/json")
                .body(userPurchase)
                .when()
                .post("/auth/purchase")
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
                .post("/auth/purchase")
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
                .post("/auth/purchase")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body(equalTo("Id must be numerical value"));
    }


    @Test
    public void purchase_InvalidID_ReturnsBadRequest () {

        Map<String, String> userPurchase = new HashMap<>();
        userPurchase.put("customer", "John");
        userPurchase.put("id", "1");

        RestAssuredMockMvc
                .given()
                .contentType("application/json")
                .body(userPurchase)
                .when()
                .post("/auth/purchase")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body(equalTo("This is not a valid item Id"));
    }

    @Test
    public void purchase_OutOfStock_ReturnsConflict () {

        Map<String, String> userPurchase = new HashMap<>();
        userPurchase.put("customer", "John");
        userPurchase.put("id", "1");

        when(purchaseServiceImpl.checkIdExists("1")).thenReturn(true);

        RestAssuredMockMvc
                .given()
                .contentType("application/json")
                .body(userPurchase)
                .when()
                .post("/auth/purchase")
                .then()
                .statusCode(HttpStatus.CONFLICT.value())
                .body(equalTo("Item not in stock"));
    }
}
