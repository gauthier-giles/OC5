package com.openclassrooms.SafetyNetAlerts.bean;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.lang.NonNull;

@Getter
@Setter
@ToString
@JsonFilter("FireStationFilter")
public class FireStation {
        @NonNull
        String address;
        String station;
    }
    
