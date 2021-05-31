package com.openclassrooms.SafetyNetAlerts.DAO;

import com.openclassrooms.SafetyNetAlerts.bean.FireStation;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface FireStationDAOInt {
    List<FireStation> updateFirestation(FireStation fireStation) throws IOException, ParseException;
    List<FireStation> addFirestation(FireStation fireStation) throws IOException, ParseException;
   //List<FireStation> deleteFirestation(FireStation fireStation);
    Map<String, FireStation> getFireStations();

    List<FireStation> deleteFireStation(FireStation stationToDelete);


}
