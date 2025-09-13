package com.example.nhom2_st22b_web2.controller;

import com.example.nhom2_st22b_web2.dto.AuthResponse;
import com.example.nhom2_st22b_web2.dto.LoginRequest;
import com.example.nhom2_st22b_web2.dto.RegisterRequest;
import com.example.nhom2_st22b_web2.models.Company;
import com.example.nhom2_st22b_web2.models.UserDemo;
import com.example.nhom2_st22b_web2.service.CompanyService;
import com.example.nhom2_st22b_web2.service.UserService;
import com.example.nhom2_st22b_web2.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private CompanyService companyService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            // Check if email already exists
            if (userService.findByEmail(request.getEmail()).isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Email already exists");
            }

            UserDemo user = new UserDemo();
            user.setName(request.getName());
            user.setEmail(request.getEmail());
            user.setPassword(request.getPassword());

            // Set company if provided
            if (request.getCompanyId() != null) {
                Company company = companyService.findById(request.getCompanyId());
                if (company != null) {
                    user.setCompany(company);
                }
            }

            UserDemo createdUser = userService.saveOrUpdate(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Registration failed: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = jwtUtil.generateToken(userDetails.getUsername(), userDetails.getAuthorities());

            List<String> roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            AuthResponse response = new AuthResponse(token, userDetails.getUsername(), roles);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Login failed: Invalid email or password");
        }
    }
}
