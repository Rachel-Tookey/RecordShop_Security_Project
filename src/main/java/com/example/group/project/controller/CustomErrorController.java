package com.example.group.project.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;


@Slf4j
@RestControllerAdvice
public class CustomErrorController implements ErrorController {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> errorPage(WebRequest request, Exception ex){

        Map<String, Object> body = new HashMap<>();
        String url = ((ServletWebRequest)request).getRequest().getRequestURI().toString();
        Object details = ex.getMessage();
        body.put("Further details", details);
        body.put("You attempted to access the following URL", url);

        log.error("User sent request to " + url + " and the following error occured: " + details);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }
}
