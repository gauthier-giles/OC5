package com.openclassrooms.SafetyNetAlerts.bean;


import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.lang.NonNull;

import java.util.List;

@Getter
@Setter
@ToString
@JsonFilter("medicalFilter")
public class MedicalRecord {
    @NonNull
    String firstName;
    @NonNull
    String lastName;
    String birthdate;
    List<String> medications;
    List<String> allergies;
    }

