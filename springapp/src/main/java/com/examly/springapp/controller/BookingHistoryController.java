package com.examly.springapp.controller;

import com.examly.springapp.model.Booking;
import com.examly.springapp.model.BookingHistory;
import com.examly.springapp.service.BookingHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/history")
@CrossOrigin(origins = "https://8081-edebaceebefbedebeccfdbeabafcfcfebf.premiumproject.examly.io/")
public class BookingHistoryController {

    @Autowired
    private BookingHistoryService bookingHistoryService;

    // Create history record
    @PostMapping
    public ResponseEntity<BookingHistory> createHistory(@RequestBody BookingHistory history) {
        return ResponseEntity.ok(bookingHistoryService.addHistory(history));
    }

    // Get all history
    @GetMapping
    public ResponseEntity<List<BookingHistory>> getAllHistory() {
        return ResponseEntity.ok(bookingHistoryService.getAllHistory());
    }

    // Get history by booking ID
    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<List<BookingHistory>> getHistoryByBookingId(@PathVariable Long bookingId) {
        return ResponseEntity.ok(bookingHistoryService.getHistoryByBookingId(bookingId));
    }

    // Get single history by ID
    @GetMapping("/{id}")
    public ResponseEntity<BookingHistory> getHistoryById(@PathVariable Long id) {
        return ResponseEntity.ok(bookingHistoryService.getHistoryById(id));
    }

    // Delete history
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteHistory(@PathVariable Long id) {
        bookingHistoryService.deleteHistory(id);
        return ResponseEntity.ok("History deleted successfully");
    }
    @PostMapping("/status-change")
    public ResponseEntity<BookingHistory> addStatusChange(
            @RequestParam Long bookingId,
            @RequestParam String previousStatus,
            @RequestParam String newStatus,
            @RequestParam(required = false) String notes) {

        // Normally, fetch booking from BookingService
        Booking booking = new Booking();
        booking.setBookingId(bookingId); // Minimal object for history reference

        BookingHistory history = bookingHistoryService.addStatusChangeHistory(booking, previousStatus, newStatus, notes);
        return ResponseEntity.ok(history);
    }

    // Get history by user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BookingHistory>> getHistoryByUser(@PathVariable String userId) {
        return ResponseEntity.ok(bookingHistoryService.getHistoryByUser(userId));
    }

    // Get booking history summary
    @GetMapping("/summary/{bookingId}")
    public ResponseEntity<String> getHistorySummary(@PathVariable Long bookingId) {
        return ResponseEntity.ok(bookingHistoryService.getHistorySummary(bookingId));
    }

    // Delete all history for a booking
    @DeleteMapping("/booking/{bookingId}")
    public ResponseEntity<String> deleteHistoryByBooking(@PathVariable Long bookingId) {
        bookingHistoryService.deleteHistoryByBooking(bookingId);
        return ResponseEntity.ok("All history for booking " + bookingId + " deleted successfully");
    }
}

