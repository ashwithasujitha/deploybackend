import React from "react";
import { Link } from "react-router-dom";
import './Navbar.css';

function Navbar() {
  const isLoggedIn = !!localStorage.getItem("user");

  return (
    <nav className="navbar">
      <div className="navbar-logo">MyApp</div>
      {isLoggedIn && (
        <div className="navbar-links">
          <Link to="/slots" className="nav-link">Slot Type</Link>
          <Link to="/history" className="nav-link">Booking History</Link>
          <Link to="/payment" className="nav-link">Payment</Link>
        </div>
      )}
    </nav>
  );
}

export default Navbar;

