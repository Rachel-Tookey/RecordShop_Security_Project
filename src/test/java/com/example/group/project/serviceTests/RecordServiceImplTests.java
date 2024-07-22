package com.example.group.project.serviceTests;


import com.example.group.project.model.entity.Record;
import com.example.group.project.model.repository.RecordRepository;
import com.example.group.project.service.impl.RecordServiceImpl;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;


@ExtendWith(MockitoExtension.class)
public class RecordServiceImplTests {
    @Mock
    private RecordRepository recordRepository;

    @InjectMocks
    private RecordServiceImpl recordServiceImpl;

    @Test
    public void getAllRecords_withRecordsInDatabase_returnsAllRecords() {
        Record record1 = new Record();
        Record record2 = new Record();
        Record record3 = new Record();

        List<Record> allRecords = List.of(record1, record2, record3);

        Mockito
                .when(recordRepository.findAll())
                .thenReturn(allRecords);

        Assertions.assertEquals(allRecords.size(), recordServiceImpl.getAllRecords().size());
        Assertions.assertEquals(allRecords, recordServiceImpl.getAllRecords());

    }

    @Test
    public void getAllRecords_withNoRecordsInDatabase_returnsEmptyList() {
        List<Record> noRecords = List.of();

        Mockito
                .when(recordRepository.findAll())
                .thenReturn(noRecords);

        Assertions.assertTrue(recordServiceImpl.getAllRecords().isEmpty());

    }

    @Test
    public void getRecord_givenExistingArtist_returnsArtistRecords() {
        Record record1 = new Record();
        Record record2 = new Record();

        String artist = "Michael Jackson";

        List<Record> recordsByArtist = List.of(record1, record2);

        Mockito
                .when(recordRepository.findByArtistIgnoreCase(artist))
                .thenReturn(recordsByArtist);

        Assertions.assertEquals(recordsByArtist.size(), recordServiceImpl.getRecordsByArtist(artist).size());
        Assertions.assertEquals(recordsByArtist, recordServiceImpl.getRecordsByArtist(artist));
    }

    @Test
    public void getRecordByArtist_givenNotExistingArtist_returnsEmptyList() {
        String artist = "Bob Dylan";

        List<Record> noRecordsFound = List.of();

        Mockito
                .when(recordRepository.findByArtistIgnoreCase(artist))
                .thenReturn(noRecordsFound);

        Assertions.assertTrue(recordServiceImpl.getRecordsByArtist(artist).isEmpty());

    }

    @Test
    public void getRecordByName_givenExistingRecord_returnsThatRecord() {
        String recordName = "Rec1";
        Record record1 = new Record();
        record1.setName(recordName);

        List<Record> recordByName = List.of(record1);

        Mockito
                .when(recordRepository.findByNameIgnoreCase(recordName))
                .thenReturn(recordByName);

        Assertions.assertEquals(recordByName.size(), recordServiceImpl.getRecordByName(recordName).size());
        Assertions.assertEquals(recordByName, recordServiceImpl.getRecordByName(recordName));
    }

    @Test
    public void getRecordByName_givenNotExistingRecord_returnsEmptyList() {
        String recordName = "Rec1";

        List<Record> recordByName = List.of();

        Mockito
                .when(recordRepository.findByNameIgnoreCase(recordName))
                .thenReturn(recordByName);

        Assertions.assertTrue(recordServiceImpl.getRecordByName(recordName).isEmpty());
    }

    @Test
    public void getRecordsByNameAndArtist_givenExistingArtistAndName_returnsCorrectRecords () {
        String recordName = "Thriller";
        String artist = "Michael Jackson";

        Record record1 = new Record();
        record1.setName(recordName);
        record1.setArtist(artist);

        List<Record> recordByNameAndArtist = List.of(record1);

        Mockito
                .when(recordRepository.findByNameAndArtistIgnoreCase(recordName, artist))
                .thenReturn(recordByNameAndArtist);

        Assertions.assertEquals(recordByNameAndArtist.size(), recordServiceImpl.getRecordsByNameAndArtist(artist,
                recordName).size());
        Assertions.assertEquals(recordByNameAndArtist, recordServiceImpl.getRecordsByNameAndArtist(artist, recordName));
    }

    @Test
    public void getRecordsByNameAndArtist_givenNotExistingArtistAndName_returnsEmptyList () {
        String recordName = "Thriller";
        String artist = "Michael Jackson";

        List<Record> recordByNameAndArtist = List.of();

        Mockito
                .when(recordRepository.findByNameAndArtistIgnoreCase(recordName, artist))
                .thenReturn(recordByNameAndArtist);

        Assertions.assertTrue(recordServiceImpl.getRecordsByNameAndArtist(artist,
                recordName).isEmpty());

    }

}