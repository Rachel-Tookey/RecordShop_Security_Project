package com.example.group.project.service.impl;

import com.example.group.project.constant.RecordParam;
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
    public RecordServiceImpl(RecordRepository recordRepository) {
        this.recordRepository = recordRepository;
        this.artist = RecordParam.ARTIST_PARAM;
        this.record = RecordParam.RECORD_PARAM;

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

    public List<Record> getRecordsByNameAndArtist(String recordName, String artist) {
        return recordRepository.findByNameAndArtistIgnoreCase(recordName, artist);
    }

    public List<Record> requestHandler(Map<String, String> param) throws ResourceNotFoundException,
            InvalidParameterException{

        if (param.containsKey(artist) && param.containsKey(record)) {
            List<Record> recordsByNameAndArtist = getRecordsByNameAndArtist(param.get(record), param.get(artist));
            if (recordsByNameAndArtist.isEmpty()) {
                String exceptionMessage =
                        "No record found with name "+ param.get(record) + " and artist " + param.get(artist);
                throw new ResourceNotFoundException(exceptionMessage);
            } else { return recordsByNameAndArtist; }

        } else if(param.containsKey(record)) {
            List<Record> recordByName = getRecordByName(param.get(record));
            if (recordByName.isEmpty()) {
                String exceptionMessage = "No record found with name " + param.get(record);
                throw new ResourceNotFoundException(exceptionMessage);
            } else { return recordByName; }

        } else if (param.containsKey(artist)) {
            List<Record> recordsByArtist = getRecordsByArtist(param.get(artist));
            if (recordsByArtist.isEmpty()) {
                String exceptionMessage = "No record found having artist " + param.get(artist);
                throw new ResourceNotFoundException(exceptionMessage);
            } else { return recordsByArtist; }

        } else if(param.isEmpty()){
            List<Record> allRecords = getAllRecords();
            if (allRecords.isEmpty()) {
                String exceptionMessage = "No records found in the database";
                throw new ResourceNotFoundException(exceptionMessage);
            } else { return allRecords; }
        } else {
            throw new InvalidParameterException("Invalid Parameters used in the request");
        }
    }
}
