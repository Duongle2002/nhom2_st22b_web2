package com.example.nhom2_st22b_web2.repository;

import com.example.nhom2_st22b_web2.model.UserDemo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<UserDemo, Integer> {
    List<UserDemo> findAll();
    Optional<UserDemo> findByEmail(String email);
}
