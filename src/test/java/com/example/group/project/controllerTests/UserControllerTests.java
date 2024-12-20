package com.example.group.project.controllerTests;

import com.example.group.project.security.WebSecurityConfig;
import io.jsonwebtoken.lang.Assert;
import io.restassured.RestAssured;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
//        securityContext.setAuthentication(new UsernamePasswordAuthenticationToken("GillyT11", "goodbye1", List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))));
//        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    public void Login_CorrectCredentials_ReturnsOkToken () {
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

        Assert.notNull(token);
    }



}
