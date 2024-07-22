package com.example.group.project.service.impl;

import com.example.group.project.exceptions.InvalidParameterException;
import com.example.group.project.exceptions.ResourceNotFoundException;
import com.example.group.project.model.entity.Record;
import com.example.group.project.model.repository.RecordRepository;
import com.example.group.project.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RecordServiceImpl implements RecordService {
    private final String artist;
    private final String record;

    @Autowired
    private final RecordRepository recordRepository;

    @Autowired
    public RecordServiceImpl(RecordRepository recordRepository, String ARTIST_PARAM,
                             String RECORD_PARAM) {
        this.recordRepository = recordRepository;
        this.artist = ARTIST_PARAM;
        this.record = RECORD_PARAM;

    }

    public List<Record> getAllRecords(){
        List<Record> allRecords = recordRepository.findAll();
        if (allRecords.isEmpty()) {
            throw new ResourceNotFoundException("No records found in the database.");
        }
        return allRecords;

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

    public List<Record> paramHandler(Map<String, String> param) {
        if (param.containsKey(artist) && param.containsKey(record)) {
            return getRecordsByNameAndArtist(param.get(record), param.get(artist));

        } else if(param.containsKey(record)) {
            return getRecordByName(param.get(artist));

        } else if (param.containsKey(artist)) {
            return getRecordsByArtist(param.get(artist));

        } else if(param.isEmpty()){
            return getAllRecords();

        } else {
            throw new InvalidParameterException("Invalid Parameters used in the request");
        }
    }
}
