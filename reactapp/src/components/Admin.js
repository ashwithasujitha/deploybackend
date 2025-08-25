import React, { useEffect, useState } from "react";
import axios from "axios";
import "./Admin.css";

const Admin= () => {
  const [stats, setStats] = useState({
    totalBookings: 0,
    totalRevenue: 0,
    occupiedSlots: 0,
    availableSlots: 0,
  });

  const [users, setUsers] = useState([]);
  const [slots, setSlots] = useState([]);

  // Fetch dashboard stats
  const fetchStats = async () => {
    try {
      const res = await axios.get("https://8080-edebaceebefbedebeccfdbeabafcfcfebf.premiumproject.examly.io/api/admin/stats");
      setStats(res.data);
    } catch (err) {
      console.error(err);
    }
  };

  // Fetch all users
  const fetchUsers = async () => {
    try {
      const res = await axios.get("https://8080-edebaceebefbedebeccfdbeabafcfcfebf.premiumproject.examly.io/api/users");
      setUsers(res.data);
    } catch (err) {
      console.error(err);
    }
  };

  // Fetch all slots
  const fetchSlots = async () => {
    try {
      const res = await axios.get("https://8080-edebaceebefbedebeccfdbeabafcfcfebf.premiumproject.examly.io/api/slots");
      setSlots(res.data);
    } catch (err) {
      console.error(err);
    }
  };

  useEffect(() => {
    fetchStats();
    fetchUsers();
    fetchSlots();
  }, []);

 
 return (
    <div className="admin-dashboard">
      <h1>Admin Dashboard</h1>

      {/* Stats Section */}
      <div className="stats-container">
        <div className="stat-card">
          <h3>Total Bookings</h3>
          <p>{stats.totalBookings}</p>
        </div>
        <div className="stat-card">
          <h3>Total Revenue</h3>
          <p>${stats.totalRevenue}</p>
        </div>
        <div className="stat-card">
          <h3>Occupied Slots</h3>
          <p>{stats.occupiedSlots}</p>
        </div>
        <div className="stat-card">
          <h3>Available Slots</h3>
          <p>{stats.availableSlots}</p>
        </div>
      </div>

      {/* Users Section */}
      <h2>Users</h2>
      <table className="admin-table">
        <thead>
          <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Email</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {users.map((u) => (
            <tr key={u.id}>
              <td>{u.id}</td>
              <td>{u.firstName} {u.lastName}</td>
              <td>{u.email}</td>
              <td>
                <button>Edit</button>
                <button>Delete</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      {/* Slots Section */}
      <h2>Parking Slots</h2>
      <table className="admin-table">
        <thead>
          <tr>
            <th>ID</th>
            <th>Slot Number</th>
            <th>Type</th>
            <th>Rate</th>
            <th>Status</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {slots.map((s) => (
            <tr key={s.id}>
              <td>{s.id}</td>
              <td>{s.slotNumber}</td>
              <td>{s.vehicleType}</td>
              <td>${s.hourlyRate}</td>
              <td>{s.isAvailable ? "Available" : "Occupied"}</td>
              <td>
                <button>Edit</button>
                <button>Delete</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default Admin;