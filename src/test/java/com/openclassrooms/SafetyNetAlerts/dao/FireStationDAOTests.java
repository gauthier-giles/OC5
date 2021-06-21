package com.openclassrooms.SafetyNetAlerts.dao;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.SafetyNetAlerts.DAO.FireStationDAO;
import com.openclassrooms.SafetyNetAlerts.DTO.JsonDTO;
import com.openclassrooms.SafetyNetAlerts.bean.FireStation;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;

//import org.junit.Test;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)

public class FireStationDAOTests {
    private static Logger logger = LoggerFactory.getLogger(JsonDTO.class);

    @InjectMocks
    FireStationDAO firestationDAO;

    @Mock
    JsonDTO jsonFileDTO;

    @NonNull
    private static JsonNode jsonNode;
    @NonNull
    private static ObjectMapper mapper;
    @NonNull
    private static FireStation republiqueStation;
    @NonNull
    private static FireStation cluverStation;


    @BeforeEach
    public void setUp() throws IOException, org.json.simple.parser.ParseException {
        String DUMMY_JSON =
                "{\"firestations\": " +
                        "[" +
                        "{ \"address\":\"13 avenue de la République\", \"station\":\"1\" }," +
                        "{ \"address\":\"1509 Culver St\", \"station\":\"3\" }" +
                        "]} ";

        mapper = new ObjectMapper();
        jsonNode = mapper.readTree(DUMMY_JSON);

        // Create beans using dummy json
        JsonNode jsonFirestations = jsonNode.get("firestations");
        Map<String, FireStation> firestations = new HashMap<>();
        republiqueStation = mapper.readValue(String.valueOf(jsonFirestations.get(0)), FireStation.class);
        cluverStation = mapper.readValue(String.valueOf(jsonFirestations.get(1)), FireStation.class);
        firestations.put("13 avenue de la République", republiqueStation);
        firestations.put("1509 Culver St", cluverStation);

        // Create classe with mocked datas
        when(jsonFileDTO.getFireStations()).thenReturn(firestations);
        firestationDAO = new FireStationDAO(jsonFileDTO);

    }

    @Test
    public void getFirestationTest() throws JsonProcessingException {
        // WHEN
        List<FireStation> firestations = firestationDAO.getFireStations();

        // THEN
        Assertions.assertThat(firestations).containsExactlyInAnyOrder(republiqueStation, cluverStation);
        System.out.println("TEST PASSES =============XXXXXXXX==============");
    }

    @Test
    public void addFirestationTest() throws JsonProcessingException {
        // GIVEN
        String steppesJson = "{ \"address\":\"112 Steppes Pl\", \"station\":\"3\" }";
        FireStation steppesStation = mapper.readValue(steppesJson, FireStation.class);

        // WHEN
        firestationDAO.addFireStation(steppesStation);

        // THEN
        Assertions.assertThat(firestationDAO.firestations.values()).containsExactlyInAnyOrder(republiqueStation, cluverStation, steppesStation);
    }

    @Test
    public void removeFirestationTest() throws JsonProcessingException {
        // GIVEN
        String steppesJson = "{ \"address\":\"112 Steppes Pl\", \"station\":\"3\" }";
        FireStation steppes = mapper.readValue(steppesJson, FireStation.class);

        // WHEN
        firestationDAO.deleteFireStation(steppes);

        // THEN
        Assertions.assertThat(firestationDAO.firestations.values()).containsExactlyInAnyOrder(republiqueStation, cluverStation);
    }

    @Test
    public void updateFirestationTest() throws JsonProcessingException {
        // GIVEN
        String cluverJson = "{ \"address\":\"1509 Culver St\", \"station\":\"2\" }";
        FireStation cluver = mapper.readValue(cluverJson, FireStation.class);

        // WHEN
        List<FireStation> stations = firestationDAO.updateFireStation(cluver);

        // THEN
        Assertions.assertThat(stations.get(0).getAddress()).isEqualTo("1509 Culver St");
        Assertions.assertThat(stations.get(0).getStation()).isEqualTo("2");
    }
    // TODO refaire une classe de test



}
