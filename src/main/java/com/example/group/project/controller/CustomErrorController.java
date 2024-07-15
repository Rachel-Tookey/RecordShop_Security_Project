package com.example.group.project.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Going to wait for feedback from my assignment before using this:

@Slf4j
@RestController
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String errorPage(HttpServletRequest request){
        String url = (String) request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);
        log.error("User sent request to " + url + " and an error message has been thrown");
        return "An error has occured. Please try again later";
    }

}
