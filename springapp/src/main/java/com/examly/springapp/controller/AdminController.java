package com.examly.springapp.controller;

import com.examly.springapp.model.Admin;
import com.examly.springapp.model.ParkingSlot;
import com.examly.springapp.model.User;
import com.examly.springapp.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admins")
@CrossOrigin(origins = "https://8080-edebaceebefbedebeccfdbeabafcfcfebf.premiumproject.examly.io/")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/login")
    public ResponseEntity<Admin> login(@RequestParam String email, @RequestParam String password) {
        return ResponseEntity.ok(adminService.loginAdmin(email, password));
    }

    @PostMapping("/register")
    public ResponseEntity<Admin> register(@RequestBody Admin admin) {
        return ResponseEntity.ok(adminService.registerAdmin(admin));
    }

    @GetMapping("/dashboard")
    public ResponseEntity<String> getDashboardStats() {
        return ResponseEntity.ok(adminService.getDashboardStats());
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(adminService.getAllUsers());
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        adminService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }

    @GetMapping("/slots")
    public ResponseEntity<List<ParkingSlot>> getAllSlots() {
        return ResponseEntity.ok(adminService.getAllSlots());
    }

    @PostMapping("/slots")
    public ResponseEntity<ParkingSlot> addOrUpdateSlot(@RequestBody ParkingSlot slot) {
        return ResponseEntity.ok(adminService.addOrUpdateSlot(slot));
    }

    @DeleteMapping("/slots/{id}")
    public ResponseEntity<String> deleteSlot(@PathVariable Long id) {
        adminService.deleteSlot(id);
        return ResponseEntity.ok("Slot deleted successfully");
    }
}

