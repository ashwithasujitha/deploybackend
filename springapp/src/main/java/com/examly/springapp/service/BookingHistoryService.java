package com.examly.springapp.service;

import com.examly.springapp.model.Booking;
import com.examly.springapp.model.BookingHistory;
import com.examly.springapp.repository.BookingHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingHistoryService {

    @Autowired
    private BookingHistoryRepository bookingHistoryRepository;

    // Create history record
    public BookingHistory addHistory(BookingHistory history) {
        return bookingHistoryRepository.save(history);
    }

    // Get all history
    public List<BookingHistory> getAllHistory() {
        return bookingHistoryRepository.findAll();
    }

    // Get history by bookingId
    public List<BookingHistory> getHistoryByBookingId(Long bookingId) {
        return bookingHistoryRepository.findByBooking_BookingId(bookingId);
    }

    // Get single history by ID
    public BookingHistory getHistoryById(Long id) {
        return bookingHistoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("History not found with id " + id));
    }

    // Delete history
    public void deleteHistory(Long id) {
        if (!bookingHistoryRepository.existsById(id)) {
            throw new RuntimeException("History not found with id " + id);
        }
        bookingHistoryRepository.deleteById(id);
    }
    public BookingHistory addStatusChangeHistory(Booking booking, String previousStatus, String newStatus, String notes) {
        BookingHistory history = BookingHistory.builder()
                .booking(booking)
                .previousStatus(previousStatus)
                .newStatus(newStatus)
                .statusChange("Status updated")
                .changeDate(Timestamp.valueOf(LocalDateTime.now()))
                .notes(notes)
                .build();

        return bookingHistoryRepository.save(history);
    }

    // Get all history for a user by userId
    public List<BookingHistory> getHistoryByUser(String userId) {
        return bookingHistoryRepository.findAll().stream()
                .filter(h -> h.getBooking().getUserId().equals(userId))
                .toList();
    }

    // Get history summary for a booking (for admin/dashboard)
    public String getHistorySummary(Long bookingId) {
        List<BookingHistory> histories = getHistoryByBookingId(bookingId);
        long totalChanges = histories.size();
        long statusChanges = histories.stream()
                .filter(h -> h.getStatusChange() != null)
                .count();

        return String.format("Booking ID: %d | Total changes: %d | Status changes: %d", bookingId, totalChanges, statusChanges);
    }

    // Delete all history of a booking (for cleanup)
    public void deleteHistoryByBooking(Long bookingId) {
        List<BookingHistory> histories = getHistoryByBookingId(bookingId);
        bookingHistoryRepository.deleteAll(histories);
    }
}

