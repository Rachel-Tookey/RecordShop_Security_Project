package com.example.group.project.controllerTests;

import com.example.group.project.controller.RecordController;
import com.example.group.project.exceptions.InvalidParameterException;
import com.example.group.project.exceptions.ResourceNotFoundException;
import com.example.group.project.model.entity.Record;
import com.example.group.project.service.impl.RecordServiceImpl;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class RecordControllerTests {
    @Mock
    private RecordServiceImpl recordServiceImpl;

    @InjectMocks
    private RecordController recordController;

    @BeforeEach
    public void setUpMockRecordController() {
        RestAssuredMockMvc.standaloneSetup(recordController);
    }

    // Test successful requests
    @Test
    public void getRecords_givenExistingArtistAndName_returnsCorrectRecords() {
        String recordName = "Thriller";
        String artist = "Michael Jackson";

        Record record1 = new Record();
        record1.setName(recordName);
        record1.setArtist(artist);

        List<Record> recordByNameAndArtist = List.of(record1);

        Mockito
                .when(recordServiceImpl.requestHandler(any(Map.class)))
                .thenReturn(recordByNameAndArtist);


        RestAssuredMockMvc
                .given()
                    .param("name", "Thriller")
                    .param("artist","Michael Jackson")
                .when()
                    .get("/records")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("$.size()", equalTo(recordByNameAndArtist.size()))
                .body("[0].artist", equalTo(record1.getArtist()))
                .body("[0].name", equalTo(record1.getName()));
    }

    @Test
    public void getRecords_givenExistingArtist_returnsArtistRecords() {
        Record record1 = new Record();
        Record record2 = new Record();

        record1.setName("Rec1");
        record2.setName("Rec2");

        String artist = "Michael Jackson";

        record1.setArtist(artist);
        record2.setArtist(artist);

        List<Record> recordsByArtist = List.of(record1, record2);

        Mockito
                .when(recordServiceImpl.requestHandler(any(Map.class)))
                .thenReturn(recordsByArtist);


        RestAssuredMockMvc
                .given()
                    .param("artist", "Michael Jackson")
                .when()
                    .get("/records")
                .then()
                    .statusCode(200)
                    .body("$.size()", equalTo(recordsByArtist.size()))
                    .body("[0].artist", equalTo(record1.getArtist()))
                    .body("[1].artist", equalTo(record2.getArtist()));
    }

    @Test
    public void getRecord_givenExistingRecord_returnsThatRecords() {
        String recordName = "Rec1";
        Record record1 = new Record();
        record1.setName(recordName);

        List<Record> recordByName = List.of(record1);

        Mockito
                .when(recordServiceImpl.requestHandler(any(Map.class)))
                .thenReturn(recordByName);


        RestAssuredMockMvc
                .given()
                .param("name", "Rec1")
                .when()
                .get("/records")
                .then()
                .statusCode(200)
                .body("$.size()", equalTo(recordByName.size()))
                .body("[0].name", equalTo(record1.getName()));
    }

    @Test
    public void getRecords_givenNoParam_returnsAllRecords() {
        Record record1 = new Record();
        Record record2 = new Record();
        Record record3 = new Record();

        record1.setName("Rec1");
        record2.setName("Rec2");
        record3.setName("Rec3");

        List<Record> allRecords = List.of(record1, record2, record3);

        Mockito
                .when(recordServiceImpl.requestHandler(any(Map.class)))
                .thenReturn(allRecords);


        RestAssuredMockMvc
                .when()
                .get("/records")
                .then()
                .statusCode(200)
                .body("$.size()", equalTo(allRecords.size()))
                .body("[0].name", equalTo(record1.getName()))
                .body("[1].name", equalTo(record2.getName()))
                .body("[2].name", equalTo(record3.getName()));
    }


    // Test unsuccessful requests
    @Test
    public void getRecords_givenNotExistingDetails_returnsNotFoundStatus() {
        String artist = "Bob Dylan";
        String record = "Blowing in the wind";

        String exceptionMessage = "No record found with name " + record + " and artist " + artist;

        Mockito
                .when(recordServiceImpl.requestHandler(any(Map.class)))
                .thenThrow(new ResourceNotFoundException(exceptionMessage));

        RestAssuredMockMvc
                .given()
                    .param("artist",artist)
                    .param("name", record)
                .when()
                    .get("/records")
                .then()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .body(equalTo(exceptionMessage));
    }


    @Test
    public void getRecords_givenWrongParameterKeys_returnsBadRequest() {
        String exceptionMessage = "Invalid Parameters used in the request";

        Mockito
                .when(recordServiceImpl.requestHandler(any(Map.class)))
                .thenThrow(new InvalidParameterException(exceptionMessage));

        RestAssuredMockMvc
                .given()
                    .param("not a param", "not a value")
                .when()
                    .get("/records")
                .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body(equalTo(exceptionMessage));
    }

}
