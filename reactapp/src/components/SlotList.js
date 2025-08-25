import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import './SlotList.css';

function SlotList() {
  const [slots, setSlots] = useState([]);
  const [category, setCategory] = useState("all"); // all, vip, handicap, regular
  const navigate = useNavigate();

  // Fetch slots from backend
  useEffect(() => {
    fetchSlots();
  }, []);

  const fetchSlots = async () => {
    try {
      const res = await axios.get("https://8080-edebaceebefbedebeccfdbeabafcfcfebf.premiumproject.examly.io/api/slots");
      setSlots(res.data);
    } catch (err) {
      console.error("Error fetching slots:", err);
    }
  };

  // Filter change
  const handleCategoryChange = (e) => {
    setCategory(e.target.value);
  };

  // Navigate to booking form
  const handleBook = (slot) => {
    navigate("/booking", { state: { slot } }); // pass slot object
  };

  // Filter slots by category
  const filteredSlots = slots.filter(slot => 
    category === "all" ? true : slot.vehicleType.toLowerCase() === category
  );

  return (
    <div className="slotlist-container">
      <h1>Available Parking Slots</h1>

      {/* Category Filter */}
      <div className="category-filter">
        <label>Category:</label>
        <select value={category} onChange={handleCategoryChange}>
          <option value="all">All</option>
          <option value="vip">VIP</option>
          <option value="handicap">Handicap</option>
          <option value="regular">Regular</option>
        </select>
      </div>

      <div className="slots-grid">
        {filteredSlots.map(slot => (
          <div key={slot.id} className={`slot-card ${!slot.isAvailable ? "unavailable" : ""}`}>
            <h3>Slot: {slot.slotNumber}</h3>
            <p>Type: {slot.vehicleType}</p>
            <p>Hourly Rate: ₹{slot.hourlyRate}</p>
            <p>Status: {slot.isAvailable ? "Available" : "Booked"}</p>
            <button 
              disabled={!slot.isAvailable} 
              onClick={() => handleBook(slot)}
            >
              {slot.isAvailable ? "Book" : "Unavailable"}
            </button>
          </div>
        ))}
      </div>
    </div>
  );
}

export default SlotList;

