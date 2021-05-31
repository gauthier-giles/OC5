package com.openclassrooms.SafetyNetAlerts.bean;


import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.lang.NonNull;

@Getter
@Setter
@ToString
@JsonFilter("personFilter")
public class Persons {
    @NonNull
    String firstName;
    @NonNull
    String lastName;

    String address;
    String city;
    String zip;
    String phone;
    String email;

    public Persons() { //Default constructor
    }
}

