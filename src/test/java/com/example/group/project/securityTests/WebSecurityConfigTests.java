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
    public void GetPurchases_NoAuth_OkResponse()  throws Exception {
        mockMvc.perform(get("/getpurchases"))
                .andExpect(status().isOk());
    }

    @Test
    public void GetRecords_NoAuth_ForbiddenResponse()  throws Exception {
        mockMvc.perform(get("/auth/records"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void PostPurchase_NoAuth_ForbiddenResponse()  throws Exception {
        mockMvc.perform(post("/auth/purchase"))
                .andExpect(status().isForbidden());
    }


    @Test
    public void GetRequest_Auth_ForbiddenResponse()  throws Exception {
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
    public void LoginRequest_CorrectCredentials_OkResponse()  throws Exception {
        mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"username\": \"adminUser\", \"password\": \"goodbye\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void LoginRequest_CorrectCredentials_TokenResponse()  throws Exception {
        String tokenResult = mockMvc.perform(post("/login") .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"username\": \"adminUser\", \"password\": \"goodbye\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").exists())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String token = "Bearer " + JsonPath.read(tokenResult, "$.accessToken");
        Assert.notNull(token);

        MvcResult csrfResult = mockMvc.perform(get("/auth/records")
                        .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(header().exists("X-XSRF-TOKEN"))
                .andExpect(cookie().exists("XSRF-TOKEN"))
                .andReturn();

        String csrfTokenHeader = csrfResult.getResponse().getHeader("X-XSRF-TOKEN");
        String csrfTokenCookie = csrfResult.getResponse().getCookie("XSRF-TOKEN").getValue();

        Assert.notNull(csrfTokenHeader);
        Assert.notNull(csrfTokenCookie);

        when(purchaseServiceImpl.checkIdExists("4")).thenReturn(true);
        when(purchaseServiceImpl.checkStock("4")).thenReturn(true);

        mockMvc.perform(post("/auth/purchase")
                        .header("Authorization", token)
                        .header("X-XSRF-TOKEN", csrfTokenHeader)
                        .cookie(new Cookie("XSRF-TOKEN", csrfTokenCookie))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"customer\": \"john\", \"id\": \"4\"}"))
                .andExpect(status().isOk());


    }


}
