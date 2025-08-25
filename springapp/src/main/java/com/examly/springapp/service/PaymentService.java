package com.examly.springapp.service;

import com.examly.springapp.model.Booking;
import com.examly.springapp.model.Payment;
import com.examly.springapp.repository.BookingRepository;
import com.examly.springapp.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;

    public PaymentService(PaymentRepository paymentRepository, BookingRepository bookingRepository) {
        this.paymentRepository = paymentRepository;
        this.bookingRepository = bookingRepository;
    }

    // Process payment
    public Payment processPayment(Long bookingId, String userId, Double amount) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        Payment payment = Payment.builder()
                .booking(booking)
                .userId(userId)
                .amount(amount)
                .status(Payment.PaymentStatus.COMPLETED) // mock completed
                .paymentDate(LocalDateTime.now())
                .build();

        return paymentRepository.save(payment);
    }

    // Refund payment
    public Payment refundPayment(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        payment.setStatus(Payment.PaymentStatus.FAILED);
        return paymentRepository.save(payment);
    }

    // Payment history for user
    public List<Payment> getPaymentsByUser(String userId) {
        return paymentRepository.findByUserId(userId);
    }

    // Payment status for booking
    public List<Payment> getPaymentsByBooking(Long bookingId) {
        return paymentRepository.findByBooking_BookingId(bookingId);
    }

    // Pending payments
    public List<Payment> getPendingPayments() {
        return paymentRepository.findAll().stream()
                .filter(p -> p.getStatus() == Payment.PaymentStatus.PENDING)
                .toList();
    }

    // Total revenue
    public double getTotalRevenue() {
        return paymentRepository.findAll().stream()
                .filter(p -> p.getStatus() == Payment.PaymentStatus.COMPLETED)
                .mapToDouble(Payment::getAmount)
                .sum();
    }

    // Revenue by user
    public double getRevenueByUser(String userId) {
        return paymentRepository.findByUserId(userId).stream()
                .filter(p -> p.getStatus() == Payment.PaymentStatus.COMPLETED)
                .mapToDouble(Payment::getAmount)
                .sum();
    }
}
