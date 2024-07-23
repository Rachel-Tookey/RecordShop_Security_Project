package com.example.group.project.service;

import com.example.group.project.exceptions.InvalidParameterException;
import com.example.group.project.exceptions.ResourceNotFoundException;
import com.example.group.project.model.entity.Record;

import java.util.List;
import java.util.Map;

public interface RecordService {
    List<Record> getAllRecords();
    List<Record> getRecordByName(String recordName);
    List<Record> getRecordsByArtist(String artist);
    List<Record> getRecordsByNameAndArtist(String recordName, String artist);
    List<Record> requestHandler(Map<String, String> param) throws ResourceNotFoundException,
            InvalidParameterException;

}
