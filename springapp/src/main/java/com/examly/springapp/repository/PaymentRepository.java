package com.examly.springapp.repository;

import com.examly.springapp.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByUserId(String userId);
    List<Payment> findByBooking_BookingId(Long bookingId);
}

