package com.openclassrooms.SafetyNetAlerts.DaO;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.SafetyNetAlerts.DAO.PersonsDAO;
import com.openclassrooms.SafetyNetAlerts.DTO.JsonDTO;
import com.openclassrooms.SafetyNetAlerts.bean.Persons;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
public class PersonsDAOTests {

    private static Logger logger = LoggerFactory.getLogger(JsonDTO.class);

    @InjectMocks
    PersonsDAO personDAO;

    @Mock
    JsonDTO jsonFileDTO;

    @NonNull
    private static JsonNode jsonNode;
    @NonNull
    private static ObjectMapper mapper;
    @NonNull
    private static Persons john;
    @NonNull
    private static Persons jacob;
    @NonNull
    private static Persons tenley;

    @Before
    public void setUp() throws IOException, org.json.simple.parser.ParseException {
        String DUMMY_JSON = "{\n" +
                "\"persons\": " +
                "[\n" +
                "{ \"firstName\":\"John\", \"lastName\":\"Boyd\", \"address\":\"13 avenue de la République\", \"city\":\"Culver\", \"zip\":\"97451\", \"phone\":\"841-874-6512\", \"email\":\"jaboyd@email.com\" }," +
                "{ \"firstName\":\"Jacob\", \"lastName\":\"Boyd\", \"address\":\"1509 Culver St\", \"city\":\"Culver\", \"zip\":\"97451\", \"phone\":\"841-874-6513\", \"email\":\"drk@email.com\" }, " +
                "{ \"firstName\":\"Tenley\", \"lastName\":\"Boyd\", \"address\":\"1509 Culver St\", \"city\":\"Culver\", \"zip\":\"97451\", \"phone\":\"841-874-6512\", \"email\":\"tenz@email.com\" }" +
                "]}";
        mapper = new ObjectMapper();
        jsonNode = mapper.readTree(DUMMY_JSON);

        // Create beans using dummy json
        JsonNode jsonPersons = jsonNode.get("persons");
        Map<String, Persons> persons = new HashMap<>();
        john = mapper.readValue(String.valueOf(jsonPersons.get(0)), Persons.class);
        jacob = mapper.readValue(String.valueOf(jsonPersons.get(1)), Persons.class);
        tenley = mapper.readValue(String.valueOf(jsonPersons.get(2)), Persons.class);
        persons.put("John Boyd", john);
        persons.put("Jacob Boyd", jacob);
        persons.put("Tenley Boyd", tenley);

        // Create classe with mocked datas
        when(jsonFileDTO.getPersons()).thenReturn(persons);
        personDAO = new PersonsDAO(jsonFileDTO);
    }

    @Test
    public void getPersonsTest() throws JsonProcessingException {
        // WHEN
        List<Persons> persons = personDAO.getPersons();

        // THEN
        Assertions.assertThat(persons).containsExactlyInAnyOrder(john, jacob, tenley);
    }

    @Test
    public void addPersonsTest() throws JsonProcessingException {
        // GIVEN
        String jonanathanJson = "{ \"firstName\":\"Jonanathan\", \"lastName\":\"Marrack\", \"address\":\"29 15th St\", \"city\":\"Culver\", \"zip\":\"97451\", \"phone\":\"841-874-6513\", \"email\":\"drk@email.com\" }";
        Persons jonanathan = mapper.readValue(jonanathanJson, Persons.class);

        // WHEN
        personDAO.addPerson(jonanathan);

        // THEN
        Assertions.assertThat(personDAO.persons.values()).containsExactlyInAnyOrder(john, jacob, tenley, jonanathan);
    }

    @Test
    public void removePersonsTest() throws JsonProcessingException {
        // GIVEN
        String jonanathanJson = "{ \"firstName\":\"Jonanathan\", \"lastName\":\"Marrack\", \"address\":\"29 15th St\", \"city\":\"Culver\", \"zip\":\"97451\", \"phone\":\"841-874-6513\", \"email\":\"drk@email.com\" }";
        Persons jonanathan = mapper.readValue(jonanathanJson, Persons.class);

        // WHEN
        personDAO.deletePerson(jonanathan);

        // THEN
        Assertions.assertThat(personDAO.persons.values()).containsExactlyInAnyOrder(john, jacob, tenley);
    }

    @Test
    public void updatePersonsTest() throws JsonProcessingException {
        // GIVEN
        String jacobJson = "{ \"firstName\":\"Jacob\", \"lastName\":\"Boyd\", \"address\":\"800 avenue guy de la morandais\", \"city\":\"Culver\", \"zip\":\"97451\", \"phone\":\"841-874-6513\", \"email\":\"drk@email.com\" }";
        Persons jacob = mapper.readValue(jacobJson, Persons.class);

        // WHEN
        List<Persons> persons = personDAO.updatePerson(jacob);

        // THEN
        Assertions.assertThat(persons.get(1).getAddress()).isEqualTo("800 avenue guy de la morandais");
    }
}
