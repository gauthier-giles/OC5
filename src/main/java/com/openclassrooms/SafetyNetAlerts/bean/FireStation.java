package com.openclassrooms.SafetyNetAlerts.bean;

import org.springframework.lang.NonNull;

public class FireStation {
        @NonNull
        private String address;
        private String station;

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

        public FireStation() { //Default constructor
        }
}
    
