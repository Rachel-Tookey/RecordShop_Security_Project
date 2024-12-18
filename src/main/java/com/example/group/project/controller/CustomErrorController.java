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

    public static HttpStatus getStatusFromCode(int code) {
        for (HttpStatus status : HttpStatus.values()) {
            if (status.value() == code) {
                return status;
            }
        }
        return HttpStatus.NOT_FOUND;
    }

    @RequestMapping("/error")
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> errorPage(HttpServletRequest request, Exception ex){

        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        Integer statusCode = Integer.valueOf(status.toString());
        HttpStatus castStatus = getStatusFromCode(statusCode);

        Map<String, Object> body = new LinkedHashMap<>();
        String url = ((ServletWebRequest)request).getRequest().getRequestURI();
        Object details = ex.getMessage();
        body.put("You attempted to access the following URL", url);
        body.put("Further details", details);

        log.error("User sent request to " + url + " and the following error occured: " + details);

        return ResponseEntity.status(castStatus).body(body);

    }


}
