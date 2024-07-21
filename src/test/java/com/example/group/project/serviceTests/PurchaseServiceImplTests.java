package com.example.group.project.serviceTests;


import com.example.group.project.controller.PurchaseController;
import com.example.group.project.controller.RecordController;
import com.example.group.project.model.entity.Record;
import com.example.group.project.model.repository.PurchaseRepository;
import com.example.group.project.model.repository.RecordRepository;
import com.example.group.project.service.implementation.PurchaseServiceImpl;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

import java.util.HashMap;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
public class PurchaseServiceImplTests {

    @Mock
    private PurchaseRepository purchaseRepository;

    @Mock
    private RecordRepository recordRepository;

    @Mock
    private PurchaseServiceImpl purchaseServiceImpl;

    @InjectMocks
    private PurchaseController purchaseController;

    @InjectMocks
    private RecordController recordController;

    @BeforeEach
    public void setUpMockRecordController() {
        RestAssuredMockMvc.standaloneSetup(recordController);
    }

    @BeforeEach
    public void setUpMockPurchaseController() { RestAssuredMockMvc.standaloneSetup(purchaseController); }

    // testing pullID
    @Test
    public void pullIDConvertLongSuccess() {

        Map<String, Object> userPurchase = new HashMap<>();
        userPurchase.put("customer", "John");
        userPurchase.put("id", 1);

        Long testReturn = purchaseServiceImpl.pullID(userPurchase);

        assertTrue(testReturn instanceof Long);

    }

    // check stock
    @Test
    public void checkStockReturnsTrue() {
        Record recordTest = Record.builder()
                .id(1l)
                .name("Thriller")
                .artist("Michael Jackson")
                .quantity(1)
                .price(9.99)
                .build();

        lenient().when(purchaseServiceImpl.pullID(any(Map.class))).thenReturn(recordTest.getId());
        lenient().when(purchaseServiceImpl.checkStock(any(Map.class))).thenReturn(true);

        Map<String, Object> userPurchase = new HashMap<>();
        userPurchase.put("id", recordTest.getId());

        boolean checkStockTest = purchaseServiceImpl.checkStock(userPurchase);

        assertTrue(checkStockTest);

    }

    @Test
    public void checkStockReturnsFalse() {
        Record recordTest = Record.builder()
                .id(1l)
                .name("Thriller")
                .artist("Michael Jackson")
                .quantity(0)
                .price(9.99)
                .build();

        lenient().when(purchaseServiceImpl.pullID(any(Map.class))).thenReturn(recordTest.getId());
        lenient().when(purchaseServiceImpl.checkStock(any(Map.class))).thenReturn(false);

        Map<String, Object> userPurchase = new HashMap<>();
        userPurchase.put("id", recordTest.getId());

        boolean checkStockTest = purchaseServiceImpl.checkStock(userPurchase);

        assertFalse(checkStockTest);

    }



}
