package com.example.nhom2_st22b_web2.repositorys;

import com.example.nhom2_st22b_web2.models.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    
    Optional<Company> findByCode(String code);
    
    Optional<Company> findByEmail(String email);
    
    List<Company> findByNameContainingIgnoreCase(String name);
    
    @Query("SELECT c FROM Company c WHERE c.name LIKE %:keyword% OR c.code LIKE %:keyword% OR c.description LIKE %:keyword%")
    List<Company> searchCompanies(@Param("keyword") String keyword);
    
    boolean existsByCode(String code);
    
    boolean existsByEmail(String email);
}
