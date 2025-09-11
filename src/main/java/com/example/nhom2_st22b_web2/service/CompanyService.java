package com.example.nhom2_st22b_web2.service;

import com.example.nhom2_st22b_web2.models.Company;
import com.example.nhom2_st22b_web2.repositorys.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    public Company saveOrUpdate(Company company) {
        return companyRepository.save(company);
    }

    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    public Company findById(Integer id) {
        Optional<Company> company = companyRepository.findById(id);
        return company.orElse(null);
    }

    public void deleteCompany(Integer id) {
        companyRepository.deleteById(id);
    }
}