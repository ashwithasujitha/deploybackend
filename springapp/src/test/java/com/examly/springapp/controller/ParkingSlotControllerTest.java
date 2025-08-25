package com.examly.springapp.controller;

import com.examly.springapp.model.ParkingSlot;
import com.examly.springapp.repository.ParkingSlotRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ParkingSlotControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ParkingSlotRepository parkingSlotRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        parkingSlotRepository.deleteAll();
        parkingSlotRepository.saveAll(List.of(
                new ParkingSlot(null, "A1", "Regular", true, 10.0),
                new ParkingSlot(null, "B1", "VIP", true, 20.0)
        ));
    }

    @Test
    void getAllParkingSlotsTest() throws Exception {
        mockMvc.perform(get("/api/slots"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].slotNumber").value("A1"));
    }

    @Test
    void createSlotUniqueTest() throws Exception {
        ParkingSlot slot = new ParkingSlot(null, "X1", "Regular", true, 12.5);
        mockMvc.perform(post("/api/slots")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(slot)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.slotNumber").value("X1"));

        // Duplicate slotNumber
        mockMvc.perform(post("/api/slots")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(slot)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").exists());
    }
}
