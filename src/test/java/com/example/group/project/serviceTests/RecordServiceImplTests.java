package com.example.group.project.serviceTests;


import com.example.group.project.model.entity.Record;
import com.example.group.project.model.repository.RecordRepository;
import com.example.group.project.service.impl.RecordServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;


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


}