package com.example.group.project.controller;

import com.example.group.project.service.impl.StaffServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class StaffController {

    @Autowired
    private StaffServiceImpl staffServiceImpl;

    @GetMapping("/staff")
    public ResponseEntity<?> getStaff(){
        return ResponseEntity.status(HttpStatus.OK).body("Test");
    }


}
