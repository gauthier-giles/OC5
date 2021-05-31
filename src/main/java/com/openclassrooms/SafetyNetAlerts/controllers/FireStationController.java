package com.openclassrooms.SafetyNetAlerts.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.openclassrooms.SafetyNetAlerts.DAO.FilterDAO;
import com.openclassrooms.SafetyNetAlerts.DAO.FireStationDAO;
import com.openclassrooms.SafetyNetAlerts.bean.FireStation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class FireStationController {

    private static Logger logger = LoggerFactory.getLogger(FireStationController.class);

    @Autowired
    FireStationDAO firestationDAO;

    @Autowired
    FilterDAO filterService;


    /**
    * example : http://localhost:8080/firestation?stationNumber=1
    */
    @GetMapping(value = "/firestation")
    public Map<String, Object> countAdultAndChildPerStation(int stationNumber) {
        logger.info("http://localhost:8080/firestation?stationNumber=" + stationNumber);
        Map<String, Object> adultAndChildPerStation = null;
        try {
            adultAndChildPerStation = filterService.countAdultAndChildPerStation(stationNumber);
            logger.info(String.valueOf(adultAndChildPerStation));
        } catch(Exception e) {
            logger.error("Request failed. Exception error is: " + e);
        }
        return adultAndChildPerStation;
    }

    /**
    * example : http://localhost:8080/flood/stations?stations=1
    * example : ?stations=1&stations=2
    */
    @GetMapping(value = "/flood/stations")
    public Map<String, List<JsonNode>> personsAndMedicalRecordPerAddressPerStation(String[] stations) {
        String parameters = String.join("&", stations);
        logger.info("GET http://localhost:8080/flood/stations?" + parameters);
        Map<String, List<JsonNode>> personsAndMedicalRecord = null;
        try {
            personsAndMedicalRecord = filterService.getPersonsAndMedicalRecordPerAddressPerStation(stations);
            logger.info(String.valueOf(personsAndMedicalRecord));
        } catch(Exception e) {
            logger.error("Request failed. Exception error is: " + e);
        }
        logger.info("query result : "+personsAndMedicalRecord);
        return personsAndMedicalRecord;
    }

    /**
     * example : http://localhost:8080/phoneAlert?stationNumber=2
     */
    @GetMapping(value = "/phoneAlert")
    public Map<String, List<String>> getPersonsPhoneForStation(int stationNumber) {
        Map<String, List<String>> phoneNumbers = null;
        logger.info("http://localhost:8080/phoneAlert?firestation" + stationNumber);
        try {
            phoneNumbers = filterService.getPhoneNumbersForStation(stationNumber);
            logger.info(String.valueOf(phoneNumbers));
        } catch (Exception e) {
            logger.error("Request failed. Exception error is: " + e);
        }
        return phoneNumbers;
    }


    @DeleteMapping("/firestation")
    public List<FireStation> removeFireStation(@RequestBody FireStation stationToDelete) {
        List<FireStation> stationsAfterDeleting = null;
        logger.info("http://localhost:8080/firestation");
        logger.info("body: " + stationToDelete);
        try {
            stationsAfterDeleting = firestationDAO.deleteFireStation(stationToDelete);
            logger.info(String.valueOf(stationsAfterDeleting));
        } catch (Exception e) {
            logger.error("Request failed. Exception error is: " + e);
        }
        return stationsAfterDeleting;
    }


    @PostMapping("/fireStation")
    public List<FireStation> addFireStation(@RequestBody FireStation stationToAdd) {
        List<FireStation> stationsAfterAdding = null;
        logger.info("http://localhost:8080/firestation");
        logger.info("body: " + stationToAdd);
        try {
            stationsAfterAdding = firestationDAO.deleteFireStation(stationToAdd);
            logger.info(String.valueOf(stationsAfterAdding));
        } catch (Exception e) {
            logger.error("Request failed. Exception error is: " + e);
        }
        return stationsAfterAdding;
    }


    @PutMapping("/firestation")
    public List<FireStation> updateFirestation(@RequestBody FireStation stationToUpdate) {
        List<FireStation> stationsAfterModification = null;
        logger.info("http://localhost:8080/firestation");
        logger.info("body: " + stationToUpdate);
        try {
            stationsAfterModification = firestationDAO.deleteFireStation(stationToUpdate);
            logger.info(String.valueOf(stationsAfterModification));
        } catch (Exception e) {
            logger.error("Request failed. Exception error is: " + e);
        }
        return stationsAfterModification;
    }
}
