package com.openclassrooms.SafetyNetAlerts.DTO;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.openclassrooms.SafetyNetAlerts.bean.FireStation;
import com.openclassrooms.SafetyNetAlerts.bean.MedicalRecord;
import com.openclassrooms.SafetyNetAlerts.bean.Persons;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JsonDTO {

    private static Logger logger = LoggerFactory.getLogger(JsonDTO.class);

    private static JsonDTO jsonDTOInstance;
    private static String pathToFile = "data.json";
    private JSONObject jsonObject;


//    @Bean
//    public static JsonDTO getInstance(){
//        if(JsonDTO.jsonDTOInstance== null) {
//            jsonDTOInstance = new JsonDTO();
//        }
//        return jsonDTOInstance;
//    }

    private JsonDTO(){
        try{
            FileReader reader = new FileReader(pathToFile);
            JSONParser jsonParser = new JSONParser();
            jsonObject  = (JSONObject) jsonParser.parse(reader);
            logger.info("data.json parsed with success");
        } catch (FileNotFoundException e) {
            logger.error("data.json not found", e);
        } catch (ParseException e) {
            logger.error("parse data.json is impossible", e);
        } catch (IOException e) {
            logger.error("fail to load data.json", e);
        }
    }

    public Map<String, Persons> getPersons() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(String.valueOf(jsonObject));
        JsonNode jsonArray = jsonNode.get("persons");
        HashMap<String, Persons> persons = new HashMap<>();
        for(JsonNode person: jsonArray) {
            String personJson = mapper.writeValueAsString(person);
            persons.put(person.get("firstName").asText() + " " + person.get("lastname"), mapper.readValue(personJson, Persons.class));
        }
        logger.info("there is" + persons.size() + "persons");
        if (persons.size() > 0) {
            return persons;
        }else{
            logger.error("No persons parsed");
            return null;
        }
    }

    public Map<String, FireStation> getFireStations() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(String.valueOf(jsonObject));
        JsonNode jsonArray = jsonNode.get("firestations");
        HashMap<String, FireStation> firestations = new HashMap<>();
        for(JsonNode firestation: jsonArray) {
            String firestationJson = mapper.writeValueAsString(firestation);
            firestations.put(firestation.get("address").asText(), mapper.readValue(firestationJson, FireStation.class));
        }
        logger.info("There is" + firestations.size() + " FireStations");
        if (firestations.size() > 0) {
            return firestations;
        }else{
            logger.error("No FireStation parsed");
            return null;
        }
    }

    public Map<String, MedicalRecord> getMedicalRecords() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(String.valueOf(jsonObject));
        ObjectReader reader = mapper.readerFor(new TypeReference<List<String>>() {
        });
        JsonNode jsonArray = jsonNode.get("medicalrecords");
        HashMap <String, MedicalRecord> medicalRecords = new HashMap<>();
        for(JsonNode medicalRecordJson: jsonArray) {
            MedicalRecord medicalRecord = new MedicalRecord();
            medicalRecord.setFirstName(medicalRecordJson.get("firstName").asText());
            medicalRecord.setLastName(medicalRecordJson.get("lastName").asText());
            medicalRecord.setBirthdate(medicalRecordJson.get("birthdate").asText());
            medicalRecord.setAllergies(reader.readValue(medicalRecordJson.get("allergies")));
            medicalRecord.setMedications(reader.readValue(medicalRecordJson.get("medications")));
            medicalRecords.put(medicalRecordJson.get("firstName").asText() + " " + medicalRecordJson.get("lastName").asText(),medicalRecord);
        }
        logger.info("there is" + medicalRecords.size() + "medical record");
        if (medicalRecords.size() > 0) {
            return medicalRecords;
        }else{
            logger.error("No medical record parsed");
            return null;
        }
    }



}
