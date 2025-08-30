package com.example.nhom2_st22b_web2.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Utility class để tạo hash BCrypt cho mật khẩu
 * Sử dụng để tạo hash cho data.sql hoặc test
 */
public class PasswordGenerator {
    
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        
        // Mật khẩu mới: 123456
        String password = "123456";
        String hashedPassword = encoder.encode(password);
        
        System.out.println("=== PASSWORD HASH GENERATOR ===");
        System.out.println("Password: " + password);
        System.out.println("BCrypt Hash: " + hashedPassword);
        System.out.println("===============================");
        
        // Test verify
        boolean matches = encoder.matches(password, hashedPassword);
        System.out.println("Password matches: " + matches);
        
        // Tạo hash cho từng user
        System.out.println("\n=== USER PASSWORD HASHES ===");
        System.out.println("Admin: " + encoder.encode("123456"));
        System.out.println("Manager: " + encoder.encode("123456"));
        System.out.println("Employee: " + encoder.encode("123456"));
        System.out.println("Guest: " + encoder.encode("123456"));
    }
} 