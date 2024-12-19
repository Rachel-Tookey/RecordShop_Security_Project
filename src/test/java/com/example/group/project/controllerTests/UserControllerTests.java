package com.example.group.project.controllerTests;

import com.example.group.project.controller.UserController;
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

    /*
    Register:
    Test for missing parameters
    Test for missing info
    Test for username already in use
    Test for saving user

    // Test only an admin can reach this page?

    Login:
    Test for bad values
    Test for good values -> mock authentication manager?
    Test for returned token as cookie

     */


}
