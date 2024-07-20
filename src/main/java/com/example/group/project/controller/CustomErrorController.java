package com.example.group.project.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public ResponseEntity<?> errorPage(HttpServletRequest request){
        String url = (String) request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);
        log.error("User sent request to " + url + " and an error message has been thrown");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error has occurred. Please try again later");
    }
}
