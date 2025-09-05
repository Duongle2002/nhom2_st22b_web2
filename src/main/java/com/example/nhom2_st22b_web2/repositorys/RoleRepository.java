package com.example.nhom2_st22b_web2.repositorys;

import com.example.nhom2_st22b_web2.models.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
