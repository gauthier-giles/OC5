package com.openclassrooms.SafetyNetAlerts.DAO;


import com.openclassrooms.SafetyNetAlerts.DTO.JsonDTO;
import com.openclassrooms.SafetyNetAlerts.bean.Persons;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class PersonsDAO implements PersonsDAOInt {

    private static Logger logger = LoggerFactory.getLogger(PersonsDAO.class);

    public Map<String, Persons> persons;

    @Autowired
    public PersonsDAO(JsonDTO jsonDTO) {
        try {
            this.persons = jsonDTO.getPersons();
            logger.info("Persons Class initialized");
        } catch (NullPointerException | IOException e) {
            logger.error("failed to initialized this class", e);
        }
    }

    @Override
    public Map<String, Persons> getPersons() {
        return this.persons;
    }

    @Override
    public List<Persons> updatePerson(Persons personToUpdate) {
        for (Persons person : this.persons.values()) {
            if (personToUpdate.getFirstName().equals(person.getFirstName())
                    && personToUpdate.getLastName().equals(person.getLastName())) {
                person.setAddress(personToUpdate.getAddress());
                person.setCity(personToUpdate.getCity());
                person.setEmail(personToUpdate.getEmail());
                person.setPhone(personToUpdate.getPhone());
                person.setZip(personToUpdate.getZip());
            }
        }
        return new ArrayList<>(this.persons.values());
    }



    @Override
    public List<Persons> addPerson(Persons person) {
        logger.info(String.valueOf(person.getFirstName() + " " + person.getLastName()));
        try {
            this.persons.put(person.getFirstName() + " " + person.getLastName(), person);
            logger.info(person.getFirstName() + " " + person.getLastName() + "is added");
        } catch (Exception e) {
            logger.error("failed to add the person", e);
        }
        return new ArrayList<>(this.persons.values());
    }


    @Override
    public List<Persons> deletePerson(Persons personToDelete) {
        boolean deleted = this.persons.values().removeIf(person -> personToDelete.getFirstName().equals(person.getFirstName())
                && personToDelete.getLastName().equals(person.getLastName()));
        if (deleted) {
            logger.info(personToDelete.getFirstName() + " " + personToDelete.getLastName() + "is delete");
            logger.info("now there is" + persons.size() + "persons");
        } else {
            logger.error("nobody knows as" + personToDelete.getFirstName() + " " + personToDelete.getLastName());
        }
        return new ArrayList<>(this.persons.values());
    }

}
