package com.openclassrooms.SafetyNetAlerts.DAO;

import com.openclassrooms.SafetyNetAlerts.DTO.JsonDTO;
import com.openclassrooms.SafetyNetAlerts.bean.MedicalRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class MedicalRecordDAO implements MedicalRecordDAOInt{

    private static final Logger logger = LoggerFactory.getLogger(MedicalRecordDAO.class);

    public Map<String, MedicalRecord> medicalRecords;

    @Autowired
    public MedicalRecordDAO(JsonDTO jsonFileDTO){
        try {
            this.medicalRecords = jsonFileDTO.getMedicalRecords();
            logger.info("class persons initialized");
        } catch (NullPointerException | IOException e) {
            logger.error("failed to initialize MedicalRecordDao", e);
        }
    }


    @Override
    public List<MedicalRecord> getMedicalRecords() {
        return new ArrayList<>(this.medicalRecords.values());
    }


    @Override
    public List<MedicalRecord> updateMedicalRecord(MedicalRecord recordToUpdate){
        for(MedicalRecord medicalRecord: this.medicalRecords.values()) {
            if(recordToUpdate.getFirstName().equals(medicalRecord.getFirstName())
                    && recordToUpdate.getLastName().equals(medicalRecord.getLastName())) {
                medicalRecord.setBirthdate(recordToUpdate.getBirthdate());
                medicalRecord.setMedications(recordToUpdate.getMedications());
                medicalRecord.setAllergies(recordToUpdate.getAllergies());
            }
        }
        return new ArrayList<>(this.medicalRecords.values());
    }


    @Override
    public List<MedicalRecord> addMedicalRecord(MedicalRecord recordToAdd){
        try{
            this.medicalRecords.put(recordToAdd.getFirstName() + " " + recordToAdd.getLastName(),recordToAdd);
            logger.info(recordToAdd.getFirstName() + " " + recordToAdd.getLastName() + " is added");
        } catch (Exception e){
            logger.error("failed to add", e);
        }
        return new ArrayList<>(this.medicalRecords.values());
    }


    @Override
    public List<MedicalRecord> deleteMedicalRecord(MedicalRecord recordToDelete) {
        boolean deleted = this.medicalRecords.values().removeIf(person -> recordToDelete.getFirstName().equals(person.getFirstName())
                && recordToDelete.getLastName().equals(person.getLastName()));
        if (deleted) {
            logger.info(recordToDelete.getFirstName() + " " + recordToDelete.getLastName() + " is delete");
            logger.info("now there is " + medicalRecords.size() + " persons");
        } else {
            logger.error("nobody knows as " + recordToDelete.getFirstName() + " " + recordToDelete.getLastName());
        }
        return new ArrayList<>(this.medicalRecords.values());
    }




}
