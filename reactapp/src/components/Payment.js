import React, { useEffect, useState } from "react";
import axios from "axios";
import "./Payment.css";

const Payment = ({ user }) => {
  const [bookings, setBookings] = useState([]);
  const [payments, setPayments] = useState([]);
  const [selectedBooking, setSelectedBooking] = useState("");
  const [amount, setAmount] = useState("");
  const [message, setMessage] = useState("");

  // Fetch bookings
  const fetchBookings = async () => {
    try {
      const res = await axios.get(`https://8080-edebaceebefbedebeccfdbeabafcfcfebf.premiumproject.examly.io/api/bookings/user/${user.id}`);
      setBookings(res.data);
    } catch (err) {
      console.error(err);
    }
  };

  // Fetch payment history
  const fetchPayments = async () => {
    try {
      const res = await axios.get(`https://8080-edebaceebefbedebeccfdbeabafcfcfebf.premiumproject.examly.io/api/payments/user/${user.id}`);
      setPayments(res.data);
    } catch (err) {
      console.error(err);
    }
  };

  useEffect(() => {
    if (user) {
      fetchBookings();
      fetchPayments();
    }
  }, [user]);

  // Handle payment
  const handlePay = async () => {
    if (!selectedBooking || !amount) {
      setMessage("Select a booking and enter amount.");
      return;
    }

    try {
      const res = await axios.post("https://8080-edebaceebefbedebeccfdbeabafcfcfebf.premiumproject.examly.io/api/payments", {
        bookingId: selectedBooking,
        userId: user.id,
        amount: parseFloat(amount),
      });

      setMessage("Payment successful!");
      setAmount("");
      setSelectedBooking("");
      fetchPayments();
    } catch (err) {
      console.error(err);
      setMessage(err.response?.data || "Payment failed");
    }
  };

  return (
    <div className="payment-container">
      <h2>Payment Page</h2>

      {message && <p className="payment-message">{message}</p>}

      <div className="payment-form">
        <label>Select Booking:</label>
        <select
          value={selectedBooking}
          onChange={(e) => setSelectedBooking(e.target.value)}
        >
          <option value="">--Select Booking--</option>
          {bookings.map((b) => (
            <option key={b.bookingId} value={b.bookingId}>
              {b.parkingSlot.slotNumber} | {b.status} | ${b.price}
            </option>
          ))}
        </select>

        <label>Amount:</label>
        <input
          type="number"
          value={amount}
          onChange={(e) => setAmount(e.target.value)}
          placeholder="Enter amount"
        />

        <button onClick={handlePay}>Pay</button>
      </div>

      <h3>Payment History</h3>
      <table className="payment-table">
        <thead>
          <tr>
            <th>Booking ID</th>
            <th>Slot</th>
            <th>Amount</th>
            <th>Status</th>
          </tr>
        </thead>
        <tbody>
          {payments.map((p) => (
            <tr key={p.paymentId}>
              <td>{p.booking.bookingId}</td>
              <td>{p.booking.parkingSlot.slotNumber}</td>
              <td>${p.amount}</td>
              <td>{p.status}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default Payment;
