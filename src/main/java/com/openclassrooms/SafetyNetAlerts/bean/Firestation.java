package com.openclassrooms.SafetyNetAlerts.bean;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.lang.NonNull;

@Getter
@Setter
@ToString

public class Firestation {
    @NonNull
    String address;
    String station;
    }

