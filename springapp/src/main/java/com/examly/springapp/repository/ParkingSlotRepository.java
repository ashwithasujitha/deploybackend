package com.examly.springapp.repository;

import com.examly.springapp.model.ParkingSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingSlotRepository extends JpaRepository<ParkingSlot, Long> {
    // Example: find by availability
    java.util.List<ParkingSlot> findByIsAvailable(Boolean isAvailable);

    // Example: find by vehicle type
    java.util.List<ParkingSlot> findByVehicleType(String vehicleType);
}

