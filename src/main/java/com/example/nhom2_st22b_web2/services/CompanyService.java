package com.example.nhom2_st22b_web2.services;

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

    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    public Optional<Company> getCompanyById(Long id) {
        return companyRepository.findById(id);
    }

    public Company saveCompany(Company company) {
        // Validation cơ bản
        if (company.getName() == null || company.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Tên công ty không được để trống");
        }
        if (company.getCode() == null || company.getCode().trim().isEmpty()) {
            throw new IllegalArgumentException("Mã công ty không được để trống");
        }
        if (company.getEmail() == null || company.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email công ty không được để trống");
        }
        
        // Kiểm tra mã công ty đã tồn tại chưa
        if (company.getId() == null && companyRepository.existsByCode(company.getCode())) {
            throw new IllegalArgumentException("Mã công ty đã tồn tại: " + company.getCode());
        }
        
        // Kiểm tra email đã tồn tại chưa
        if (company.getId() == null && companyRepository.existsByEmail(company.getEmail())) {
            throw new IllegalArgumentException("Email công ty đã tồn tại: " + company.getEmail());
        }
        
        return companyRepository.save(company);
    }
    
    public void deleteCompany(Long id) {
        if (!companyRepository.existsById(id)) {
            throw new IllegalArgumentException("Không tìm thấy công ty với ID: " + id);
        }
        companyRepository.deleteById(id);
    }
    
    public Company updateCompany(Long id, Company companyDetails) {
        Optional<Company> existingCompany = companyRepository.findById(id);
        
        if (existingCompany.isPresent()) {
            Company company = existingCompany.get();
            
            // Validation cơ bản
            if (companyDetails.getName() == null || companyDetails.getName().trim().isEmpty()) {
                throw new IllegalArgumentException("Tên công ty không được để trống");
            }
            if (companyDetails.getCode() == null || companyDetails.getCode().trim().isEmpty()) {
                throw new IllegalArgumentException("Mã công ty không được để trống");
            }
            if (companyDetails.getEmail() == null || companyDetails.getEmail().trim().isEmpty()) {
                throw new IllegalArgumentException("Email công ty không được để trống");
            }
            
            // Kiểm tra mã công ty đã tồn tại chưa (trừ chính nó)
            if (!company.getCode().equals(companyDetails.getCode()) && 
                companyRepository.existsByCode(companyDetails.getCode())) {
                throw new IllegalArgumentException("Mã công ty đã tồn tại: " + companyDetails.getCode());
            }
            
            // Kiểm tra email đã tồn tại chưa (trừ chính nó)
            if (!company.getEmail().equals(companyDetails.getEmail()) && 
                companyRepository.existsByEmail(companyDetails.getEmail())) {
                throw new IllegalArgumentException("Email công ty đã tồn tại: " + companyDetails.getEmail());
            }
            
            company.setName(companyDetails.getName());
            company.setCode(companyDetails.getCode());
            company.setDescription(companyDetails.getDescription());
            company.setAddress(companyDetails.getAddress());
            company.setPhone(companyDetails.getPhone());
            company.setEmail(companyDetails.getEmail());
            
            return companyRepository.save(company);
        } else {
            throw new IllegalArgumentException("Không tìm thấy công ty với ID: " + id);
        }
    }
    
    public boolean existsById(Long id) {
        return companyRepository.existsById(id);
    }
    
    public long countCompanies() {
        return companyRepository.count();
    }
    
    public List<Company> searchCompanies(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllCompanies();
        }
        return companyRepository.searchCompanies(keyword.trim());
    }
    
    public Optional<Company> findByCode(String code) {
        return companyRepository.findByCode(code);
    }
}
