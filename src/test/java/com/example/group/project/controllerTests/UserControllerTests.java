package com.example.group.project.controllerTests;

import com.example.group.project.controller.UserController;
import com.example.group.project.security.WebSecurityConfig;
import com.example.group.project.service.impl.UserDetailsServiceImpl;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@Import(WebSecurityConfig.class)
@ExtendWith(SpringExtension.class)
public class UserControllerTests {

    @MockBean
    private UserDetailsServiceImpl userDetailsServiceImpl;


    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        UserDetails userDetails = User.builder()
                .username("testUser")
                .password("password")
                .roles("ADMIN")
                .build();

        when(userDetailsServiceImpl.loadUserByUsername("testUser"))
                .thenReturn(userDetails);
    }

    @Test
    public void GetRequest_NoAuth_OkResponse()  throws Exception {
        mockMvc.perform(get("/getusers"))
                .andExpect(status().isOk());
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
        UserDetails userDetails = User.builder()
                .username("rightUser")
                .password("$2a$10$kTJMa8NiWpcGW8GC.NtUy.q28VUCpO5H1/v9exYNr8BgUgN2yWi4q")
                .roles("ADMIN")
                .build();

        when(userDetailsServiceImpl.loadUserByUsername("rightUser"))
                .thenReturn(userDetails);

        mockMvc.perform(post("/login") .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"username\": \"rightUser\", \"password\": \"goodbye\"}"))
                .andExpect(status().isOk());
    }




}
