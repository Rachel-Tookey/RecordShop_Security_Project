package com.example.group.project.service.impl;

import com.example.group.project.model.entity.Record;
import com.example.group.project.model.repository.RecordRepository;
import com.example.group.project.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecordServiceImpl implements RecordService {

    private final RecordRepository recordRepository;

    @Autowired
    public RecordServiceImpl(RecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    public List<Record> getAllRecords(){
        return recordRepository.findAll();
    }

    public List<Record> getRecordsByName(String recordName) {
        return recordRepository.findByNameIgnoreCase(recordName);
    }

    public List<Record> getRecordsByNameAndArtist(String artist, String recordName) {
        return recordRepository.findByNameAndArtistIgnoreCase(recordName, artist);
    }

}
