package com.examly.springapp.controller;

import com.examly.springapp.model.ParkingSlot;
import com.examly.springapp.service.ParkingSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/slots")
@CrossOrigin(origins = "*")
public class ParkingSlotController {

    @Autowired
    private ParkingSlotService parkingSlotService;

    // Add slot
    @PostMapping
    public ResponseEntity<ParkingSlot> addSlot(@RequestBody ParkingSlot slot) {
        return ResponseEntity.ok(parkingSlotService.addParkingSlot(slot));
    }

    // Get all slots
    @GetMapping
    public ResponseEntity<List<ParkingSlot>> getAllSlots() {
        return ResponseEntity.ok(parkingSlotService.getAllSlots());
    }

    // Get slot by ID
    @GetMapping("/{id}")
    public ResponseEntity<ParkingSlot> getSlotById(@PathVariable Long id) {
        return ResponseEntity.ok(parkingSlotService.getSlotById(id));
    }

    // Update slot
    @PutMapping("/{id}")
    public ResponseEntity<ParkingSlot> updateSlot(@PathVariable Long id, @RequestBody ParkingSlot slotDetails) {
        return ResponseEntity.ok(parkingSlotService.updateSlot(id, slotDetails));
    }

    // Delete slot
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSlot(@PathVariable Long id) {
        parkingSlotService.deleteSlot(id);
        return ResponseEntity.ok("Slot deleted successfully");
    }

    // Get available slots
    @GetMapping("/available")
    public ResponseEntity<List<ParkingSlot>> getisAvailableSlots() {
        return ResponseEntity.ok(parkingSlotService.getAvailableSlots());
    }
    @GetMapping("/vehicle/{type}")
    public ResponseEntity<List<ParkingSlot>> getSlotsByVehicleType(@PathVariable String type) {
        return ResponseEntity.ok(parkingSlotService.getSlotsByVehicleType(type));
    }

    // Get available slots by vehicle type
    @GetMapping("/available/vehicle/{type}")
    public ResponseEntity<List<ParkingSlot>> getAvailableSlotsByVehicleType(@PathVariable String type) {
        return ResponseEntity.ok(parkingSlotService.getAvailableSlotsByVehicleType(type));
    }

    // Get slots by location, floor, section
    @GetMapping("/filter")
    public ResponseEntity<List<ParkingSlot>> filterSlots(
            @RequestParam(required = false) String location,
            @RequestParam(required = false) Integer floor,
            @RequestParam(required = false) String section) {
        return ResponseEntity.ok(parkingSlotService.getSlotsByLocation(location, floor, section));
    }

    // Count total available slots
    @GetMapping("/count/available")
    public ResponseEntity<Long> countAvailableSlots() {
        return ResponseEntity.ok(parkingSlotService.countAvailableSlots());
    }

    // Toggle availability of a slot
    @PatchMapping("/{id}/toggle")
    public ResponseEntity<ParkingSlot> toggleSlotAvailability(@PathVariable Long id) {
        return ResponseEntity.ok(parkingSlotService.toggleSlotAvailability(id));
    }

    // Slots summary
    @GetMapping("/summary")
    public ResponseEntity<String> getSlotsSummary() {
        return ResponseEntity.ok(parkingSlotService.getSlotsSummary());
    }
}

