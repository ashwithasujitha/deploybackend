import React, { useState, useEffect } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import axios from "axios";
import './BookingForm.css';

function BookingForm({ user }) {
  const location = useLocation();
  const navigate = useNavigate();
  const { slot } = location.state || {}; // Safe fallback
  const [formData, setFormData] = useState({ startTime: "", endTime: "", vehicleNumber: "" });
  const [error, setError] = useState("");
  const [cost, setCost] = useState(0);

  const BACKEND_URL = "https://8080-edebaceebefbedebeccfdbeabafcfcfebf.premiumproject.examly.io"; 

  // Redirect if no slot
  useEffect(() => {
    if (!slot) {
      alert("No slot selected! Redirecting to slot list.");
      navigate("/slots");
    }
  }, [slot, navigate]);

  // Calculate estimated cost
  useEffect(() => {
    const { startTime, endTime } = formData;
    if (startTime && endTime && slot) {
      const hours = Math.ceil((new Date(endTime) - new Date(startTime)) / (1000 * 60 * 60));
      setCost(hours * (slot.hourlyRate || 0));
    }
  }, [formData, slot]);

  const handleChange = (e) => setFormData({ ...formData, [e.target.name]: e.target.value });

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!slot || !user) return;

    try {
      await axios.post(
        `${BACKEND_URL}/api/bookings/create`,
        null, // Spring backend expects RequestParams
        { params: { 
            slotId: slot?.id, 
            userId: user?.id, 
            price: cost, 
            vehicleNumber: formData.vehicleNumber 
        } }
      );
      alert("Booking successful!");
      navigate("/mybookings");
    } catch (err) {
      setError(err.response?.data || err.message || "Booking failed!");
    }
  };

  if (!slot) return null; // nothing while redirecting


  return (
    <div className="booking-container">
      <h2>Book Slot: {slot.slotNumber}</h2>
      <p>Type: {slot.vehicleType}</p>
      <p>Hourly Rate: ₹{slot.hourlyRate}</p>
      {error && <p className="error">[Error - You need to specify the message]</p>}

      <form onSubmit={handleSubmit} className="booking-form">
        <label>Vehicle Number:</label>
        <input
          type="text"
          name="vehicleNumber"
          value={formData.vehicleNumber}
          onChange={handleChange}
          placeholder="Enter your vehicle number"
          required
        />

        <label>Start Time:</label>
        <input
          type="datetime-local"
          name="startTime"
          value={formData.startTime}
          onChange={handleChange}
          required
        />

        <label>End Time:</label>
        <input
          type="datetime-local"
          name="endTime"
          value={formData.endTime}
          onChange={handleChange}
          required
        />

        <p className="cost">Estimated Cost: ₹{cost}</p>
        <button type="submit" className="book-btn">Book</button>
      </form>
    </div>
  );
}

export default BookingForm;