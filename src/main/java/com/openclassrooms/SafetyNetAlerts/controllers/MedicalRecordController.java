package com.openclassrooms.SafetyNetAlerts.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.openclassrooms.SafetyNetAlerts.DAO.MedicalRecordDAO;
import com.openclassrooms.SafetyNetAlerts.bean.MedicalRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class MedicalRecordController {
    private static Logger logger = LoggerFactory.getLogger(MedicalRecordController.class);
    private static ObjectMapper mapper = new ObjectMapper();
    private static FilterProvider medicalFilter = new SimpleFilterProvider().addFilter("medicalFilter", SimpleBeanPropertyFilter.serializeAll());

    @Autowired
    MedicalRecordDAO medicalRecordDAO;




    @GetMapping(value = "/medicalrecord", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getMedicalRecords() {
        logger.info("/medicalrecord");
        String medicalRecord = null;
        try {
            medicalRecord = mapper.writer(medicalFilter).withDefaultPrettyPrinter().
                    writeValueAsString(medicalRecordDAO.getMedicalRecords());
            logger.info(medicalRecord);
        }   catch (Exception e) {
            logger.error("failed to get the medical record. Exception error is: " + e);
        }
        return medicalRecord;
    }

    @DeleteMapping(value = "/medicalrecord", produces = MediaType.APPLICATION_JSON_VALUE)
    public String removePerson(@RequestBody MedicalRecord medicalRecord) {
        logger.info("medicalrecord");
        logger.info(String.valueOf(medicalRecord));
        String medicalRecordLeft = null;
        try {
            medicalRecordLeft = mapper.writer(medicalFilter).
                    withDefaultPrettyPrinter().
                    writeValueAsString(medicalRecordDAO.deleteMedicalRecord(medicalRecord));
            logger.info(medicalRecordLeft);
        }   catch (Exception e) {
            logger.error("failed to delete the medical record. Exception error is: " + e);
        }
        return medicalRecordLeft;
    }

    @PostMapping(value = "/medicalrecord", produces = MediaType.APPLICATION_JSON_VALUE)
    public String addPerson(@RequestBody MedicalRecord medicalRecord) {
        logger.info("/medicalrecord");
        logger.info(String.valueOf(medicalRecord));
        String allMedicalRecord = null;
        try {
            allMedicalRecord = mapper.writer(medicalFilter).withDefaultPrettyPrinter().
                    writeValueAsString(medicalRecordDAO.addMedicalRecord(medicalRecord));
            logger.info(allMedicalRecord);
        }   catch (Exception e) {
            logger.error("failed to add the medical record. Exception error is: " + e);
        }
        return allMedicalRecord;
    }

    @PutMapping(value = "/medicalrecord", produces = MediaType.APPLICATION_JSON_VALUE)
    public String updatePerson(@RequestBody MedicalRecord medicalRecord) {
        logger.info("/medicalrecord");
        logger.info(String.valueOf(medicalRecord));
        String allMedicalRecord = null;
        try {
            allMedicalRecord = mapper.writer(medicalFilter).
                    withDefaultPrettyPrinter().
                    writeValueAsString(medicalRecordDAO.updateMedicalRecord(medicalRecord));
            logger.info(allMedicalRecord);
        }   catch (Exception e) {
            logger.error("failed to update the medical record. Exception error is: " + e);
        }
        return allMedicalRecord;
    }


}
