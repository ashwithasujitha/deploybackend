package com.examly.springapp.controller;

import com.examly.springapp.model.ParkingSlot;
import com.examly.springapp.model.Booking;
import com.examly.springapp.repository.ParkingSlotRepository;
import com.examly.springapp.repository.BookingRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BookingControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ParkingSlotRepository slotRepo;
    @Autowired
    private BookingRepository bookingRepo;
    @Autowired
    private ObjectMapper objectMapper;

    private ParkingSlot slot1;
    private ParkingSlot slot2;

    @BeforeEach
    void setup() {
        bookingRepo.deleteAll();
        slotRepo.deleteAll();
        slot1 = slotRepo.save(new ParkingSlot(null, "A1", "Regular", true, 5.0));
        slot2 = slotRepo.save(new ParkingSlot(null, "B2", "VIP", true, 10.0));
    }

    @Test
    void createBookingTest() throws Exception {
        Booking booking = new Booking(null, 1L, slot1, "ABC123", LocalDateTime.now().plusHours(1), LocalDateTime.now().plusHours(4), null, null);
        mockMvc.perform(post("/api/bookings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(booking)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.totalCost").value(15.0))
                .andExpect(jsonPath("$.status").value("Confirmed"));
    }
    @Test
    void addingBookingTest() throws Exception {
        Booking booking = new Booking(null, 1L, slot1, "ABC123", LocalDateTime.now().plusHours(1), LocalDateTime.now().plusHours(4), null, null);
        mockMvc.perform(post("/api/bookings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(booking)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.totalCost").value(15.0))
                .andExpect(jsonPath("$.status").value("Confirmed"));
    }

    @Test
    void bookingValidationTest() throws Exception {
        // Unavailable slot
        slot1.setIsAvailable(false);
        slotRepo.save(slot1);
        Booking b1 = new Booking(null, 1L, slot1, "XYZ", LocalDateTime.now().plusHours(2), LocalDateTime.now().plusHours(3), null, null);
        mockMvc.perform(post("/api/bookings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(b1)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").exists());
        // End before start
        Booking b2 = new Booking(null, 1L, slot2, "XYZ", LocalDateTime.now().plusHours(5), LocalDateTime.now().plusHours(2), null, null);
        mockMvc.perform(post("/api/bookings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(b2)))
                .andExpect(status().isBadRequest());
        // Vehicle number empty
        Booking b3 = new Booking(null, 1L, slot2, "", LocalDateTime.now().plusHours(1), LocalDateTime.now().plusHours(2), null, null);
        mockMvc.perform(post("/api/bookings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(b3)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getUserBookingsTest() throws Exception {
        Booking b1 = bookingRepo.save(new Booking(null, 1L, slot1, "ABC111", LocalDateTime.now(), LocalDateTime.now().plusHours(2), 10.0, "Confirmed"));
        Booking b2 = bookingRepo.save(new Booking(null, 1L, slot2, "DEF222", LocalDateTime.now().plusHours(1), LocalDateTime.now().plusHours(3), 20.0, "Confirmed"));
        Booking b3 = bookingRepo.save(new Booking(null, 2L, slot2, "GHI333", LocalDateTime.now(), LocalDateTime.now().plusHours(3), 30.0, "Confirmed"));
        mockMvc.perform(get("/api/bookings/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userId").value(1));
    }

    @Test
    void displayBookingsTest() throws Exception {
        Booking b1 = bookingRepo.save(new Booking(null, 1L, slot1, "ABC111", LocalDateTime.now(), LocalDateTime.now().plusHours(2), 10.0, "Confirmed"));
        Booking b2 = bookingRepo.save(new Booking(null, 1L, slot2, "DEF222", LocalDateTime.now().plusHours(1), LocalDateTime.now().plusHours(3), 20.0, "Confirmed"));
        Booking b3 = bookingRepo.save(new Booking(null, 2L, slot2, "GHI333", LocalDateTime.now(), LocalDateTime.now().plusHours(3), 30.0, "Confirmed"));
        mockMvc.perform(get("/api/bookings/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userId").value(1));
    }

    @Test
    void showParticularBookingsTest() throws Exception {
        Booking b1 = bookingRepo.save(new Booking(null, 1L, slot1, "ABC111", LocalDateTime.now(), LocalDateTime.now().plusHours(2), 10.0, "Confirmed"));
        Booking b2 = bookingRepo.save(new Booking(null, 1L, slot2, "DEF222", LocalDateTime.now().plusHours(1), LocalDateTime.now().plusHours(3), 20.0, "Confirmed"));
        Booking b3 = bookingRepo.save(new Booking(null, 2L, slot2, "GHI333", LocalDateTime.now(), LocalDateTime.now().plusHours(3), 30.0, "Confirmed"));
        mockMvc.perform(get("/api/bookings/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userId").value(1));
    }
    @Test
    void cancelBookingTest() throws Exception {
        Booking b1 = bookingRepo.save(new Booking(null, 1L, slot1, "ABC123", LocalDateTime.now().plusHours(1), LocalDateTime.now().plusHours(2), 5.0, "Confirmed"));
        // Cancel booking
        mockMvc.perform(put("/api/bookings/" + b1.getId() + "/cancel"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("Cancelled"));
        // Try cancel again
        mockMvc.perform(put("/api/bookings/" + b1.getId() + "/cancel"))
                .andExpect(status().isBadRequest());
    }
    @Test
    void rejectBookingTest() throws Exception {
        Booking b1 = bookingRepo.save(new Booking(null, 1L, slot1, "ABC123", LocalDateTime.now().plusHours(1), LocalDateTime.now().plusHours(2), 5.0, "Confirmed"));
        // Cancel booking
        mockMvc.perform(put("/api/bookings/" + b1.getId() + "/cancel"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("Cancelled"));
        // Try cancel again
        mockMvc.perform(put("/api/bookings/" + b1.getId() + "/cancel"))
                .andExpect(status().isBadRequest());
    }
}
