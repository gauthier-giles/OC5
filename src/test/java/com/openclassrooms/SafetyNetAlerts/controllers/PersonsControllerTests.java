package com.openclassrooms.SafetyNetAlerts.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class PersonsControllerTests {

    @Autowired
    private MockMvc mockMvc;

    static ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testGetPerson() throws Exception {
        mockMvc.perform(get("/person"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void testGetPersonsEmailAtCity() throws Exception {
        mockMvc.perform(get("/communityEmail?city=Culver"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void testGetPersonInfo() throws Exception {
        mockMvc.perform(get("/personInfo?lastName=Boyd"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void testGetChildAtAddress() throws Exception {
        mockMvc.perform(get("/childAlert?address=1509%20Culver%20St"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void testGetPersonsAndMedicalRecordsAndStationNumberOfAddress() throws Exception {
        mockMvc.perform(get("/fire?address=1509%20Culver%20St"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void testDeleteMedicalRecord() throws Exception {
        mockMvc.perform(delete("/person")
                .contentType("application/json")
                .content("{ \"firstName\" : \"Zach\", \"lastName\" : \"Zemicks\"}"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void testPostMedicalRecord() throws Exception {
        mockMvc.perform(post("/person")
                .contentType("application/json")
                .content("{ \"firstName\" : \"Gauthier\", \"lastName\" : \"GILES\", \"birthdate\" : \"08/01/1985\", \"medications\" : [\"NADA\"], \"allergies\" : [ \"NADA\" ] }"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void testPutMedicalRecord() throws Exception {
        mockMvc.perform(post("/person")
                .contentType("application/json")
                .content("{ \"firstName\" : \"Gauthier\", \"lastName\" : \"GILES\", \"birthdate\" : \"08/01/1985\", \"medications\" : [\"NADA\"], \"allergies\" : [ \"NADA\" ] }"))
                .andExpect(status().is2xxSuccessful());
    }

}