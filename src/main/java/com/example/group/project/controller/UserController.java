package com.example.group.project.controller;

import com.example.group.project.dto.AuthResponseDTO;
import com.example.group.project.dto.LoginRequestDTO;
import com.example.group.project.model.entity.User;
import com.example.group.project.security.GetCookies;
import com.example.group.project.security.JwtGenerator;
import com.example.group.project.service.impl.UserDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;


@Slf4j
@RestController()
public class UserController {

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private AuthenticationManager authenticationManager;

    // does it need authorisation?
    @GetMapping("/auth/getusers")
    public ResponseEntity<?> getUsers(@RequestHeader("Authorization") String authHeader){
        return ResponseEntity.status(HttpStatus.OK).body(userDetailsServiceImpl.getAllUsers());
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO request){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        if (authentication.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = JwtGenerator.generateToken(authentication.getName());

            HttpHeaders returnHeaders = new HttpHeaders();
            returnHeaders.add(HttpHeaders.SET_COOKIE, GetCookies.GetCookie(token));
            return ResponseEntity.status(HttpStatus.OK).headers(returnHeaders).body(new AuthResponseDTO(token));
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Login unsuccessful");
        }

    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody HashMap<String, String> newUser){
        log.info("Registering new user");
        User returnUser = User.builder()
                .username(newUser.get("username"))
                .password(userDetailsServiceImpl.hashPassword(newUser.get("password")))
                .role(newUser.get("role")).build();

        userDetailsServiceImpl.saveUser(returnUser);
        return ResponseEntity.status(HttpStatus.OK).body("New User!");
    }



}
