package com.openclassrooms.SafetyNetAlerts.DAO;

import com.openclassrooms.SafetyNetAlerts.bean.MedicalRecord;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface MedicalRecordDAOInt {
    List<MedicalRecord> updateMedicalRecord(MedicalRecord medicalRecord) throws IOException, ParseException;
    List<MedicalRecord> addMedicalRecord(MedicalRecord medicalRecord) throws IOException, ParseException;
    List<MedicalRecord> deleteMedicalRecord(MedicalRecord medicalRecord);
    Map<String, MedicalRecord> getMedicalRecords();
//    List<MedicalRecord> getMedicalRecords();

}
