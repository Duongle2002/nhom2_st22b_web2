package com.example.nhom2_st22b_web2.repositorys;

import com.example.nhom2_st22b_web2.models.Company;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CompanyRepository extends CrudRepository<Company, Integer> {
    List<Company> findAll();
}