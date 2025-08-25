import React, { useEffect, useState } from "react";
import { getUserBookings, cancelBooking } from "../utils/api";
import './UserBookings.css';

function UserBookings({ userId }) {
  const [bookings, setBookings] = useState([]);

  useEffect(() => {
    const fetchBookings = async () => {
      const data = await getUserBookings(userId);
      setBookings(data);
    };
    fetchBookings();
  }, [userId]);

  const handleCancel = async (id) => {
    await cancelBooking(id);
    setBookings(bookings.filter((b) => b.bookingId !== id));
  };

  return (
    <div>
      <h2>My Bookings</h2>
      {bookings.length === 0 && <p>No bookings found</p>}
      {bookings.map((b) => (
        <div key={b.bookingId} style={{ border: "1px solid black", margin: "5px", padding: "5px" }}>
          <p>Slot: {b.parkingSlot.slotNumber}</p>
          <p>Time: {new Date(b.startTime).toLocaleString()} - {new Date(b.endTime).toLocaleString()}</p>
          <p>Price: ${b.price}</p>
          <p>Status: {b.status}</p>
          {b.status === "BOOKED" && <button onClick={() => handleCancel(b.bookingId)}>Cancel</button>}
        </div>
      ))}
    </div>
  );
}

export default UserBookings;

