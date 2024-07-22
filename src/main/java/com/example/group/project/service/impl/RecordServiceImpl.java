package com.example.group.project.service.impl;

import com.example.group.project.model.entity.Record;
import com.example.group.project.model.repository.RecordRepository;
import com.example.group.project.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RecordServiceImpl implements RecordService {
    @Autowired
    private final RecordRepository recordRepository;

    @Autowired
    public RecordServiceImpl(RecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    public List<Record> getAllRecords(){
        return recordRepository.findAll();
    }

    public List<Record> getRecordByName(String recordName) {
        return recordRepository.findByNameIgnoreCase(recordName);
    }

    public List<Record> getRecordsByArtist(String artist) {
        return recordRepository.findByArtistIgnoreCase(artist);
    }

    public List<Record> getRecordsByNameAndArtist(String artist, String recordName) {
        return recordRepository.findByNameAndArtistIgnoreCase(recordName, artist);
    }

}
