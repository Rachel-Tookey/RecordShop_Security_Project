package com.example.group.project.serviceTests;

import com.example.group.project.model.entity.Purchase;
import com.example.group.project.model.entity.Record;
import com.example.group.project.model.repository.PurchaseRepository;
import com.example.group.project.model.repository.RecordRepository;
import com.example.group.project.service.impl.PurchaseServiceImpl;
import com.example.group.project.util.DateUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.HashMap;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
public class PurchaseServiceImplTests {

    @Mock
    private PurchaseRepository purchaseRepository;

    @Mock
    private RecordRepository recordRepository;

    @InjectMocks
    private PurchaseServiceImpl purchaseServiceImpl;

    @BeforeEach
    public void setUp() {
    }

    // testing pullID
    @Test
    public void pullID_ConvertLong_Success() {

        Map<String, Object> userPurchase = new HashMap<>();
        userPurchase.put("customer", "John");
        userPurchase.put("id", 1L);

        Long testReturn = purchaseServiceImpl.pullID(userPurchase);

        assertTrue(testReturn instanceof Long);

    }

    // check stock
    @Test
    public void checkStock_Called_ReturnsTrue() {
        Record recordTest = Record.builder()
                .id(1L)
                .name("Thriller")
                .artist("Michael Jackson")
                .quantity(1)
                .price(9.99)
                .build();

        Map<String, Object> userPurchase = new HashMap<>();
        userPurchase.put("id", recordTest.getId());

        when(recordRepository.getReferenceById(1L)).thenReturn(recordTest);

        boolean checkStockTest = purchaseServiceImpl.checkStock(userPurchase);

        assertTrue(checkStockTest);

    }

    @Test
    public void checkStock_Called_ReturnsFalse() {
        Record recordTest = Record.builder()
                .id(1L)
                .name("Thriller")
                .artist("Michael Jackson")
                .quantity(0)
                .price(9.99)
                .build();

        Map<String, Object> userPurchase = new HashMap<>();
        userPurchase.put("id", recordTest.getId());

        when(recordRepository.getReferenceById(1L)).thenReturn(recordTest);

        boolean checkStockTest = purchaseServiceImpl.checkStock(userPurchase);

        assertFalse(checkStockTest);

    }

    // check id exits:
    @Test
    public void checkIDExists_ValidID_ReturnsTrue() {
        Record recordTest = Record.builder()
                .name("Thriller")
                .artist("Michael Jackson")
                .quantity(0)
                .price(9.99)
                .build();

        Long recordTestID = 2L;
        recordTest.setId(recordTestID);

        Map<String, Object> userPurchase = new HashMap<>();
        userPurchase.put("id", 2L);

        when(recordRepository.existsById(recordTestID)).thenReturn(true);

        boolean checkStockTest = purchaseServiceImpl.checkIdExists(userPurchase);

        assertTrue(checkStockTest);

    }

    @Test
    public void checkIDExists_InvalidID_ReturnsFalse() {

        Long testID = 2L;

        when(recordRepository.existsById(testID)).thenReturn(false);

        Map<String, Object> userPurchase = new HashMap<>();
        userPurchase.put("id", testID);

        boolean checkStockTest = purchaseServiceImpl.checkIdExists(userPurchase);

        assertFalse(checkStockTest);

    }

    // adjust price
    @Test
    public void adjustPrice_ValidInputNoDiscount_ReturnPrice(){
        Record recordTest = Record.builder()
                .name("Thriller")
                .artist("Michael Jackson")
                .quantity(0)
                .price(9.99)
                .build();

        Long recordTestID = 1L;
        recordTest.setId(recordTestID);

        Map<String, Object> userPurchase = new HashMap<>();
        userPurchase.put("id", 1L);

        when(recordRepository.getReferenceById(recordTestID)).thenReturn(recordTest);

        double recordPrice = purchaseServiceImpl.adjustPrice(userPurchase);

        assertEquals(recordTest.getPrice(), recordPrice);

    }

    @Test
    public void adjustPrice_ValidInputDiscount_ReturnPrice(){
        Record recordTest = Record.builder()
                .name("Thriller")
                .artist("Michael Jackson")
                .quantity(0)
                .price(9.99)
                .build();

        Long recordTestID = 1L;
        recordTest.setId(recordTestID);

        Map<String, Object> userPurchase = new HashMap<>();
        userPurchase.put("id", 1L);
        userPurchase.put("discount", "CFG");

        when(recordRepository.getReferenceById(recordTestID)).thenReturn(recordTest);

        double recordPrice = purchaseServiceImpl.adjustPrice(userPurchase);

        assertEquals(7.99, recordPrice);

    }

    @Test
    public void adjustPrice_ValidInputWrongDiscount_ReturnPrice(){
        Record recordTest = Record.builder()
                .name("Thriller")
                .artist("Michael Jackson")
                .quantity(0)
                .price(9.99)
                .build();

        Long recordTestID = 1L;
        recordTest.setId(recordTestID);

        Map<String, Object> userPurchase = new HashMap<>();
        userPurchase.put("id", 1L);
        userPurchase.put("discount", "cfg");

        when(recordRepository.getReferenceById(recordTestID)).thenReturn(recordTest);

        double recordPrice = purchaseServiceImpl.adjustPrice(userPurchase);

        assertEquals(9.99, recordPrice);

    }

    // commit purchase:
    @Test
    public void commitPurchase_ValidInput_ReturnsID() {

        Map<String, Object> userPurchase = new HashMap<>();
        userPurchase.put("customer", "John");
        userPurchase.put("id", 1L);

        Record recordTest = Record.builder()
                .id(1L)
                .name("Thriller")
                .artist("Michael Jackson")
                .quantity(0)
                .price(9.99)
                .build();

        Long recordTestID = 1L;
        recordTest.setId(recordTestID);

        Purchase purchaseTest = Purchase.builder()
                .customer("John")
                .price(9.99)
                .date(DateUtil.getDate())
                .recordLink(recordTest)
                .build();


        when(recordRepository.getReferenceById(recordTestID)).thenReturn(recordTest);
        when(purchaseRepository.save(purchaseTest)).thenAnswer(invocation -> invocation.getArgument(0));

        purchaseServiceImpl.commitPurchase(userPurchase);

        verify(purchaseRepository).save(purchaseTest);


    }

}
