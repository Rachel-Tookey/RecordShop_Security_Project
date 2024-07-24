package com.example.group.project.controller;

import com.example.group.project.exceptions.InvalidParameterException;
import com.example.group.project.exceptions.ResourceNotFoundException;
import com.example.group.project.service.impl.RecordServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class  RecordController {
    @Autowired
    private RecordServiceImpl recordServiceImpl;

    // GET endpoint to view records
    @GetMapping("/records")
    // handles request parameters and calls the method; will return the appropriate HTTP response based on the outcome/exceptions
    public ResponseEntity<?> getRecords(@RequestParam(required = false) Map<String, String> requestParams) {
        try {
            Map<String, String> params = (requestParams == null) ? new HashMap<>(Map.of()) : requestParams;

               return ResponseEntity.status(HttpStatus.OK).body(recordServiceImpl.requestHandler(params));
        } catch (InvalidParameterException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
