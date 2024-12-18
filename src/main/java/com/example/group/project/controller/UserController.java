package com.example.group.project.controller;


import com.example.group.project.dto.AuthResponseDTO;
import com.example.group.project.dto.LoginRequestDTO;
import com.example.group.project.security.utils.GetCookies;
import com.example.group.project.security.tokens.JwtGenerator;
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

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO request){

        // check for null values?

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        if (authentication.isAuthenticated()) {
            log.info("Credentials authenticated");
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = JwtGenerator.generateToken(authentication.getName());

            HttpHeaders returnHeaders = new HttpHeaders();
            returnHeaders.add(HttpHeaders.SET_COOKIE, GetCookies.GetCookie(token));
            log.info("Returning token");
            return ResponseEntity.status(HttpStatus.OK).headers(returnHeaders).body(new AuthResponseDTO(token));
        } else {
            log.info("Credentials not authenticated");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Login unsuccessful");
        }

    }

    @PostMapping("/auth/register")
    public ResponseEntity<?> register(@RequestBody HashMap<String, String> newUser){
        log.info("Registering new user");

        String[] params = new String[] {"firstname", "lastname", "username", "password", "role"};

        for (String param : params) {
            if (!newUser.containsKey(param)) {
                log.info("User missing info");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Must contain " + param + " key");
            } else if (newUser.get(param) == null) {
                log.info("User invalid info");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Must contain " + param + " value");
            }
        }

        if (userDetailsServiceImpl.checkUserExists(newUser.get("username"))) {
            return ResponseEntity.status((HttpStatus.BAD_REQUEST)).body("Username already in use");
        }

        log.info("Saving user");
        userDetailsServiceImpl.saveUser(newUser);

        return ResponseEntity.status(HttpStatus.OK).body("New User!");
    }



}
