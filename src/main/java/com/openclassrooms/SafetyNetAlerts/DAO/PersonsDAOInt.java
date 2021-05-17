package com.openclassrooms.SafetyNetAlerts.DAO;

import com.openclassrooms.SafetyNetAlerts.bean.Persons;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.List;

public interface PersonsDAOInt {
    List<Persons> updatePerson(Persons person) throws IOException, ParseException;
    List<Persons> addPerson(Persons person) throws IOException, ParseException;
    List<Persons> deletePerson(Persons person);
    List<Persons> getPersons();
}
