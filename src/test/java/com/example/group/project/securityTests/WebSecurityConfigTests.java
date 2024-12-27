package com.example.group.project.securityTests;

import com.example.group.project.controller.PurchaseController;
import com.example.group.project.controller.RecordController;
import com.example.group.project.controller.UserController;
import com.example.group.project.security.WebSecurityConfig;
import com.example.group.project.service.impl.PurchaseServiceImpl;
import com.example.group.project.service.impl.RecordServiceImpl;
import com.example.group.project.service.impl.UserDetailsServiceImpl;
import com.jayway.jsonpath.JsonPath;
import io.jsonwebtoken.lang.Assert;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest({UserController.class, RecordController.class, PurchaseController.class})
@Import(WebSecurityConfig.class)
@ExtendWith(SpringExtension.class)
public class WebSecurityConfigTests {

    @MockBean
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @MockBean
    private PurchaseServiceImpl purchaseServiceImpl;

    @MockBean
    private RecordServiceImpl recordServiceImpl;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        UserDetails userDetails = User.builder()
                .username("adminUser")
                .password("$2a$10$kTJMa8NiWpcGW8GC.NtUy.q28VUCpO5H1/v9exYNr8BgUgN2yWi4q")
                .roles("ADMIN")
                .build();

        when(userDetailsServiceImpl.loadUserByUsername("adminUser"))
                .thenReturn(userDetails);


    }

    @Test
    public void GetWelcomeRequest_NoAuth_200Response()  throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk());
    }

    @Test
    public void GetRecords_NoAuth_403Response()  throws Exception {
        mockMvc.perform(get("/auth/records"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void PostPurchase_NoAuth_403Response()  throws Exception {
        mockMvc.perform(post("/auth/purchase"))
                .andExpect(status().isForbidden());
    }


    @Test
    public void GetRequest_Auth_403Response()  throws Exception {
        mockMvc.perform(post("/auth/register"))
                .andExpect(status().isForbidden());
    }


    @Test
    public void LoginRequest_BadCredentials_404Response()  throws Exception {
        mockMvc.perform(post("/login") .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"username\": \"wrongUser\", \"password\": \"wrongpassword\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void LoginRequest_CorrectCredentials_200Response()  throws Exception {
        mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"username\": \"adminUser\", \"password\": \"goodbye\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void Login_CorrectCredentials_ReceiveJWTToken()  throws Exception {
        String tokenResult = mockMvc.perform(post("/login") .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"username\": \"adminUser\", \"password\": \"goodbye\"}"))
                .andExpect(jsonPath("$.accessToken").exists())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String token = "Bearer " + JsonPath.read(tokenResult, "$.accessToken");
        Assert.notNull(token);
    }


    @Test
    public void AuthGetRequest_UseJWTToken_200Response()  throws Exception {
        String tokenResult = mockMvc.perform(post("/login") .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"username\": \"adminUser\", \"password\": \"goodbye\"}"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        String token = "Bearer " + JsonPath.read(tokenResult, "$.accessToken");

        mockMvc.perform(get("/auth/records")
                        .header("Authorization", token))
                .andExpect(status().isOk());
    }


    @Test
    public void AuthGetRequest_NoToken_403Response()  throws Exception {
        mockMvc.perform(get("/auth/records"))
                .andExpect(status().isForbidden());
    }


    @Test
    public void AuthGetRequest_InvalidToken_403Response()  throws Exception {
        mockMvc.perform(get("/auth/records")
                        .header("Authorization", "1234"))
                .andExpect(status().isForbidden());
    }


    @Test
    public void AuthGetRequest_UseToken_ReceiveCSRFHeaderAndCookie()  throws Exception {
        String tokenResult = mockMvc.perform(post("/login") .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"username\": \"adminUser\", \"password\": \"goodbye\"}"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        String token = "Bearer " + JsonPath.read(tokenResult, "$.accessToken");

        MvcResult csrfResult = mockMvc.perform(get("/auth/records")
                        .header("Authorization", token))
                .andExpect(header().exists("X-XSRF-TOKEN"))
                .andExpect(cookie().exists("XSRF-TOKEN"))
                .andReturn();

        String csrfTokenHeader = csrfResult.getResponse().getHeader("X-XSRF-TOKEN");
        String csrfTokenCookie = csrfResult.getResponse().getCookie("XSRF-TOKEN").getValue();

        Assert.notNull(csrfTokenHeader);
        Assert.notNull(csrfTokenCookie);
    }

    @Test
    public void AuthPostRequest_JWTAndCSRFTokens_200Response()  throws Exception {
        String tokenResult = mockMvc.perform(post("/login") .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"username\": \"adminUser\", \"password\": \"goodbye\"}"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        String token = "Bearer " + JsonPath.read(tokenResult, "$.accessToken");

        MvcResult csrfResult = mockMvc.perform(get("/auth/records")
                        .header("Authorization", token))
                .andReturn();

        String csrfTokenHeader = csrfResult.getResponse().getHeader("X-XSRF-TOKEN");
        String csrfTokenCookie = csrfResult.getResponse().getCookie("XSRF-TOKEN").getValue();

        when(purchaseServiceImpl.checkProductIdExists("4")).thenReturn(true);
        when(purchaseServiceImpl.checkStock("4")).thenReturn(true);

        mockMvc.perform(post("/auth/purchase")
                        .header("Authorization", token)
                        .header("X-XSRF-TOKEN", csrfTokenHeader)
                        .cookie(new Cookie("XSRF-TOKEN", csrfTokenCookie))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"customer\": \"john\", \"id\": \"4\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void AuthPostRequest_ValidJWTAndInvalidCSRFTokens_403Response()  throws Exception {
        String tokenResult = mockMvc.perform(post("/login") .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"username\": \"adminUser\", \"password\": \"goodbye\"}"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        String token = "Bearer " + JsonPath.read(tokenResult, "$.accessToken");

        mockMvc.perform(post("/auth/purchase")
                        .header("Authorization", token)
                        .header("X-XSRF-TOKEN", "1234")
                        .cookie(new Cookie("XSRF-TOKEN", "1234"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"customer\": \"john\", \"id\": \"4\"}"))
                .andExpect(status().isForbidden());
    }


    @Test
    public void AuthPostRequest_UseOnlyJWToken_403Response()  throws Exception {
        String tokenResult = mockMvc.perform(post("/login") .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"username\": \"adminUser\", \"password\": \"goodbye\"}"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        String token = "Bearer " + JsonPath.read(tokenResult, "$.accessToken");

        mockMvc.perform(post("/auth/purchase")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"customer\": \"john\", \"id\": \"4\"}"))
                .andExpect(status().isForbidden());
    }

    /*

    Role based checking

     */


}
