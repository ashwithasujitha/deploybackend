package com.examly.springapp.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;

    private String userId;

    @ManyToOne
    @JoinColumn(name = "slot_id", nullable = false)
    private ParkingSlot parkingSlot;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Double price;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    private String vehicleNumber;
    private LocalDateTime createdAt;

    public enum BookingStatus {
        BOOKED, CANCELLED, COMPLETED
    }
}

