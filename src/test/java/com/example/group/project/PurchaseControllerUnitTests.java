package com.example.group.project;

import com.example.group.project.controller.PurchaseController;
import com.example.group.project.model.repository.PurchaseRepository;
import org.junit.jupiter.api.BeforeAll;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import static io.restassured.RestAssured.*;


@SpringBootTest
public class PurchaseControllerUnitTests {

    @Mock
    private PurchaseRepository purchaseRepository;

    @InjectMocks
    private PurchaseController purchaseController;



    //@BeforeAll
    //static void setup(){
    //    baseURI = "http://localhost:8080";
    //}



}
