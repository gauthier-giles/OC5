package com.openclassrooms.SafetyNetAlerts.DAO;

import com.openclassrooms.SafetyNetAlerts.DTO.JsonDTO;
import com.openclassrooms.SafetyNetAlerts.bean.FireStation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class FireStationDAO implements FireStationDAOInt{

    private static Logger logger = LoggerFactory.getLogger(FireStationDAO.class);

    public Map<String, FireStation> firestations;

    @Autowired
    public FireStationDAO(JsonDTO jsonDTO){
        try {
            this.firestations = jsonDTO.getFireStations();
            logger.info("persons class initialized");
        } catch (NullPointerException | IOException e) {
            logger.error("failed to initialize FirestationsDAO", e);
        }
    }

    @Override
    public List<FireStation> updateFirestation(FireStation stationUpdate){
        for(FireStation firestation: this.firestations.values()) {
            if(firestation.getAddress().equals(stationUpdate.getAddress())){
                firestation.setStation(stationUpdate.getStation());
            }
        }
        return new ArrayList<>(this.firestations.values());
    }


    @Override
    public List<FireStation> addFirestation(FireStation stationToAdd){
        try {
            this.firestations.put(stationToAdd.getAddress(), stationToAdd);
            logger.info(stationToAdd.getAddress() + " is added");
        }   catch(Exception e) {
            logger.error("failed to add the person", e);
        }
        return new ArrayList<>(this.firestations.values());
    }




    @Override
    public List<FireStation> deleteFireStation(FireStation stationToDelete) {
        boolean deleted = this.firestations.values().removeIf(firestation -> stationToDelete.getAddress().equals(firestation.getAddress()));
        if (deleted) {
            logger.info(stationToDelete.getAddress() +  "is deleted");
            logger.info("now there is " + firestations.size() + " Firestations");
        } else {
            logger.error("no Firestation existed in this address" + stationToDelete.getAddress());
        }
        return new ArrayList<>(this.firestations.values());
    }


    @Override
    public Map<String, FireStation> getFireStations() {
        return this.firestations;
        // retourner un Map plutot qu'un ArrayList
    }

}
