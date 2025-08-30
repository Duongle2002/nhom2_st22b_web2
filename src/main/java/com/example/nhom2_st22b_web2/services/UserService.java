package com.example.nhom2_st22b_web2.services;

import com.example.nhom2_st22b_web2.models.Role;
import com.example.nhom2_st22b_web2.models.User;
import com.example.nhom2_st22b_web2.repositorys.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User saveUser(User user) {
        // Validation cơ bản
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Tên đăng nhập không được để trống");
        }
        if (user.getFullName() == null || user.getFullName().trim().isEmpty()) {
            throw new IllegalArgumentException("Họ tên không được để trống");
        }
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email không được để trống");
        }
        if (user.getRole() == null) {
            throw new IllegalArgumentException("Vai trò không được để trống");
        }
        
        // Kiểm tra username đã tồn tại chưa
        if (user.getId() == null && userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("Tên đăng nhập đã tồn tại: " + user.getUsername());
        }
        
        // Kiểm tra email đã tồn tại chưa
        if (user.getId() == null && userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email đã tồn tại: " + user.getEmail());
        }
        
        // Mã hóa mật khẩu nếu là user mới hoặc mật khẩu đã thay đổi
        if (user.getId() == null || (user.getPassword() != null && !user.getPassword().startsWith("$2a$"))) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        
        return userRepository.save(user);
    }
    
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("Không tìm thấy người dùng với ID: " + id);
        }
        userRepository.deleteById(id);
    }
    
    public User updateUser(Long id, User userDetails) {
        Optional<User> existingUser = userRepository.findById(id);
        
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            
            // Validation cơ bản
            if (userDetails.getUsername() == null || userDetails.getUsername().trim().isEmpty()) {
                throw new IllegalArgumentException("Tên đăng nhập không được để trống");
            }
            if (userDetails.getFullName() == null || userDetails.getFullName().trim().isEmpty()) {
                throw new IllegalArgumentException("Họ tên không được để trống");
            }
            if (userDetails.getEmail() == null || userDetails.getEmail().trim().isEmpty()) {
                throw new IllegalArgumentException("Email không được để trống");
            }
            if (userDetails.getRole() == null) {
                throw new IllegalArgumentException("Vai trò không được để trống");
            }
            
            // Kiểm tra username đã tồn tại chưa (trừ chính nó)
            if (!user.getUsername().equals(userDetails.getUsername()) && 
                userRepository.existsByUsername(userDetails.getUsername())) {
                throw new IllegalArgumentException("Tên đăng nhập đã tồn tại: " + userDetails.getUsername());
            }
            
            // Kiểm tra email đã tồn tại chưa (trừ chính nó)
            if (!user.getEmail().equals(userDetails.getEmail()) && 
                userRepository.existsByEmail(userDetails.getEmail())) {
                throw new IllegalArgumentException("Email đã tồn tại: " + userDetails.getEmail());
            }
            
            user.setUsername(userDetails.getUsername());
            user.setFullName(userDetails.getFullName());
            user.setEmail(userDetails.getEmail());
            user.setPhone(userDetails.getPhone());
            user.setAddress(userDetails.getAddress());
            user.setRole(userDetails.getRole());
            user.setCompany(userDetails.getCompany());
            
            return userRepository.save(user);
        } else {
            throw new IllegalArgumentException("Không tìm thấy người dùng với ID: " + id);
        }
    }
    
    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }
    
    public long countUsers() {
        return userRepository.count();
    }
    
    public List<User> searchUsers(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllUsers();
        }
        return userRepository.searchUsers(keyword.trim());
    }
    
    public List<User> getUsersByCompany(Long companyId) {
        return userRepository.findByCompanyId(companyId);
    }
    
    public List<User> getUsersByRole(Role role) {
        return userRepository.findByRole(role);
    }
    
    public long countUsersByCompany(Long companyId) {
        return userRepository.countByCompanyId(companyId);
    }
    
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
