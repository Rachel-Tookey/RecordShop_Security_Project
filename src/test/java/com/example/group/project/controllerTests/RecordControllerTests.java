package com.example.group.project.controllerTests;

import com.example.group.project.controller.RecordController;
import com.example.group.project.model.entity.Record;
import com.example.group.project.service.impl.RecordServiceImpl;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import static org.hamcrest.Matchers.equalTo;
import java.util.List;

@SpringBootTest
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

    // todo autowire the baseURI for docker
    @BeforeAll
    static void setup(){
        String baseURI = "http://localhost:8080";
    }

    // Test successful requests
    @Test
    public void getRecord_givenExistingArtistAndName_returnsCorrectRecords () {
        String recordName = "Thriller";
        String artist = "Michael Jackson";

        Record record1 = new Record();
        record1.setName(recordName);
        record1.setArtist(artist);

        List<Record> recordByNameAndArtist = List.of(record1);

        Mockito
                .when(recordServiceImpl.getRecordsByNameAndArtist(recordName, artist))
                .thenReturn(recordByNameAndArtist);


        RestAssuredMockMvc
                .given()
                    .param("name", "Thriller")
                    .param("artist","Michael Jackson")
                .when()
                    .get("/getRecord")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("$.size()", equalTo(recordByNameAndArtist.size()))
                .body("[0].artist", equalTo(record1.getArtist()))
                .body("[0].name", equalTo(record1.getName()));
    }

    @Test
    public void getRecord_givenExistingArtist_returnsArtistRecords () {
        Record record1 = new Record();
        Record record2 = new Record();

        record1.setName("Rec1");
        record2.setName("Rec2");

        String artist = "Michael Jackson";

        record1.setArtist(artist);
        record2.setArtist(artist);

        List<Record> recordsByArtist = List.of(record1, record2);

        Mockito
                .when(recordServiceImpl.getRecordsByArtist(artist))
                .thenReturn(recordsByArtist);


        RestAssuredMockMvc
                .given()
                    .param("artist", "Michael Jackson")
                .when()
                    .get("/getRecord")
                .then()
                    .statusCode(200)
                    .body("$.size()", equalTo(recordsByArtist.size()))
                    .body("[0].artist", equalTo(record1.getArtist()))
                    .body("[1].artist", equalTo(record2.getArtist()));
    }

    @Test
    public void getRecord_givenExistingRecord_returnsThatRecord () {
        String recordName = "Rec1";
        Record record1 = new Record();
        record1.setName(recordName);

        List<Record> recordByName = List.of(record1);

        Mockito
                .when(recordServiceImpl.getRecordByName(recordName))
                .thenReturn(recordByName);


        RestAssuredMockMvc
                .given()
                .param("name", "Rec1")
                .when()
                .get("/getRecord")
                .then()
                .statusCode(200)
                .body("$.size()", equalTo(recordByName.size()))
                .body("[0].name", equalTo(record1.getName()));
    }

    @Test
    public void getRecord_givenNoParam_returnsAllRecords () {
        Record record1 = new Record();
        Record record2 = new Record();
        Record record3 = new Record();

        record1.setName("Rec1");
        record2.setName("Rec2");
        record3.setName("Rec3");

        List<Record> allRecords = List.of(record1, record2, record3);

        Mockito
                .when(recordServiceImpl.getAllRecords())
                .thenReturn(allRecords);


        RestAssuredMockMvc
                .when()
                .get("/getRecord")
                .then()
                .statusCode(200)
                .body("$.size()", equalTo(allRecords.size()))
                .body("[0].name", equalTo(record1.getName()))
                .body("[1].name", equalTo(record2.getName()))
                .body("[2].name", equalTo(record3.getName()));
    }


    // Test unsuccessful requests
    @Test
    public void getRecord_givenNotExistingDetails_returnsNotFoundStatus () {
        String artist = "Bob Dylan";
        String record = "Blowing in the wind";

        List<Record> noRecordsFound = List.of();

        Mockito
                .when(recordServiceImpl
                        .getRecordsByNameAndArtist(record, artist))
                .thenReturn(noRecordsFound);

        String expectedMessage = "No record found with name " + record + " and artist " + artist;

        RestAssuredMockMvc
                .given()
                    .param("artist",artist)
                    .param("name", record)
                .when()
                    .get("/getRecord")
                .then()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .body(equalTo(expectedMessage));
    }

    @Test
    public void getRecord_givenNotExistingArtist_returnsMessageWithArtist () {
        String artist = "Bob Dylan";

        List<Record> noRecordsFound = List.of();

        Mockito
                .when(recordServiceImpl.getRecordsByArtist(artist))
                .thenReturn(noRecordsFound);

        String expectedMessage = "No record found having artist " + artist;

        RestAssuredMockMvc
                .given()
                .param("artist", artist)
                .when()
                .get("/getRecord")
                .then()
                .statusCode(200)
                .body(equalTo(expectedMessage));
    }

    @Test
    public void getRecord_givenNotExistingRecord_returnsMessageWithRecord () {
        String recordName = "Blowing in the wind";
        List<Record> noRecordsFound = List.of();

        Mockito
                .when(recordServiceImpl.getRecordByName(recordName))
                .thenReturn(noRecordsFound);

        String expectedMessage = "No record found with name " + recordName;

        RestAssuredMockMvc
                .given()
                .param("name", recordName)
                .when()
                .get("/getRecord")
                .then()
                .statusCode(200)
                .body(equalTo(expectedMessage));
    }

    @Test
    public void getRecord_givenWrongParameterKeys_returnsAllRecords () {
        Record record1 = new Record();
        Record record2 = new Record();
        Record record3 = new Record();

        record1.setName("Rec1");
        record2.setName("Rec2");
        record3.setName("Rec3");

        List<Record> allRecords = List.of(record1, record2, record3);

        Mockito
                .when(recordServiceImpl.getAllRecords())
                .thenReturn(allRecords);


        RestAssuredMockMvc
                .given()
                .param("not a param", "not a value")
                .when()
                .get("/getRecord")
                .then()
                .statusCode(200)
                .body("$.size()", equalTo(allRecords.size()))
                .body("[0].name", equalTo(record1.getName()))
                .body("[1].name", equalTo(record2.getName()))
                .body("[2].name", equalTo(record3.getName()));
    }

}
