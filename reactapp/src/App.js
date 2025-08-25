import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Login from "./components/Login";
import Home from "./components/Home";
import SlotList from "./components/SlotList";
import UserBookings from "./components/UserBookings";
import Payment from "./components/Payment";

function App() {
  return (
    <Router>
      <Routes>
        {/* Login Page */}
        <Route path="/" element={<Login />} />

        {/* Home Page */}
        <Route path="/home" element={<Home />} />

        {/* Other Pages */}
        <Route path="/slots" element={<SlotList />} />
        <Route path="/history" element={<UserBookings />} />
        <Route path="/payment" element={<Payment />} />
      </Routes>
    </Router>
  );
}

export default App;

