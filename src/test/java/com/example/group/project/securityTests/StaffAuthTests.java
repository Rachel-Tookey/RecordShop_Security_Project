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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({UserController.class, RecordController.class, PurchaseController.class})
@Import(WebSecurityConfig.class)
@ExtendWith(SpringExtension.class)
public class StaffAuthTests {

    @MockBean
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @MockBean
    private PurchaseServiceImpl purchaseServiceImpl;

    @MockBean
    private RecordServiceImpl recordServiceImpl;

    @Autowired
    private MockMvc mockMvc;

    private String staffTokenResponse;

    private String csrfTokenHeader;

    private String csrfTokenCookie;

    @BeforeEach
    public void setup() throws Exception {
        UserDetails staffDetails = User.builder()
                .username("staffUser")
                .password("$2a$10$kTJMa8NiWpcGW8GC.NtUy.q28VUCpO5H1/v9exYNr8BgUgN2yWi4q")
                .roles("STAFF")
                .build();

        when(userDetailsServiceImpl.loadUserByUsername("staffUser"))
                .thenReturn(staffDetails);

        staffTokenResponse = "Bearer " + JsonPath.read(mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"username\": \"staffUser\", \"password\": \"goodbye\"}"))
                .andReturn()
                .getResponse()
                .getContentAsString(), "$.accessToken");

        MvcResult csrfResult = mockMvc.perform(get("/auth/records"))
                .andReturn();

        csrfTokenHeader = csrfResult.getResponse().getHeader("X-XSRF-TOKEN");
        csrfTokenCookie = csrfResult.getResponse().getCookie("XSRF-TOKEN").getValue();

    }

    @Test
    public void AuthGetRecords_UseJWTToken_200Response() throws Exception {
        mockMvc.perform(get("/auth/records"))
                .andExpect(status().isOk());
    }

    @Test
    public void AuthGetPurchases_UseJWTToken_200Response() throws Exception {
        mockMvc.perform(get("/auth/getPurchases"))
                .andExpect(status().isOk());
    }


    @Test
    public void AuthGetRequest_UseToken_ReceiveCSRFHeaderAndCookie() throws Exception {
        MvcResult csrfResult = mockMvc.perform(get("/auth/records"))
                .andExpect(header().exists("X-XSRF-TOKEN"))
                .andExpect(cookie().exists("XSRF-TOKEN"))
                .andReturn();

        String csrfTokenHeader = csrfResult.getResponse().getHeader("X-XSRF-TOKEN");
        String csrfTokenCookie = csrfResult.getResponse().getCookie("XSRF-TOKEN").getValue();

        Assert.notNull(csrfTokenHeader);
        Assert.notNull(csrfTokenCookie);
    }


    @Test
    public void AuthPostRequest_ValidJWTAndInvalidCSRFTokens_403Response() throws Exception {
        mockMvc.perform(post("/auth/purchase")
                        .header("X-XSRF-TOKEN", "1234")
                        .cookie(new Cookie("XSRF-TOKEN", "1234"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"customer\": \"john\", \"id\": \"4\"}"))
                .andExpect(status().isForbidden());
    }


    @Test
    public void AuthPostRequest_UseOnlyJWToken_403Response() throws Exception {

        mockMvc.perform(post("/auth/purchase")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"customer\": \"john\", \"id\": \"4\"}"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void AuthPostRequest_JWTAndCSRFTokens_200Response() throws Exception {

        when(purchaseServiceImpl.checkProductIdExists("4")).thenReturn(true);
        when(purchaseServiceImpl.checkStock("4")).thenReturn(true);

        mockMvc.perform(post("/auth/purchase")
                        .header("X-XSRF-TOKEN", csrfTokenHeader)
                        .cookie(new Cookie("XSRF-TOKEN", csrfTokenCookie))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"customer\": \"john\", \"id\": \"4\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void AuthDeleteRequest_CorrectTokensWrongRole_403Response() throws Exception {

        mockMvc.perform(delete("/auth/deletePurchase")
                        .param("id", "1")
                        .header("X-XSRF-TOKEN", csrfTokenHeader)
                        .cookie(new Cookie("XSRF-TOKEN", csrfTokenCookie)))
                .andExpect(status().isForbidden());
    }



}

