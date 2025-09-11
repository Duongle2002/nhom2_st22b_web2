package com.example.nhom2_st22b_web2.service;
import com.example.nhom2_st22b_web2.models.Role;
import com.example.nhom2_st22b_web2.models.UserDemo;
import com.example.nhom2_st22b_web2.repositorys.RoleRepository;
import com.example.nhom2_st22b_web2.repositorys.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDemo saveOrUpdate(UserDemo user) {
        // Create
        if (user.getId() == null) {
            if (userRepository.findByEmail(user.getEmail()).isPresent()) {
                throw new RuntimeException("Email already exists");
            }
            String rawPassword = user.getPassword();
            if (rawPassword == null || rawPassword.isBlank()) {
                throw new RuntimeException("Password is required");
            }
            user.setPassword(ensureEncoded(rawPassword));
            // Assign default USER role if present and none provided
            if (user.getRoles() == null || user.getRoles().isEmpty()) {
                Optional<Role> userRole = roleRepository.findByName("USER");
                userRole.ifPresent(role -> {
                    Set<Role> roles = new HashSet<>();
                    roles.add(role);
                    user.setRoles(roles);
                });
            }
            return userRepository.save(user);
        }

        // Update
        Optional<UserDemo> existingOpt = userRepository.findById(user.getId());
        if (existingOpt.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        UserDemo existing = existingOpt.get();

        // If email changed, ensure unique
        if (user.getEmail() != null && !user.getEmail().equals(existing.getEmail())) {
            Optional<UserDemo> byEmail = userRepository.findByEmail(user.getEmail());
            if (byEmail.isPresent() && !byEmail.get().getId().equals(existing.getId())) {
                throw new RuntimeException("Email already exists");
            }
            existing.setEmail(user.getEmail());
        }

        if (user.getName() != null) {
            existing.setName(user.getName());
        }
        if (user.getCompany() != null) {
            existing.setCompany(user.getCompany());
        }

        // Password: only update if provided; avoid double-encoding
        if (user.getPassword() != null && !user.getPassword().isBlank()) {
            existing.setPassword(ensureEncoded(user.getPassword()));
        }

        // Roles: keep provided if non-empty, else preserve existing
        if (user.getRoles() != null && !user.getRoles().isEmpty()) {
            existing.setRoles(user.getRoles());
        }

        return userRepository.save(existing);
    }

    public List<UserDemo> getAllUsers() {
        return userRepository.findAll();
    }

    public UserDemo findById(Integer id) {
        Optional<UserDemo> user = userRepository.findById(id);
        return user.orElse(null);
    }

    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    public Optional<UserDemo> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    private String ensureEncoded(String maybeRawPassword) {
        if (maybeRawPassword.startsWith("$2a$") || maybeRawPassword.startsWith("$2b$") || maybeRawPassword.startsWith("$2y$")) {
            return maybeRawPassword;
        }
        return passwordEncoder.encode(maybeRawPassword);
    }
}