package com.openclassrooms.SafetyNetAlerts.bean;

import com.fasterxml.jackson.annotation.JsonFilter;
import org.springframework.lang.NonNull;
import java.util.List;

@JsonFilter("medicalFilter")
public class MedicalRecord {
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;

    private String birthdate;
    private List<String> medications;
    private List<String> allergies;

    @NonNull
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NonNull String firstName) {
        this.firstName = firstName;
    }

    @NonNull
    public String getLastName() {
        return lastName;
    }

    public void setLastName(@NonNull String lastName) {
        this.lastName = lastName;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public List<String> getMedications() {
        return medications;
    }

    public void setMedications(List<String> medications) {
        this.medications = medications;
    }

    public List<String> getAllergies() {
        return allergies;
    }

    public void setAllergies(List<String> allergies) {
        this.allergies = allergies;
    }

    public MedicalRecord() { //Default constructor
    }
}

