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

    @Test
    public void pullId_ConvertLong_Success() {

        String inputId = "1";
        Long testReturn = purchaseServiceImpl.parseId(inputId);
        assertInstanceOf(Long.class, testReturn);
        assertEquals(testReturn, 1L);

    }

    @Test
    public void checkStock_Called_ReturnsTrue() {
        Record recordTest = Record.builder()
                .id(1L)
                .name("Thriller")
                .artist("Michael Jackson")
                .quantity(1)
                .price(9.99)
                .build();

        when(recordRepository.getReferenceById(1L)).thenReturn(recordTest);

        boolean checkStockTest = purchaseServiceImpl.checkStock("1");

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

        Map<String, String> userPurchase = new HashMap<>();
        userPurchase.put("id", "1");

        when(recordRepository.getReferenceById(1L)).thenReturn(recordTest);

        boolean checkStockTest = purchaseServiceImpl.checkStock("1");

        assertFalse(checkStockTest);

    }

    @Test
    public void checkIdExists_ValidId_ReturnsTrue() {
        Record recordTest = Record.builder()
                .name("Thriller")
                .artist("Michael Jackson")
                .quantity(0)
                .price(9.99)
                .build();

        Long recordTestID = 2L;
        recordTest.setId(recordTestID);

        when(recordRepository.existsById(recordTestID)).thenReturn(true);

        boolean checkStockTest = purchaseServiceImpl.checkIdExists("2");

        assertTrue(checkStockTest);
    }

    @Test
    public void checkIdExists_InvalidId_ReturnsFalse() {

        Long testID = 2L;

        when(recordRepository.existsById(testID)).thenReturn(false);

        boolean checkStockTest = purchaseServiceImpl.checkIdExists("2");

        assertFalse(checkStockTest);

    }

    @Test
    public void adjustPrice_ValidInputNoDiscount_ReturnPrice(){
        Record recordTest = Record.builder()
                .name("Thriller")
                .artist("Michael Jackson")
                .quantity(0)
                .price(9.99)
                .build();

        Long recordTestId = 1L;
        recordTest.setId(recordTestId);

        double recordPrice = purchaseServiceImpl.adjustPrice(recordTest.getPrice(), false);

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

        Long recordTestId = 1L;
        recordTest.setId(recordTestId);

        double recordPrice = purchaseServiceImpl.adjustPrice(recordTest.getPrice(), true);

        assertEquals(7.99, recordPrice);

    }


    @Test
    public void commitPurchase_ValidInput_MakesSave() {

        Map<String, String> userPurchase = new HashMap<>();
        userPurchase.put("customer", "John");
        userPurchase.put("id", "1");

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
