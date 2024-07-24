package com.example.group.project.service.impl;

import com.example.group.project.constant.RecordParam;
import com.example.group.project.exceptions.InvalidParameterException;
import com.example.group.project.exceptions.ResourceNotFoundException;
import com.example.group.project.model.entity.Record;
import com.example.group.project.model.repository.RecordRepository;
import com.example.group.project.service.RecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class RecordServiceImpl implements RecordService {

    private final String artist;
    private final String record;
    private final RecordRepository recordRepository;

    public RecordServiceImpl(RecordRepository recordRepository) {
        this.recordRepository = recordRepository;
        this.artist = RecordParam.ARTIST_PARAM;
        this.record = RecordParam.RECORD_PARAM;
    }

    @Override
    public List<Record> getAllRecords(){
        return recordRepository.findAll();
    }

    @Override
    public List<Record> getRecordByName(String recordName) {
        return recordRepository.findByNameIgnoreCase(recordName);
    }

    @Override
    public List<Record> getRecordsByArtist(String artist) {
        return recordRepository.findByArtistIgnoreCase(artist);
    }

    @Override
    public List<Record> getRecordsByNameAndArtist(String recordName, String artist) {
        return recordRepository.findByNameAndArtistIgnoreCase(recordName, artist);
    }

    @Override
    public List<Record> requestHandler(Map<String, String> param) throws ResourceNotFoundException,
            InvalidParameterException{

        if (param.containsKey(artist) && param.containsKey(record)) {
            log.debug("Artist and Record params inserted.");
            List<Record> recordsByNameAndArtist = getRecordsByNameAndArtist(param.get(record), param.get(artist));
            if (recordsByNameAndArtist.isEmpty()) {
                String exceptionMessage =
                        "No record found with name "+ param.get(record) + " and artist " + param.get(artist);
                throw new ResourceNotFoundException(exceptionMessage);
            } else { return recordsByNameAndArtist; }

        } else if(param.containsKey(record)) {
            log.debug("Record param inserted.");
            List<Record> recordByName = getRecordByName(param.get(record));
            if (recordByName.isEmpty()) {
                String exceptionMessage = "No record found with name " + param.get(record);
                throw new ResourceNotFoundException(exceptionMessage);
            } else { return recordByName; }

        } else if (param.containsKey(artist)) {
            log.debug("Artist param inserted.");
            List<Record> recordsByArtist = getRecordsByArtist(param.get(artist));
            if (recordsByArtist.isEmpty()) {
                String exceptionMessage = "No record found having artist " + param.get(artist);
                throw new ResourceNotFoundException(exceptionMessage);
            } else { return recordsByArtist; }

        } else if(param.isEmpty()){
            log.debug("No param inserted.");
            List<Record> allRecords = getAllRecords();
            if (allRecords.isEmpty()) {
                String exceptionMessage = "No records found in the database";
                throw new ResourceNotFoundException(exceptionMessage);
            } else { return allRecords; }
        } else {
            log.debug("Incorrect key as param inserted.");
            throw new InvalidParameterException("Invalid Parameters used in the request");
        }
    }
}
