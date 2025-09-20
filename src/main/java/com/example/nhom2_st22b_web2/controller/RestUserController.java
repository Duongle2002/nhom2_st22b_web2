package com.example.nhom2_st22b_web2.controller;

import com.example.nhom2_st22b_web2.models.UserDemo;
import com.example.nhom2_st22b_web2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class RestUserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public List<UserDemo> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public void createUser(@RequestBody UserDemo userDemo) {
        userService.saveOrUpdate(userDemo);
    }

    @GetMapping("/{id}")
    public UserDemo getUserById(@PathVariable Integer id) {
        return userService.findById(id);
    }

    @PutMapping("/{id}")
    public void updateUser(@PathVariable Integer id, @RequestBody UserDemo userDemo) {
        userDemo.setId(id);
        userService.saveOrUpdate(userDemo);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
    }
}