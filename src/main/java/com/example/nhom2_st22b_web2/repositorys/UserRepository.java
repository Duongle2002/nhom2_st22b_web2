package com.example.nhom2_st22b_web2.repositorys;

import com.example.nhom2_st22b_web2.models.Role;
import com.example.nhom2_st22b_web2.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByUsername(String username);
    
    Optional<User> findByEmail(String email);
    
    List<User> findByCompanyId(Long companyId);
    
    List<User> findByRole(Role role);
    
    @Query("SELECT u FROM User u WHERE u.fullName LIKE %:keyword% OR u.username LIKE %:keyword% OR u.email LIKE %:keyword%")
    List<User> searchUsers(@Param("keyword") String keyword);
    
    @Query("SELECT u FROM User u WHERE u.company.id = :companyId AND (u.fullName LIKE %:keyword% OR u.username LIKE %:keyword%)")
    List<User> searchUsersByCompany(@Param("companyId") Long companyId, @Param("keyword") String keyword);
    
    boolean existsByUsername(String username);
    
    boolean existsByEmail(String email);
    
    long countByCompanyId(Long companyId);
}
