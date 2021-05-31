package com.openclassrooms.SafetyNetAlerts.DAO;

import com.openclassrooms.SafetyNetAlerts.bean.FireStation;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.List;

public interface FireStationDAOInt {
    List<FireStation> updateFireStation(FireStation fireStation) throws IOException, ParseException;
    List<FireStation> addFireStation(FireStation fireStation) throws IOException, ParseException;
    List<FireStation> deleteFireStation(FireStation fireStation);
    List<FireStation> getFireStations();


}
