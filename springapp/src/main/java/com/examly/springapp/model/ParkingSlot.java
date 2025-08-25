package com.examly.springapp.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "parking_slots")
public class ParkingSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String slotNumber;     // instead of slotName
    private String vehicleType;    // instead of slotType
    private Double hourlyRate;     // instead of rate
    private Boolean isAvailable;   // instead of isAvailable
    private String description;

    private String location;
    private Integer floor;
    private String section;

    private String createdBy;
    private String updatedBy;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Custom constructor for quick object creation
    public ParkingSlot(Long id, String slotNumber, String vehicleType, boolean isAvailable, double hourlyRate) {
        this.id = id;
        this.slotNumber = slotNumber;
        this.vehicleType = vehicleType;
        this.isAvailable = isAvailable;
        this.hourlyRate = hourlyRate;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}

