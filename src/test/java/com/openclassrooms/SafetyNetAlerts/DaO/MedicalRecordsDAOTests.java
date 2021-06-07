package com.openclassrooms.SafetyNetAlerts.DaO;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.SafetyNetAlerts.DAO.MedicalRecordDAO;
import com.openclassrooms.SafetyNetAlerts.DTO.JsonDTO;
import com.openclassrooms.SafetyNetAlerts.bean.MedicalRecord;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
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



@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
public class MedicalRecordsDAOTests {
    private static Logger logger = LoggerFactory.getLogger(MedicalRecordsDAOTests.class);

    @InjectMocks
    MedicalRecordDAO medicalRecordDAO;

    @Mock
    JsonDTO jsonFileDTO;

    @NonNull
    private static JsonNode jsonNode;
    @NonNull
    private static ObjectMapper mapper;
    @NonNull
    private static MedicalRecord john;
    @NonNull
    private static MedicalRecord jacob;
    @NonNull
    private static MedicalRecord tenley;

    @Before
    public void setUp() throws IOException, org.json.simple.parser.ParseException {
        String DUMMY_JSON = "{\"medicalrecords\": " +
                "[" +
                "{ \"firstName\":\"John\", \"lastName\":\"Boyd\", \"birthdate\":\"03/06/1984\", \"medications\":[\"aznol:350mg\", \"hydrapermazol:100mg\"], \"allergies\":[\"nillacilan\"] }," +
                "{ \"firstName\":\"Jacob\", \"lastName\":\"Boyd\", \"birthdate\":\"03/06/1989\", \"medications\":[\"pharmacol:5000mg\", \"terazine:10mg\", \"noznazol:250mg\"], \"allergies\":[] },\n" +
                "{ \"firstName\":\"Tenley\", \"lastName\":\"Boyd\", \"birthdate\":\"02/18/2012\", \"medications\":[], \"allergies\":[\"peanut\"] }" +
                "]" +
                "}";
        mapper = new ObjectMapper();
        jsonNode = mapper.readTree(DUMMY_JSON);

        // Create beans using dummy json
        JsonNode jsonRecord = jsonNode.get("medicalrecords");
        Map<String, MedicalRecord> medicalRecords = new HashMap<>();
        john = mapper.readValue(String.valueOf(jsonRecord.get(0)), MedicalRecord.class);
        jacob = mapper.readValue(String.valueOf(jsonRecord.get(1)), MedicalRecord.class);
        tenley = mapper.readValue(String.valueOf(jsonRecord.get(2)), MedicalRecord.class);
        medicalRecords.put("John Boyd", john);
        medicalRecords.put("Jacob Boyd", jacob);
        medicalRecords.put("Tenley Boyd", tenley);

        // Create classe with mocked datas
        when(jsonFileDTO.getMedicalRecords()).thenReturn(medicalRecords);
        medicalRecordDAO = new MedicalRecordDAO(jsonFileDTO);
    }

    @Test
    public void getMedicalRecordsTest() throws JsonProcessingException {
        // WHEN
        List<MedicalRecord> medicalRecords = medicalRecordDAO.getMedicalRecords();

        // THEN
        Assertions.assertThat(medicalRecords).containsExactlyInAnyOrder(john, jacob, tenley);
    }

    @Test
    public void addPersonsTest() throws JsonProcessingException {
        // GIVEN
        String frankRecordJson = "{ \"firstName\":\"Frank\", \"lastName\":\"Heart\", \"birthdate\":\"02/06/2004\", \"medications\":[], \"allergies\":[\"water\"] }";
        MedicalRecord frankRecord = mapper.readValue(frankRecordJson, MedicalRecord.class);

        // WHEN
        medicalRecordDAO.addMedicalRecord(frankRecord);

        // THEN
        Assertions.assertThat(medicalRecordDAO.medicalRecords.values()).containsExactlyInAnyOrder(john, jacob, tenley, frankRecord);
    }

    @Test
    public void removePersonsTest() throws JsonProcessingException {
        // GIVEN
        String frankRecordJson = "{ \"firstName\":\"Frank\", \"lastName\":\"Heart\", \"birthdate\":\"02/06/2004\", \"medications\":[], \"allergies\":[\"water\"] }";
        MedicalRecord frankRecord = mapper.readValue(frankRecordJson, MedicalRecord.class);

        // WHEN
        medicalRecordDAO.deleteMedicalRecord(frankRecord);

        // THEN
        Assertions.assertThat(medicalRecordDAO.medicalRecords.values()).containsExactlyInAnyOrder(john, jacob, tenley);
    }

    @Test
    public void updatePersonsTest() throws JsonProcessingException {
        // GIVEN
        String johnRecordJson = "{ \"firstName\":\"John\", \"lastName\":\"Boyd\", \"birthdate\":\"03/06/1984\", \"medications\":[\"tramadol:200mg\", \"hydrapermazol:100mg\"], \"allergies\":[\"nillacilan\"] },";
        MedicalRecord johnRecord = mapper.readValue(johnRecordJson, MedicalRecord.class);
        // WHEN
        List<MedicalRecord> medicalRecords = medicalRecordDAO.updateMedicalRecord(johnRecord);

        // THEN
        Assertions.assertThat(medicalRecords.get(0).getMedications()).contains("tramadol:200mg");
    }
}
