package com.openclassrooms.SafetyNetAlerts.controllers;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.openclassrooms.SafetyNetAlerts.DAO.FilterDAO;
import com.openclassrooms.SafetyNetAlerts.DAO.PersonsDAO;
import com.openclassrooms.SafetyNetAlerts.bean.Persons;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class PersonsController {

    private static Logger logger = LoggerFactory.getLogger(PersonsController.class);
    private static ObjectMapper mapper = new ObjectMapper();
    private static FilterProvider personFilter = new SimpleFilterProvider().addFilter("personFilter", SimpleBeanPropertyFilter.serializeAll());

    @Autowired
    PersonsDAO personDAO;

    @Autowired
    FilterDAO filterService;

    @GetMapping(value = "/person", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getPerson() {
        logger.info("GET http://localhost:8080/person");
        String persons = null;
        try {
            persons = mapper.writer(personFilter).withDefaultPrettyPrinter().writeValueAsString(personDAO.getPersons());
            logger.info(persons);
        }   catch (Exception e) {
            logger.error("Request failed. Exception error is: " + e);
        }
        return persons;
    }

    /**
     * example : http://localhost:8080/communityEmail?city=Culver
     */
    @GetMapping(value = "/communityEmail")
    public Map<String, List<String>> personsEmailAtCity(String city) {
        logger.info("GET http://localhost:8080/communityEmail?city=" + city);
        Map<String, List<String>> personEmails = null;
        try {
            personEmails = filterService.getPersonsEmailInCity(city);
            logger.info(String.valueOf(personEmails));
        } catch (Exception e) {
            logger.error("Request failed. Exception error is: " + e);
        }
        return personEmails;
    }

    /**
     * example : http://localhost:8080/personInfo?firstName=Jacob&lastName=Boyd
     */
    @GetMapping(value = "/personInfo")
    public List<JsonNode> getPersonInfo(String firstName, String lastName) {
        logger.info("GET http://localhost:8080/personInfo?firstName=" + firstName + "&lastName=" + lastName);
        List<JsonNode> persons = null;
        try {
            persons = filterService.getPersonFiltered(firstName, lastName);
            logger.info(String.valueOf(persons));
        } catch (Exception e) {
            logger.error("Request failed. Exception error is: " + e);
        }
        return persons;
    }

    /**
    * example : http://localhost:8080/childAlert?address=1509 Culver St
    */
    @GetMapping(value = "/childAlert")
    public Map<String, List> getChildsAtAddress(String address) {
        logger.info("GET http://localhost:8080/childAlert?address=" + address);
        Map<String, List> childs = null;
        try {
            childs = filterService.countChildsAtAddress(address);
            logger.info(String.valueOf(childs));
        } catch (Exception e) {
            logger.error("Request failed. Exception error is: " + e);
        }
        return childs;
    }

    /**
     * example : http://localhost:8080/fire?address=1509 Culver St
     */
    @GetMapping(value = "/fire")
    public Map<String, Object> getPersonsAndMedicalRecordsAndStationNumberOfAddress(String address) {
        logger.info("GET http://localhost:8080/fire?address=" + address);
        Map<String, Object> personsAndRecords = null;
        try {
            personsAndRecords = filterService.getPersonsMedicalRecordsAndStationNumberOfAddress(address);
            logger.info(String.valueOf(personsAndRecords));
        } catch (Exception e) {
            logger.error("Request failed. Exception error is: " + e);
        }
        return personsAndRecords;
    }

    @DeleteMapping(value = "/person", produces = MediaType.APPLICATION_JSON_VALUE)
    public String removePerson(@RequestBody Persons person) {
        logger.info("DELETE http://localhost:8080/person");
        logger.info("body: " + person);
        String personToDelete = null;
        try {
            personToDelete = mapper.writer(personFilter).withDefaultPrettyPrinter().writeValueAsString(personDAO.deletePerson(person));
            logger.info(personToDelete);
        }   catch (Exception e) {
            logger.error("Request failed. Exception error is: " + e);
        }
        return personToDelete;
    }


    @PostMapping(value = "/person", produces = MediaType.APPLICATION_JSON_VALUE)
    public String addPerson(@RequestBody Persons person) {
        logger.info("POST http://localhost:8080/person");
        logger.info("body: " + person);
        String personToAdd = null;
        try {
            personToAdd = mapper.writer(personFilter).withDefaultPrettyPrinter().writeValueAsString(personDAO.addPerson(person));
            logger.info(personToAdd);
        }   catch (Exception e) {
            logger.error("Request failed. Exception error is: " + e);
        }
        return personToAdd;
    }

    @PutMapping(value = "/person", produces = MediaType.APPLICATION_JSON_VALUE)
    public String updatePerson(@RequestBody Persons person) {
        logger.info("PUT http://localhost:8080/person");
        logger.info("body: " + person);
        String personToModify = null;
        try {
            List<JsonNode> personFiltered = filterService.getPersonFiltered(person.getFirstName(), person.getLastName());
            if (personFiltered != null){
                personToModify = mapper.writer(personFilter).withDefaultPrettyPrinter().writeValueAsString(personDAO.addPerson(person));
                logger.info(personToModify);
            }
            //TODO revoir la gestion des exceptions
        }   catch (Exception e) {
            logger.error("Request failed. Exception error is: " + e);
        }
        return personToModify;
    }




}
