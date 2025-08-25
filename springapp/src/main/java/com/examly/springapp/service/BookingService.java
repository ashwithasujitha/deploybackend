package com.examly.springapp.service;

import com.examly.springapp.model.Booking;
import com.examly.springapp.model.ParkingSlot;
import com.examly.springapp.repository.BookingRepository;
import com.examly.springapp.repository.ParkingSlotRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final ParkingSlotRepository parkingSlotRepository;

    public BookingService(BookingRepository bookingRepository, ParkingSlotRepository parkingSlotRepository) {
        this.bookingRepository = bookingRepository;
        this.parkingSlotRepository = parkingSlotRepository;
    }

    // ===========================
    // Existing methods
    // ===========================

    // Create a new booking
    public Booking createBooking(Long slotId, String userId, double price) {
        Optional<ParkingSlot> optionalSlot = parkingSlotRepository.findById(slotId);
        if (optionalSlot.isEmpty()) {
            throw new RuntimeException("Parking slot not found");
        }

        ParkingSlot parkingSlot = optionalSlot.get();
        if (!parkingSlot.getIsAvailable()) {
            throw new RuntimeException("Parking slot already booked");
        }

        Booking booking = new Booking();
        booking.setParkingSlot(parkingSlot);
        booking.setUserId(userId);
        booking.setStartTime(LocalDateTime.now());
        booking.setEndTime(LocalDateTime.now().plusHours(1));
        booking.setPrice(price);
        booking.setStatus(Booking.BookingStatus.BOOKED);
        booking.setCreatedAt(LocalDateTime.now());

        // Mark the slot as unavailable
        parkingSlot.setIsAvailable(false);
        parkingSlotRepository.save(parkingSlot);

        return bookingRepository.save(booking);
    }

    // Fetch booking by ID
    public Optional<Booking> getBookingById(Long bookingId) {
        return bookingRepository.findById(bookingId);
    }

    // Cancel a booking
    public Booking cancelBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if (booking.getStatus() != Booking.BookingStatus.BOOKED) {
            throw new RuntimeException("Only booked bookings can be cancelled");
        }

        booking.setStatus(Booking.BookingStatus.CANCELLED);

        // Free up the slot
        ParkingSlot slot = booking.getParkingSlot();
        slot.setIsAvailable(true);
        parkingSlotRepository.save(slot);

        return bookingRepository.save(booking);
    }

    // Complete a booking
    public Booking completeBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        booking.setStatus(Booking.BookingStatus.COMPLETED);

        // Free up the slot
        ParkingSlot slot = booking.getParkingSlot();
        slot.setIsAvailable(true);
        parkingSlotRepository.save(slot);

        return bookingRepository.save(booking);
    }

    // Get all bookings for a specific user
    public List<Booking> getBookingsByUser(String userId) {
        return bookingRepository.findAll()
                .stream()
                .filter(b -> b.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

 
   // Get all bookings for a specific slot
    public List<Booking> getBookingsBySlot(Long slotId) {
        return bookingRepository.findAll()
                .stream()
                .filter(b -> b.getParkingSlot().getId().equals(slotId))
                .collect(Collectors.toList());
    }

    // Calculate total price based on duration and slot rate
    public double calculatePrice(Long slotId, Duration duration) {
        ParkingSlot slot = parkingSlotRepository.findById(slotId)
                .orElseThrow(() -> new RuntimeException("Slot not found"));
        long hours = duration.toHours();
        if (hours <= 0) hours = 1; // Minimum 1 hour
        return hours * slot.getHourlyRate();
    }

    // Get all bookings by status
    public List<Booking> getBookingsByStatus(Booking.BookingStatus status) {
        return bookingRepository.findByStatus(status);
    }

    // Get booking summary
    public String getBookingSummary() {
        long total = bookingRepository.count();
        long booked = bookingRepository.findByStatus(Booking.BookingStatus.BOOKED).size();
        long cancelled = bookingRepository.findByStatus(Booking.BookingStatus.CANCELLED).size();
        long completed = bookingRepository.findByStatus(Booking.BookingStatus.COMPLETED).size();

        return String.format("Total: %d | Booked: %d | Cancelled: %d | Completed: %d", total, booked, cancelled, completed);
    }
}