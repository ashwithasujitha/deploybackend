import React from "react";
import { Link } from "react-router-dom";

const Home = () => {
  return (
    <div style={{ textAlign: "center", marginTop: "100px" }}>
      <h1>Welcome!</h1>
      <div style={{ margin: "20px" }}>
        <Link to="/slots"><button>Slot Types</button></Link>
      </div>
      <div style={{ margin: "20px" }}>
        <Link to="/history"><button>Booking History</button></Link>
      </div>
      <div style={{ margin: "20px" }}>
        <Link to="/payment"><button>Payment</button></Link>
      </div>
    </div>
  );
};

export default Home;

