import axios from "axios";

const BASE_URL = "https://8080-edebaceebefbedebeccfdbeabafcfcfebf.premiumproject.examly.io/api";

axios.defaults.headers.common['Content-Type'] = 'application/json';
axios.defaults.withCredentials = true;

export const registerUser = async (userData) => {
  try {
    const response = await axios.post(`${BASE_URL}/auth/register`, userData);
    return response.data;
  } catch (error) {
    throw new Error(error.response?.data?.error || 'Registration failed');
  }
};

export const loginUser = async (credentials) => {
  try {
    const response = await axios.post(`${BASE_URL}/auth/login`, credentials);
    return response.data;
  } catch (error) {
    throw new Error(error.response?.data?.error || 'Login failed');
  }
};
// Fetch bookings for a specific user
export const getUserBookings = async (userId) => {
  try {
    const response = await axios.get(`${BASE_URL}/bookings/user/${userId}`);
    return response.data;
  } catch (error) {
    throw new Error(error.response?.data?.error || 'Fetching user bookings failed');
  }
};

// Cancel a booking
export const cancelBooking = async (bookingId) => {
  try {
    const response = await axios.put(`${BASE_URL}/bookings/cancel/${bookingId}`);
    return response.data;
  } catch (error) {
    throw new Error(error.response?.data?.error || 'Cancel booking failed');
  }
};



