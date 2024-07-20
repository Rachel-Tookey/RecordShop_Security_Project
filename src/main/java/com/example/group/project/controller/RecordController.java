package com.example.group.project.controller;

import com.example.group.project.model.entity.Record;
import com.example.group.project.model.repository.RecordRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class RecordController {

    @Autowired
    RecordRepository recordRepository;

    @GetMapping("/getRecord")
    public ResponseEntity<List<Record>> getRecord(@RequestParam(required = false) String artist,
                                          @RequestParam(required = false) String name) {

        List<Record> records;
        if (artist != null) {
            log.debug("Artist name provided: {}", artist);
            records = recordRepository.findByArtistIgnoreCase(artist);

        } else if (name != null) {
            log.debug("Record name provided: {}", name);
            records = recordRepository.findByNameIgnoreCase(name);

        } else {
            log.debug("No param provided");
            records = recordRepository.findAll();
        }

        return ResponseEntity.ok(records);
    }
}
