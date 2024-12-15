package com.example.group.project.controller;

import com.example.group.project.LoginRequest;
import com.example.group.project.service.impl.UserDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController("auth")
public class StaffController {

    @Autowired
    private UserDetailsServiceImpl userServiceImpl;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/getusers")
    public ResponseEntity<?> getStaff(){
        return ResponseEntity.status(HttpStatus.OK).body(userServiceImpl.getAllUsers());
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request){
        //String token = JwtUtil.generateToken(request.getUsername());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        if (authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.OK).body("Login successful!");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Login unsuccessful");
        }

    }

}
