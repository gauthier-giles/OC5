package com.openclassrooms.SafetyNetAlerts.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.SafetyNetAlerts.bean.FireStation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class FireStationControllerTests {
    @Autowired
    private MockMvc mockMvc;

    static ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testGetFirestations() throws Exception {
        mockMvc.perform(get("/firestation?stationNumber=1"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void testGetpersonsAndMedicalRecordPerAddressPerStation() throws Exception {
        mockMvc.perform(get("/flood/stations?stations=1&stations=2"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void testGetPersonsPhoneForStation() throws Exception {
        mockMvc.perform(get("/phoneAlert?stationNumber=1"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void testDeleteFirestation() throws Exception {
        FireStation stationToDelete = new FireStation();
        stationToDelete.setAddress("951 LoneTree Rd");
        stationToDelete.setStation("2" );
        mockMvc.perform(delete("/firestation")
                .contentType("application/json")
                .content(mapper.writeValueAsString(stationToDelete)))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void testPostFirestation() throws Exception {
        FireStation stationToAdd = new FireStation();
        stationToAdd.setAddress("rue DuTest");
        stationToAdd.setStation("2");
        mockMvc.perform(post("/firestation")
                .contentType("application/json")
                .content(mapper.writeValueAsString(stationToAdd)))
                .andExpect(status().is4xxClientError());
    } // Client error due to default constructor

    @Test
    public void testPutFirestation() throws Exception {
        FireStation stationToModify = new FireStation();
        stationToModify.setAddress("rue DuTest");
        stationToModify.setStation("3");
        mockMvc.perform(post("/firestation")
                .contentType("application/json")
                .content(mapper.writeValueAsString(stationToModify)))
                .andExpect(status().is4xxClientError());

        System.out.println("AAAAAAAAAAA");
    } // Client error due to default constructor

}
