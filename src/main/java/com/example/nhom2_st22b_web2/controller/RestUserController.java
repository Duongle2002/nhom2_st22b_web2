package com.example.nhom2_st22b_web2.controller;

import com.example.nhom2_st22b_web2.models.UserDemo;
import com.example.nhom2_st22b_web2.service.UserService;
import com.example.nhom2_st22b_web2.models.Company;
import com.example.nhom2_st22b_web2.models.Role;
import com.example.nhom2_st22b_web2.repositorys.RoleRepository;
import com.example.nhom2_st22b_web2.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping(value = "/api/users", produces = "application/json")
public class RestUserController {
    @Autowired
    private UserService userService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<UserDemo> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<?> createUser(@RequestBody UserRequest request) {
        try {
            UserDemo toSave = new UserDemo();
            toSave.setName(request.getName());
            toSave.setEmail(request.getEmail());
            toSave.setPassword(request.getPassword());

            // Map company by id if provided
            if (request.getCompany() != null && request.getCompany().getId() != null) {
                Company company = companyService.findById(request.getCompany().getId());
                if (company == null) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Company not found");
                }
                toSave.setCompany(company);
            }

            // Map roles by names if provided
            if (request.getRoles() != null && !request.getRoles().isEmpty()) {
                Set<Role> roles = new HashSet<>();
                for (String roleName : request.getRoles()) {
                    Optional<Role> roleOpt = roleRepository.findByName(roleName);
                    if (roleOpt.isEmpty()) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Role not found: " + roleName);
                    }
                    roles.add(roleOpt.get());
                }
                toSave.setRoles(roles);
            }

            UserDemo created = userService.saveOrUpdate(toSave);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or #id == principalUsernameId")
    public ResponseEntity<?> getUserById(@PathVariable Integer id, Authentication auth) {
        if (!isAdmin(auth) && !isSelf(auth, id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        UserDemo user = userService.findById(id);
        if (user == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(user);
    }

    @PutMapping(value = "/{id}", consumes = "application/json")
    public ResponseEntity<?> updateUser(@PathVariable Integer id, @RequestBody UserRequest request, Authentication auth) {
        if (!isAdmin(auth) && !isSelf(auth, id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        try {
            UserDemo toUpdate = new UserDemo();
            toUpdate.setId(id);
            toUpdate.setName(request.getName());
            toUpdate.setEmail(request.getEmail());
            toUpdate.setPassword(request.getPassword());

            if (request.getCompany() != null && request.getCompany().getId() != null) {
                Company company = companyService.findById(request.getCompany().getId());
                if (company == null) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Company not found");
                }
                toUpdate.setCompany(company);
            }

            if (request.getRoles() != null && !request.getRoles().isEmpty()) {
                Set<Role> roles = new HashSet<>();
                for (String roleName : request.getRoles()) {
                    Optional<Role> roleOpt = roleRepository.findByName(roleName);
                    if (roleOpt.isEmpty()) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Role not found: " + roleName);
                    }
                    roles.add(roleOpt.get());
                }
                toUpdate.setRoles(roles);
            }

            UserDemo updated = userService.saveOrUpdate(toUpdate);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    private boolean isAdmin(Authentication auth) {
        return auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"));
    }

    private boolean isSelf(Authentication auth, Integer userId) {
        if (auth == null || auth.getName() == null) return false;
        UserDemo current = userService.findByEmail(auth.getName()).orElse(null);
        return current != null && current.getId().equals(userId);
    }
}

// Simple request DTO for REST create/update
class UserRequest {
    private String name;
    private String email;
    private String password;
    private Set<String> roles;
    private CompanyRef company;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public Set<String> getRoles() { return roles; }
    public void setRoles(Set<String> roles) { this.roles = roles; }
    public CompanyRef getCompany() { return company; }
    public void setCompany(CompanyRef company) { this.company = company; }

    static class CompanyRef {
        private Integer id;
        public Integer getId() { return id; }
        public void setId(Integer id) { this.id = id; }
    }
}