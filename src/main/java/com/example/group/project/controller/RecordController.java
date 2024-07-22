package com.example.group.project.controller;

import com.example.group.project.constant.RecordParam;
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


import java.util.Map;

@Slf4j
@RestController
public class  RecordController {
    @Autowired
    public RecordServiceImpl recordServiceImpl;

    @GetMapping("/getRecord")
    public ResponseEntity<?> getRecord(@RequestParam(required = false) String artist,
                                          @RequestParam(required = false) String name) {
        try {
            Map<String, String> params = Map.of(RecordParam.RECORD_PARAM, name, RecordParam.ARTIST_PARAM, artist);
            return ResponseEntity.status(HttpStatus.OK).body(recordServiceImpl.requestHandler(params));
        } catch (InvalidParameterException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
