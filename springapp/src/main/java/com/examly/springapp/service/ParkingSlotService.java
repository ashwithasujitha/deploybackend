package com.examly.springapp.service;

import com.examly.springapp.model.ParkingSlot;
import com.examly.springapp.repository.ParkingSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkingSlotService {

    @Autowired
    private ParkingSlotRepository parkingSlotRepository;

    // Add slot
    public ParkingSlot addParkingSlot(ParkingSlot slot) {
        return parkingSlotRepository.save(slot);
    }

    // Get all slots
    public List<ParkingSlot> getAllSlots() {
        return parkingSlotRepository.findAll();
    }

    // Get slot by ID
    public ParkingSlot getSlotById(Long id) {
        return parkingSlotRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Slot not found with id " + id));
    }

    // Update slot
    public ParkingSlot updateSlot(Long id, ParkingSlot slotDetails) {
        ParkingSlot slot = getSlotById(id);
        slot.setSlotNumber(slotDetails.getSlotNumber());
        slot.setVehicleType(slotDetails.getVehicleType());
        slot.setHourlyRate(slotDetails.getHourlyRate());
        slot.setIsAvailable(slotDetails.getIsAvailable());
        slot.setDescription(slotDetails.getDescription());
        slot.setLocation(slotDetails.getLocation());
        slot.setFloor(slotDetails.getFloor());
        slot.setSection(slotDetails.getSection());
        slot.setUpdatedBy(slotDetails.getUpdatedBy());
        return parkingSlotRepository.save(slot);
    }

    // Delete slot
    public void deleteSlot(Long id) {
        ParkingSlot slot = getSlotById(id);
        parkingSlotRepository.delete(slot);
    }

    // Custom: get available slots
    public List<ParkingSlot> getAvailableSlots() {
        return parkingSlotRepository.findByIsAvailable(true);
    }
    public List<ParkingSlot> getSlotsByVehicleType(String vehicleType) {
        return parkingSlotRepository.findByVehicleType(vehicleType);
    }

    // Get available slots by vehicle type
    public List<ParkingSlot> getAvailableSlotsByVehicleType(String vehicleType) {
        return parkingSlotRepository.findByIsAvailable(true)
                .stream()
                .filter(slot -> slot.getVehicleType().equalsIgnoreCase(vehicleType))
                .toList();
    }

    // Get slots by location, floor, section (any combination)
    public List<ParkingSlot> getSlotsByLocation(String location, Integer floor, String section) {
        return parkingSlotRepository.findAll()
                .stream()
                .filter(slot -> (location == null || slot.getLocation().equalsIgnoreCase(location)) &&
                                (floor == null || slot.getFloor().equals(floor)) &&
                                (section == null || slot.getSection().equalsIgnoreCase(section)))
                .toList();
    }

    // Count total available slots
    public long countAvailableSlots() {
        return parkingSlotRepository.findByIsAvailable(true).size();
    }

    // Count slots by vehicle type
    public long countSlotsByVehicleType(String vehicleType) {
        return parkingSlotRepository.findByVehicleType(vehicleType).size();
    }

    // Toggle availability of a slot
    public ParkingSlot toggleSlotAvailability(Long id) {
        ParkingSlot slot = getSlotById(id);
        slot.setIsAvailable(!slot.getIsAvailable());
        return parkingSlotRepository.save(slot);
    }

    // Get slots summary (professional reporting)
    public String getSlotsSummary() {
        long total = parkingSlotRepository.count();
        long available = countAvailableSlots();
        long cars = countSlotsByVehicleType("Car");
        long bikes = countSlotsByVehicleType("Bike");
        return String.format("Total: %d | Available: %d | Cars: %d | Bikes: %d", total, available, cars, bikes);
    }
}

