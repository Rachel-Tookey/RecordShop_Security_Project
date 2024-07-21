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
    public void pullIDConvertLongSuccess() {

        Map<String, Object> userPurchase = new HashMap<>();
        userPurchase.put("customer", "John");
        userPurchase.put("id", 1l);

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

        Map<String, Object> userPurchase = new HashMap<>();
        userPurchase.put("id", recordTest.getId());

        lenient().when(recordRepository.existsById(1l)).thenReturn(true);
        lenient().when(recordRepository.getReferenceById(1l)).thenReturn(recordTest);

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

        Map<String, Object> userPurchase = new HashMap<>();
        userPurchase.put("id", recordTest.getId());

        lenient().when(recordRepository.existsById(1l)).thenReturn(true);
        lenient().when(recordRepository.getReferenceById(1l)).thenReturn(recordTest);

        boolean checkStockTest = purchaseServiceImpl.checkStock(userPurchase);

        assertFalse(checkStockTest);

    }

    // check id exits:
    @Test
    public void checkIDExitsValidIDReturnsTrue() {
        Record recordTest = Record.builder()
                .name("Thriller")
                .artist("Michael Jackson")
                .quantity(0)
                .price(9.99)
                .build();

        Long recordTestID = 2l;
        recordTest.setId(recordTestID);

        Map<String, Object> userPurchase = new HashMap<>();
        userPurchase.put("id", 2l);

        lenient().when(recordRepository.existsById(recordTestID)).thenReturn(true);

        boolean checkStockTest = purchaseServiceImpl.checkIdExists(userPurchase);

        assertTrue(checkStockTest);

    }

    @Test
    public void checkIDExitsInvalidIDReturnsFalse() {

        Long testID = 2l;

        lenient().when(recordRepository.existsById(testID)).thenReturn(false);

        Map<String, Object> userPurchase = new HashMap<>();
        userPurchase.put("id", testID);

        boolean checkStockTest = purchaseServiceImpl.checkIdExists(userPurchase);

        assertFalse(checkStockTest);

    }

    // adjust price
    @Test
    public void adjustPriceValidInputNoDiscountReturnPrice(){
        Record recordTest = Record.builder()
                .name("Thriller")
                .artist("Michael Jackson")
                .quantity(0)
                .price(9.99)
                .build();

        Long recordTestID = 1l;
        recordTest.setId(recordTestID);

        Map<String, Object> userPurchase = new HashMap<>();
        userPurchase.put("id", 1l);

        when(recordRepository.getReferenceById(recordTestID)).thenReturn(recordTest);

        double recordPrice = purchaseServiceImpl.adjustPrice(userPurchase);

        assertEquals(recordTest.getPrice(), recordPrice);

    }

    @Test
    public void adjustPriceValidInputDiscountReturnPrice(){
        Record recordTest = Record.builder()
                .name("Thriller")
                .artist("Michael Jackson")
                .quantity(0)
                .price(9.99)
                .build();

        Long recordTestID = 1l;
        recordTest.setId(recordTestID);

        Map<String, Object> userPurchase = new HashMap<>();
        userPurchase.put("id", 1l);
        userPurchase.put("discount", "CFG");

        when(recordRepository.getReferenceById(recordTestID)).thenReturn(recordTest);

        double recordPrice = purchaseServiceImpl.adjustPrice(userPurchase);

        assertEquals(7.99, recordPrice);

    }

    @Test
    public void adjustPriceValidInputWrongDiscountReturnPrice(){
        Record recordTest = Record.builder()
                .name("Thriller")
                .artist("Michael Jackson")
                .quantity(0)
                .price(9.99)
                .build();

        Long recordTestID = 1l;
        recordTest.setId(recordTestID);

        Map<String, Object> userPurchase = new HashMap<>();
        userPurchase.put("id", 1l);
        userPurchase.put("discount", "cfg");

        when(recordRepository.getReferenceById(recordTestID)).thenReturn(recordTest);

        double recordPrice = purchaseServiceImpl.adjustPrice(userPurchase);

        assertEquals(9.99, recordPrice);

    }

    // commit purchase:
    @Test
    public void commitPurchaseValidInputReturnsID() {

        Map<String, Object> userPurchase = new HashMap<>();
        userPurchase.put("customer", "John");
        userPurchase.put("id", 1l);

        Record recordTest = Record.builder()
                .id(1l)
                .name("Thriller")
                .artist("Michael Jackson")
                .quantity(0)
                .price(9.99)
                .build();

        Long recordTestID = 1l;
        recordTest.setId(recordTestID);

        Purchase purchaseTest = Purchase.builder()
                .customer("John")
                .price(9.99)
                .date(DateUtil.getDate())
                .recordLink(recordTest)
                .build();


        lenient().when(recordRepository.getReferenceById(recordTestID)).thenReturn(recordTest);
        lenient().when(purchaseRepository.save(purchaseTest)).thenAnswer(invocation -> invocation.getArgument(0));

        purchaseServiceImpl.commitPurchase(userPurchase);

        verify(purchaseRepository).save(purchaseTest);


    }

}
