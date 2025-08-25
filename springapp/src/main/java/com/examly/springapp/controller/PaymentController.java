package com.examly.springapp.controller;

import com.examly.springapp.model.Payment;
import com.examly.springapp.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "https://8080-edebaceebefbedebeccfdbeabafcfcfebf.premiumproject.examly.io/")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<Payment> processPayment(@RequestParam Long bookingId,
                                                  @RequestParam String userId,
                                                  @RequestParam Double amount) {
        return ResponseEntity.ok(paymentService.processPayment(bookingId, userId, amount));
    }

    @PostMapping("/refund/{paymentId}")
    public ResponseEntity<Payment> refundPayment(@PathVariable Long paymentId) {
        return ResponseEntity.ok(paymentService.refundPayment(paymentId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Payment>> getPaymentsByUser(@PathVariable String userId) {
        return ResponseEntity.ok(paymentService.getPaymentsByUser(userId));
    }

    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<List<Payment>> getPaymentsByBooking(@PathVariable Long bookingId) {
        return ResponseEntity.ok(paymentService.getPaymentsByBooking(bookingId));
    }

    @GetMapping("/pending")
    public ResponseEntity<List<Payment>> getPendingPayments() {
        return ResponseEntity.ok(paymentService.getPendingPayments());
    }

    @GetMapping("/revenue")
    public ResponseEntity<Double> getTotalRevenue() {
        return ResponseEntity.ok(paymentService.getTotalRevenue());
    }

    @GetMapping("/revenue/user/{userId}")
    public ResponseEntity<Double> getRevenueByUser(@PathVariable String userId) {
        return ResponseEntity.ok(paymentService.getRevenueByUser(userId));
    }
}
