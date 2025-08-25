import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { registerUser } from "../utils/api";
import './Registeration.css';

function Registeration() {
  const [form, setForm] = useState({
    username: "",
    email: "",
    password: "",
    firstName:"",
    lastName:""
  });
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
    setError("");
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      if (!form.username || !form.email || !form.password||!form.firstName||!form.lastName) {
        setError("All fields are required!");
        return;
      }
      await registerUser(form);
      alert("Registered successfully!");
      navigate("/home");
    } catch (err) {
      setError(err.message || "Registration failed!");
    }
  };

  return (
    <div className="register-container">
      <h1>Register</h1>
      {error && <p style={{ color: "red" }}>{error}</p>}
      <form onSubmit={handleSubmit}>
        <input
          name="username"
          value={form.username}
          onChange={handleChange}
          placeholder="Username"
        />
        <input
          name="email"
          value={form.email}
          onChange={handleChange}
          placeholder="Email"
        />
        <input
          name="password"
          type="password"
          value={form.password}
          onChange={handleChange}
          placeholder="Password"
        />
        <input
          name="firstName"
          value={form.firstName}
          onChange={handleChange}
          placeholder="firstname"
        />
        <input
          name="lastName"
          value={form.lastName}
          onChange={handleChange}
          placeholder="lastname"
        />
        <button type="submit">Register</button>
      </form>
    </div>
  );
}

export default Registeration;