package com.example.group.project.controller;

import com.example.group.project.LoginRequest;
import com.example.group.project.model.entity.User;
import com.example.group.project.service.impl.UserDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;


@Slf4j
@RestController()
public class StaffController {

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/auth/getusers")
    public ResponseEntity<?> getStaff(){
        return ResponseEntity.status(HttpStatus.OK).body(userDetailsServiceImpl.getAllUsers());
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        if (authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.OK).body("Login successful!");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Login unsuccessful");
        }

    }

    @PostMapping("/newuser")
    public ResponseEntity<?> newuser(@RequestBody HashMap<String, Object> newUser){
        User returnUser = User.builder()
                .username(newUser.get("Username").toString())
                .password(userDetailsServiceImpl.hashPassword(newUser.get("Password").toString()))
                .role(newUser.get("Role").toString()).build();

        userDetailsServiceImpl.saveUser(returnUser);
        return ResponseEntity.status(HttpStatus.OK).body("New User!");
    }



}
