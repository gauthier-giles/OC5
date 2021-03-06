package com.openclassrooms.SafetyNetAlerts.DAO;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.openclassrooms.SafetyNetAlerts.DTO.JsonDTO;
import com.openclassrooms.SafetyNetAlerts.bean.FireStation;
import com.openclassrooms.SafetyNetAlerts.bean.MedicalRecord;
import com.openclassrooms.SafetyNetAlerts.bean.Persons;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FilterDAO {

    private static Logger logger = LoggerFactory.getLogger(PersonsDAO.class);


    private static Map<String, Persons> persons;
    private static Map<String, MedicalRecord> medicalRecords;
    private static Map<String, FireStation> fireStations;
    private static ObjectMapper mapper = new ObjectMapper();

    @Autowired
    public FilterDAO(JsonDTO jsonDTO) throws IOException {
        persons = jsonDTO.getPersons();
        medicalRecords = jsonDTO.getMedicalRecords();
        fireStations = jsonDTO.getFireStations();
    }

    public Map<String, List<String>> getPersonsEmailInCity(String city) throws JsonProcessingException {
        List<Persons> personsAtCity = this.persons.values().stream().filter(person -> person.getCity().equals(city)).collect(Collectors.toList());
        logger.info("Persons in city: " + personsAtCity.size());
        List<String> personsEmail = personsAtCity.stream().map(person -> person.getEmail()).collect(Collectors.toList());
        return Map.of(city, personsEmail);
    }

    public List<JsonNode> getPersonFiltered(String firstName, String lastName) throws JsonProcessingException, ParseException {
        FilterProvider personFilter = new SimpleFilterProvider().addFilter("personFilter", SimpleBeanPropertyFilter.filterOutAllExcept("address", "email"));
        FilterProvider medicalFilter = new SimpleFilterProvider().addFilter("medicalFilter", SimpleBeanPropertyFilter.filterOutAllExcept("medications", "allergies"));

        List<Persons> personsByName;
        if (firstName == null && lastName != null) {
            personsByName = this.persons.values().stream().filter(person -> (lastName).equals(person.getLastName())).collect(Collectors.toList());
        } else if (firstName != null && lastName == null) {
            personsByName = this.persons.values().stream().filter(person -> (firstName).equals(person.getFirstName())).collect(Collectors.toList());
        } else {
            personsByName = this.persons.values().stream().filter(person -> (firstName + lastName).equals(person.getFirstName() + person.getLastName())).collect(Collectors.toList());
        }
        List<JsonNode> personsJson = this.filterObjectsInJson(personsByName, personFilter);

        for (int i = 0; i < personsByName.size(); i++) {
            String fullname = personsByName.get(i).getFirstName() + " " + personsByName.get(i).getLastName();
            ((ObjectNode) personsJson.get(i)).put(fullname, this.filterObjectInJson(this.medicalRecords.get(fullname), medicalFilter));
            ((ObjectNode) personsJson.get(i)).put("??ge: ", this.calculateAge(this.medicalRecords.get(fullname).getBirthdate()));
        }
        return personsJson;
    }



    public Map<String, Object> countAdultAndChildPerStation(int stationNumber) throws ParseException, JsonProcessingException {
        HashMap<String, Object> adultAndChildPerStation = new HashMap<>();
        // Get address covered by a station
        List<String> addresses = getAddressesAtStation(stationNumber);
        logger.info("Adress : " + addresses);
        //Rajouter une exception et logger si addresses est vide?
        List<Persons> personsAtAdresses = this.persons.values()
                .stream()
                .filter(person -> addresses.contains(person.getAddress()))
                .collect(Collectors.toList());
        logger.info("Number of people at adress: " + personsAtAdresses.size());

        List<Persons> personsOverEighteen = new ArrayList<>();
        List<Persons> personsUnderEighteen = new ArrayList<>();
        List<JsonNode> personAtAdressesFiltered = new ArrayList<>();
        FilterProvider personFilter = new SimpleFilterProvider().addFilter("personFilter", SimpleBeanPropertyFilter.filterOutAllExcept("firstName", "lastName", "address", "phone"));
        for (Persons persons : personsAtAdresses) {
            String birthDay = this.medicalRecords.get(persons.getFirstName() + " " + persons.getLastName()).getBirthdate();
            int age = calculateAge(birthDay);
            if (age > 18) {
                personsOverEighteen.add(persons);
            } else {
                personsUnderEighteen.add(persons);
            }
            String personAtAdressesJson = mapper.writer(personFilter)
                    .withDefaultPrettyPrinter()
                    .writeValueAsString(persons);
            JsonNode personJsonObject = mapper.readTree(personAtAdressesJson);
            personAtAdressesFiltered.add(personJsonObject);

        }
        logger.info("Adults: " + personsOverEighteen.size() + " Childs: " + personsUnderEighteen.size());

        adultAndChildPerStation.put("personsAtFirestationAddress", personAtAdressesFiltered);
        adultAndChildPerStation.put("personsOverEighteen", personsOverEighteen.size());
        adultAndChildPerStation.put("personsUnderEighteen", personsUnderEighteen.size());

        return adultAndChildPerStation;
    }
    public Map<String, List> countChildsAtAddress(String address) throws ParseException, JsonProcessingException {
        Map<String, List> childsAtAddress = new HashMap<>();
        List<JsonNode> childs = new ArrayList<>();
        List<JsonNode> adults = new ArrayList<>();
        logger.info(String.valueOf(this.persons));
        List<Persons> personsAtAddress = this.persons.values()
                .stream()
                .filter(person -> person.getAddress().equals(address))
                .collect(Collectors.toList());
        logger.info("adults at address: " + personsAtAddress);

        List<JsonNode> childsFiltered = new ArrayList<>();
        FilterProvider personFilter = new SimpleFilterProvider().addFilter("personFilter", SimpleBeanPropertyFilter.filterOutAllExcept("firstName", "lastName"));
        for (Persons persons : personsAtAddress) {
            String birthDate = this.medicalRecords.get(persons.getFirstName() + " " + persons.getLastName()).getBirthdate();

            String personAtAdressesJson = mapper.writer(personFilter)
                    .withDefaultPrettyPrinter()
                    .writeValueAsString(persons);
            JsonNode personJsonObject = mapper.readTree(personAtAdressesJson);
            int age = calculateAge(birthDate);
            ((ObjectNode) personJsonObject).put("age", age);
            childsFiltered.add(personJsonObject);
            if (age > 18) {
                adults.add(personJsonObject);
            } else {
                childs.add(personJsonObject);
            }


        }
        childsAtAddress.put("childsAtAddress", childs);
        childsAtAddress.put("otherMembers", adults);

        return childsAtAddress;
    }

    public Map<String, List<String>> getPhoneNumbersForStation(int stationNumber) throws JsonProcessingException {
        List<String> addresses = this.getAddressesAtStation(stationNumber);
        List<List<Persons>> personsAtAdresses = addresses.stream().map(address -> this.getPersonsAtAddress(address)).collect(Collectors.toList());


        List<String> phoneNumbers = new ArrayList<>();
        for (List<Persons> personsAtAddress : personsAtAdresses) {
            personsAtAddress.stream().forEach(person -> phoneNumbers.add(person.getPhone()));
        }
        logger.info("people number by address: " + personsAtAdresses.size());
        return Map.of("Firestation n??" + stationNumber, phoneNumbers);
    }

    public Map<String, Object> getPersonsMedicalRecordsAndStationNumberOfAddress(String address) throws JsonProcessingException {
        //Modifier en utilisant des maps pour avoir une meilleure visibilit??e
        Map<String, Object> personsAndMedicalRecordsAndFireStation = new HashMap<>();
        Map<String, MedicalRecord> medicalRecords = this.medicalRecords;
        List<Persons> personsLivingInAddress = getPersonsAtAddress(address);
        logger.info("People living at address requested: " + personsLivingInAddress.toString());

        FilterProvider personFilter = new SimpleFilterProvider().addFilter("personFilter", SimpleBeanPropertyFilter.filterOutAllExcept("phone"));
        FilterProvider medicalFilter = new SimpleFilterProvider().addFilter("medicalFilter", SimpleBeanPropertyFilter.filterOutAllExcept("medications", "allergies"));

        List<JsonNode> personAndMedicalRecord = new ArrayList<>();
        for (Persons persons : personsLivingInAddress) {
            String fullName = persons.getFirstName() + " " + persons.getLastName();
            String personJson = mapper.writer(personFilter)
                    .withDefaultPrettyPrinter()
                    .writeValueAsString(persons);
            String medicalJson = mapper.writer(medicalFilter)
                    .withDefaultPrettyPrinter()
                    .writeValueAsString(medicalRecords.get(fullName));

            JsonNode personJsonNode = mapper.readTree(personJson);
            JsonNode medicalJsonNode = mapper.readTree(medicalJson);
            ((ObjectNode) personJsonNode).put(fullName, medicalJsonNode);

            personAndMedicalRecord.add(personJsonNode);
        }
        personsAndMedicalRecordsAndFireStation.put("people living in this address : ", personAndMedicalRecord);
        personsAndMedicalRecordsAndFireStation.put("FireStation number: ", this.fireStations.values()
                .stream()
                .filter(firestation -> firestation.getAddress().equals(address))
                .collect(Collectors.toList()).get(0).getStation());

        return personsAndMedicalRecordsAndFireStation;
    }


    public Map<String, List<JsonNode>> getPersonsAndMedicalRecordPerAddressPerStation(String[] stations) throws JsonProcessingException, ParseException {
        logger.info("FireStations : " + Arrays.asList(stations));
        Map<String, List<JsonNode>> PersonsAndMedicalRecordPerAddressPerStation = new HashMap<>();
        List<String> firestationsAddresses = this.fireStations.values().stream()
                .filter(firestation -> Arrays.asList(stations).contains(firestation.getStation()))
                .map(firestation -> firestation.getAddress())
                .collect(Collectors.toList());
        logger.info("FireStations: " + firestationsAddresses);

        FilterProvider personFilter = new SimpleFilterProvider().addFilter("personFilter", SimpleBeanPropertyFilter.filterOutAllExcept("phone", "firstName", "lastName"));
        FilterProvider medicalFilter = new SimpleFilterProvider().addFilter("medicalFilter", SimpleBeanPropertyFilter.filterOutAllExcept("medications", "allergies"));
        for (String address : firestationsAddresses) {
            List<Persons> personsAtAddress = this.getPersonsAtAddress(address);
            List<JsonNode> personsJson = this.filterObjectsInJson(personsAtAddress, personFilter);

            for (JsonNode person : personsJson) {
                String fullname = person.get("firstName").asText() + " " + person.get("lastName").asText();
                ((ObjectNode) person).put(fullname, this.filterObjectInJson(this.medicalRecords.get(fullname), medicalFilter));
                ((ObjectNode) person).put("??ge: ", this.calculateAge(this.medicalRecords.get(fullname).getBirthdate()));
            }
            PersonsAndMedicalRecordPerAddressPerStation.put(address, personsJson);
        }

        return PersonsAndMedicalRecordPerAddressPerStation;
    }


    static List<JsonNode> filterObjectsInJson(List objectsToFilter, FilterProvider filter) throws JsonProcessingException {
        List<JsonNode> objectsJson = new ArrayList<>();
        for (Object object : objectsToFilter) {
            objectsJson.add(filterObjectInJson(object, filter));
        }
        return objectsJson;
    }

    public static JsonNode filterObjectInJson(Object objectToFilter, FilterProvider filter) throws JsonProcessingException {
        String objectJson = mapper.writer(filter)
                .withDefaultPrettyPrinter()
                .writeValueAsString(objectToFilter);
        return mapper.readTree(objectJson);
    }

    public static List<Persons> getPersonsAtAddress(String address) {
        List<Persons> personAtAddress = new ArrayList<>();
        try {
            personAtAddress = persons.values()
                    .stream()
                    .filter(person -> address.equals(person.getAddress()))
                    .collect(Collectors.toList());

        } catch (NullPointerException e) {
            logger.error("Nothing is found for this address: " + address);
        } catch (Exception e) {
            logger.error("Unkknown error");
        }
        return personAtAddress;
    }

    public static List<String> getAddressesAtStation(int stationNumber) {
        return fireStations.values()
                .stream()
                .filter(station -> Integer.parseInt(station.getStation()) == stationNumber)
                .map(FireStation::getAddress)
                .collect(Collectors.toList());
    }


    public static int calculateAge(String birthDay) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy", Locale.FRANCE);
        LocalDate birthDate = df.parse(birthDay).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        String todayString = df.format(new Date());
        LocalDate today = df.parse(todayString).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int age = Period.between(birthDate, today).getYears();
        return age;
    }


}
