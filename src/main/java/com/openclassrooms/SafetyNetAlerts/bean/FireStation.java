package com.openclassrooms.SafetyNetAlerts.bean;

import org.springframework.lang.NonNull;

//@Getter
//@Setter
//@ToString

public class FireStation {
        @NonNull
        String address;
        String station;



        @NonNull
        public String getAddress() {
                return address;
        }

        public String getStation() {
                return station;
        }

        public void setAddress(@NonNull String address) {
                this.address = address;
        }

        public void setStation(String station) {
                this.station = station;
        }

        public FireStation(@NonNull String address, String station) {
                this.address = address;
                this.station = station;
        }

        public FireStation() { //Default constructor
        }
}
    
