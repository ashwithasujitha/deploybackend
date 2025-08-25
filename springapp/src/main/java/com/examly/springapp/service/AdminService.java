package com.examly.springapp.service;

import com.examly.springapp.model.Admin;
import com.examly.springapp.model.ParkingSlot;
import com.examly.springapp.model.User;
import com.examly.springapp.repository.AdminRepository;
import com.examly.springapp.repository.ParkingSlotRepository;
import com.examly.springapp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    private final AdminRepository adminRepository;
    private final UserRepository userRepository;
    private final ParkingSlotRepository parkingSlotRepository;

    public AdminService(AdminRepository adminRepository,
                        UserRepository userRepository,
                        ParkingSlotRepository parkingSlotRepository) {
        this.adminRepository = adminRepository;
        this.userRepository = userRepository;
        this.parkingSlotRepository = parkingSlotRepository;
    }

    // Admin login
    public Admin loginAdmin(String email, String password) {
        Admin admin = adminRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Admin not found"));
        if (!admin.getPassword().equals(password)) {
            throw new RuntimeException("Invalid password");
        }
        return admin;
    }

    // Register admin
    public Admin registerAdmin(Admin admin) {
        if (adminRepository.findByEmail(admin.getEmail()).isPresent()) {
            throw new RuntimeException("Admin email already exists");
        }
        return adminRepository.save(admin);
    }

  
  // Dashboard stats
    public String getDashboardStats() {
        long totalUsers = userRepository.count();
        long totalSlots = parkingSlotRepository.count();
        long availableSlots = parkingSlotRepository.findByIsAvailable(true).size();
        long occupiedSlots = totalSlots - availableSlots;

        return String.format("Users: %d | Slots: %d | Available: %d | Occupied: %d",
                totalUsers, totalSlots, availableSlots, occupiedSlots);
    }

    // Manage users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // Manage slots
    public List<ParkingSlot> getAllSlots() {
        return parkingSlotRepository.findAll();
    }

    public ParkingSlot addOrUpdateSlot(ParkingSlot slot) {
        return parkingSlotRepository.save(slot);
    }

    public void deleteSlot(Long id) {
        parkingSlotRepository.deleteById(id);
    }
}