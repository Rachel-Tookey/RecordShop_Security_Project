package com.example.group.project.controllerTests;

import com.example.group.project.controller.UserController;
import com.example.group.project.service.impl.UserDetailsServiceImpl;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

    /*

    Create user controller tests to validate registration and login functionality
     */

@ExtendWith(MockitoExtension.class)
public class UserControllerTests {

    @Mock
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setUpMockUserController() { RestAssuredMockMvc.standaloneSetup(userController); }

    @Test
    public void register_MissingFirstNameParam_ReturnBadRequest() {

        Map<String, String> newUser = new HashMap<>();

        RestAssuredMockMvc
                .given()
                .contentType("application/json")
                .body(newUser)
                .when()
                .post("/register")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body(equalTo("Must contain firstname key"));
    }

    @Test
    public void register_MissingRoleParam_ReturnBadRequest() {

        Map<String, String> newUser = new HashMap<>();
        newUser.put("firstname", "John");
        newUser.put("lastname", "Jones");
        newUser.put("username", "JJ2014");
        newUser.put("password", "password123");


        RestAssuredMockMvc
                .given()
                .contentType("application/json")
                .body(newUser)
                .when()
                .post("/register")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body(equalTo("Must contain role key"));
    }

    @Test
    public void register_UsernameAlreadyInUse_ReturnBadRequest() {

        Map<String, String> newUser = new HashMap<>();
        newUser.put("firstname", "John");
        newUser.put("lastname", "Jones");
        newUser.put("username", "JJ2014");
        newUser.put("password", "password123");
        newUser.put("role", "ADMIN");

        when(userDetailsServiceImpl.checkUserExists("JJ2014")).thenReturn(true);


        RestAssuredMockMvc
                .given()
                .contentType("application/json")
                .body(newUser)
                .when()
                .post("/register")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body(equalTo("Username already in use"));
    }

    @Test
    public void register_NewUserCorrect_ReturnBadRequest() {

        Map<String, String> newUser = new HashMap<>();
        newUser.put("firstname", "John");
        newUser.put("lastname", "Jones");
        newUser.put("username", "JJ2014");
        newUser.put("password", "password123");
        newUser.put("role", "ADMIN");

        when(userDetailsServiceImpl.checkUserExists("JJ2014")).thenReturn(false);


        RestAssuredMockMvc
                .given()
                .contentType("application/json")
                .body(newUser)
                .when()
                .post("/register")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body(equalTo("New User!"));
    }





}
