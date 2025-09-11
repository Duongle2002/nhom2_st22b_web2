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

    public void saveOrUpdate(UserDemo user) {
        if (user.getId() == null && userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Optional<Role> userRole = roleRepository.findByName("USER");
        if (userRole.isPresent()) {
            Set<Role> roles = new HashSet<>();
            roles.add(userRole.get());
            user.setRoles(roles);
        }
        userRepository.save(user);
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
}