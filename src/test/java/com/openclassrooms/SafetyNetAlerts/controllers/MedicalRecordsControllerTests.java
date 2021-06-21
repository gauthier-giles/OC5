package com.openclassrooms.SafetyNetAlerts.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MedicalRecordsControllerTests {
    @Autowired
    private MockMvc mockMvc;

    static ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testGetMedicalRecords() throws Exception {
        mockMvc.perform(get("/medicalrecord"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void testDeleteMedicalRecord() throws Exception {
        mockMvc.perform(delete("/medicalrecord")
                .contentType("application/json")
                .content("{ \"firstName\" : \"Zach\", \"lastName\" : \"Zemicks\"}"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void testPostMedicalRecord() throws Exception {
        mockMvc.perform(post("/medicalrecord")
                .contentType("application/json")
                .content("{ \"firstName\" : \"Gauthier\", \"lastName\" : \"GILES\", " +
                        "\"birthdate\" : \"01/01/1985\", \"medications\" : [\"Nada\"], \"allergies\" : [ \"RIEN\" ] }"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void testPutMedicalRecord() throws Exception {
        mockMvc.perform(post("/medicalrecord")
                .contentType("application/json")
                .content("{ \"firstName\" : \"Gauthier\", \"lastName\" : \"GILES\"," +
                        " \"birthdate\" : \"01/01/1985\", \"medications\" : [\"Nada\"], \"allergies\" : [ \"RIEN\" ] }"))
                .andExpect(status().is2xxSuccessful());
        System.out.println("BBBBBBBBBBB");
    }


}
