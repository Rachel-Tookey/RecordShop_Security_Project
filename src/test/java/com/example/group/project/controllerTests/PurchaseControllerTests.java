package com.example.group.project.controllerTests;

import com.example.group.project.controller.PurchaseController;
import com.example.group.project.model.repository.PurchaseRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class PurchaseControllerTests {

    @Mock
    private PurchaseRepository purchaseRepository;

    @InjectMocks
    private PurchaseController purchaseController;

}
