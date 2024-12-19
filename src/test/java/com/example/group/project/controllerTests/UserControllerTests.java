package com.example.group.project.controllerTests;

import com.example.group.project.controller.PurchaseController;
import com.example.group.project.controller.UserController;
import com.example.group.project.service.impl.PurchaseServiceImpl;
import com.example.group.project.service.impl.UserDetailsServiceImpl;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserControllerTests {

    @Mock
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setUpMockPurchaseController() { RestAssuredMockMvc.standaloneSetup(userController); }

}
