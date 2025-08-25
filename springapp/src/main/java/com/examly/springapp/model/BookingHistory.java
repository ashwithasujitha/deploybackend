package com.examly.springapp.model;

import jakarta.persistence.*;
import lombok.*;
import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "booking_history")
public class BookingHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long historyId;

    // ✅ Proper relation with Booking
    @ManyToOne
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    private String statusChange;

    private String previousStatus;

    private String newStatus;

    private Timestamp changeDate;

    @Column(columnDefinition = "TEXT")
    private String notes;
}

