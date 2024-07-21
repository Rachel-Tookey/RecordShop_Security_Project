package com.example.group.project.controllerTests;

import com.example.group.project.controller.PurchaseController;
import com.example.group.project.model.entity.Purchase;
import com.example.group.project.model.entity.Record;
import com.example.group.project.model.repository.PurchaseRepository;
import com.example.group.project.model.repository.RecordRepository;
import com.example.group.project.service.implementation.PurchaseServiceImpl;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.lenient;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

// Full stack testing of the purchase controller interaction with the purchase service later

@ExtendWith(MockitoExtension.class)
public class PurchaseControllerTests {

    @Mock
    private PurchaseRepository purchaseRepository;

    @Mock
    private RecordRepository recordRepository;

    @Mock
    private PurchaseServiceImpl purchaseServiceImpl; // Mock the service

    @InjectMocks
    private PurchaseController purchaseController;

    @BeforeEach
    public void setUpMockPurchaseController() { RestAssuredMockMvc.standaloneSetup(purchaseController); }

    // todo autowire the baseURI for docker
    @BeforeAll
    static void setup(){
        String baseURI = "http://localhost:8080";
    }

    @Test
    public void makePurchaseCorrectParametersSuccess () {
        Record recordTest = Record.builder()
                .id(1l)
                .name("Thriller")
                .artist("Michael Jackson")
                .quantity(1)
                .price(9.99)
                .build();

        Purchase purchaseTest = Purchase.builder()
                .id(1l)
                .customer("John")
                .price(9.99)
                .date(LocalDate.parse("2019-07-03"))
                .recordLink(recordTest)
                .build();

        lenient().when(recordRepository.existsById(recordTest.getId())).thenReturn(true);
        lenient().when(recordRepository.getReferenceById(recordTest.getId())).thenReturn(recordTest);
        lenient().when(purchaseRepository.save(any(Purchase.class))).thenAnswer(invocation -> invocation.getArgument(0));
        lenient().when(purchaseServiceImpl.pullID(any(Map.class))).thenReturn(recordTest.getId());
        lenient().when(purchaseServiceImpl.checkIdExists(any(Map.class))).thenReturn(true);
        lenient().when(purchaseServiceImpl.checkStock(any(Map.class))).thenReturn(true);
        lenient().when(purchaseServiceImpl.commitPurchase(any(Map.class))).thenReturn(purchaseTest.getId());

        Map<String, Object> userPurchase = new HashMap<>();
        userPurchase.put("customer", "John");
        userPurchase.put("id", recordTest.getId());

        String expectedString = "Purchase successful! Purchase ID " + purchaseTest.getId();

        RestAssuredMockMvc
                .given()
                .contentType("application/json")
                .body(userPurchase)
                .when()
                .post("/makePurchase")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body(equalTo(expectedString));

    }

    @Test
    public void makePurchaseMissingCustomerReturnsBadRequest () {

        Map<String, Object> userPurchase = new HashMap<>();
        userPurchase.put("id", 1l);

        RestAssuredMockMvc
                .given()
                .contentType("application/json")
                .body(userPurchase)
                .when()
                .post("/makePurchase")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body(equalTo("Customer name not provided"));
    }

    @Test
    public void makePurchaseTooShortCustomerReturnsBadRequest () {

        Map<String, Object> userPurchase = new HashMap<>();
        userPurchase.put("customer", "Jo");
        userPurchase.put("id", 1l);

        RestAssuredMockMvc
                .given()
                .contentType("application/json")
                .body(userPurchase)
                .when()
                .post("/makePurchase")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body(equalTo("Customer name too short"));
    }

    @Test
    public void makePurchaseMissingIDReturnsBadRequest () {

        Map<String, Object> userPurchase = new HashMap<>();
        userPurchase.put("customer", "John");

        RestAssuredMockMvc
                .given()
                .contentType("application/json")
                .body(userPurchase)
                .when()
                .post("/makePurchase")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body(equalTo("No ID provided"));
    }

    @Test
    public void makePurchaseWrongTypeReturnsBadRequest () {

        Map<String, Object> userPurchase = new HashMap<>();
        userPurchase.put("customer", "John");
        userPurchase.put("id", "three");

        when(purchaseServiceImpl.pullID(any(Map.class))).thenThrow(IllegalArgumentException.class);

        RestAssuredMockMvc
                .given()
                .contentType("application/json")
                .body(userPurchase)
                .when()
                .post("/makePurchase")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body(equalTo("ID must be numerical value"));
    }


    @Test
    public void makePurchaseInvalidIDReturnsBadRequest () {

        Map<String, Object> userPurchase = new HashMap<>();
        userPurchase.put("customer", "John");
        userPurchase.put("id", 1l);

        RestAssuredMockMvc
                .given()
                .contentType("application/json")
                .body(userPurchase)
                .when()
                .post("/makePurchase")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body(equalTo("This is not a valid item id"));
    }

    @Test
    public void makePurchaseOutofStockReturnsConflict () {

        Record recordTest = Record.builder()
                .id(1l)
                .name("Thriller")
                .artist("Michael Jackson")
                .quantity(0)
                .price(9.99)
                .build();

        lenient().when(purchaseServiceImpl.pullID(any(Map.class))).thenReturn(recordTest.getId());
        lenient().when(purchaseServiceImpl.checkIdExists(any(Map.class))).thenReturn(true);
        lenient().when(recordRepository.existsById(1l)).thenReturn(true);
        lenient().when(recordRepository.getReferenceById(1l)).thenReturn(recordTest);

        Map<String, Object> userPurchase = new HashMap<>();
        userPurchase.put("customer", "John");
        userPurchase.put("id", recordTest.getId());

        RestAssuredMockMvc
                .given()
                .contentType("application/json")
                .body(userPurchase)
                .when()
                .post("/makePurchase")
                .then()
                .statusCode(HttpStatus.CONFLICT.value())
                .body(equalTo("Item not in stock"));
    }
}
