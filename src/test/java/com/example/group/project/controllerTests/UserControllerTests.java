package com.example.group.project.controllerTests;

import com.example.group.project.controller.UserController;
import com.example.group.project.security.WebSecurityConfig;
import com.example.group.project.security.tokens.JwtGenerator;
import com.example.group.project.service.impl.UserDetailsServiceImpl;
import io.restassured.RestAssured;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = WebSecurityConfig.class)
@WebAppConfiguration
public class UserControllerTests {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
        RestAssuredMockMvc.mockMvc(mvc);

//        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
//        securityContext.setAuthentication(new UsernamePasswordAuthenticationToken("GillyT10", "goodbye", List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))));
//        SecurityContextHolder.setContext(securityContext);
    }

    @Test
//    @WithMockUser(username = "GillyT10", roles = {"ADMIN"} )
    public void Register_TooShortCustomer_ReturnsBadRequest () {
        String loginPayload = "{ \"username\": \"GillyT10\", \"password\": \"goodbye\" }";

        String token = RestAssured.given()
                .contentType("application/json")
                .body(loginPayload)
                .when()
                .post("/login")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .cookie("token");

        RestAssuredMockMvc
                .given()
                .header("Authorization", "Bearer " + token)
                .contentType("application/json")
                .when()
                .get("/auth/records")
                .then()
                .log().all()
                .statusCode(HttpStatus.OK.value());
    }

    /*
    Register:
    Test for missing parameters
    Test for missing info
    Test for username already in use
    Test for saving user

        Map<String, String> newUser = new HashMap<>();
        newUser.put("firstname", "");
        newUser.put("lastname", "John");
        newUser.put("username", "John1230");
        newUser.put("password", "password123");
        newUser.put("role", "STAFF");

    // Test only an admin can reach this page?

    Login:
    Test for bad values
    Test for good values -> mock authentication manager?
    Test for returned token as cookie

     */




}
