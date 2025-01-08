package com.example.group.project.serviceTests;

import com.example.group.project.model.repository.PurchaseRepository;
import com.example.group.project.model.repository.RecordRepository;
import com.example.group.project.model.repository.UserRepository;
import com.example.group.project.service.impl.PurchaseServiceImpl;
import com.example.group.project.service.impl.UserDetailsServiceImpl;
import io.jsonwebtoken.lang.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceImplTests {

    @Mock
    private UserRepository userRepository;

    //
//    @Autowired
//    private PasswordEncoder PasswordEncoder;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void hashPassword_returnsHashedPassword() {

        String passwordToHash = "goodbye";

        String hashedPassword = userDetailsServiceImpl.hashPassword(passwordToHash);

        assertNotEquals(passwordToHash, hashedPassword);

    }


}
