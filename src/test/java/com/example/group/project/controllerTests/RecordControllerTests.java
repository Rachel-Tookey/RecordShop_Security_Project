package com.example.group.project.controllerTests;

import com.example.group.project.controller.RecordController;
import com.example.group.project.model.Record;
import com.example.group.project.model.RecordRepository;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.hamcrest.Matchers.equalTo;
import java.util.List;


@ExtendWith(MockitoExtension.class)
public class RecordControllerTests {
    @Mock
    private RecordRepository recordRepository;

    @InjectMocks
    private RecordController recordController;

    @BeforeEach
    public void setUpMockRecordController() {
        RestAssuredMockMvc.standaloneSetup(recordController);
    }

    @BeforeAll
    static void setup(){
        String baseURI = "http://localhost:8080";
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
                .when(recordRepository.findByArtistIgnoreCase(artist))
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

}
