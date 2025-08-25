import React, { useState } from "react";
import Registeration from "./Registeration";
import Login from "./Login";

function AuthPage() {
  const [isRegistered, setIsRegistered] = useState(true); // true = show login, false = show register

  return (
    <div className="auth-page">
      {isRegistered ? <Login /> : <Registeration />}
      <p style={{ textAlign: "center", marginTop: "15px" }}>
        {isRegistered ? "Don't have an account? " : "Already registered? "}
        <span
          style={{ color: "blue", cursor: "pointer", textDecoration: "underline" }}
          onClick={() => setIsRegistered(!isRegistered)}
        >
          {isRegistered ? "Register here" : "Login here"}
        </span>
      </p>
    </div>
  );
}

export default AuthPage;

