package com.openclassrooms.SafetyNetAlerts.DAO;

import com.openclassrooms.SafetyNetAlerts.bean.Persons;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.List;


public interface PersonsDAOInt {
    List<Persons> updatePerson(Persons persons) throws IOException, ParseException;
    List<Persons> addPerson(Persons persons) throws IOException, ParseException;
    List<Persons> deletePerson(Persons persons);
    List<Persons> getPersons();
}
