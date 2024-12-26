package com.example.group.project.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.LinkedHashMap;
import java.util.Map;


@Slf4j
@RestControllerAdvice
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> errorPage(HttpServletRequest request, Exception ex){

        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        HttpStatus castStatus = (HttpStatus) status;
        Map<String, Object> body = new LinkedHashMap<>();
        String details = ex.getMessage();
        body.put("Error: ", ex.getMessage());

        log.error(details);

        if (status == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
        }

        String url = ((ServletWebRequest)request).getRequest().getRequestURI();
        body.put("You attempted to access the following URL", url);
        return ResponseEntity.status(castStatus).body(body);

    }


}
