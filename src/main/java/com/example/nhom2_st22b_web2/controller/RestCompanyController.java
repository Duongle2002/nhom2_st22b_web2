package com.example.nhom2_st22b_web2.controller;

import com.example.nhom2_st22b_web2.models.Company;
import com.example.nhom2_st22b_web2.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
public class RestCompanyController {
    @Autowired
    private CompanyService companyService;

    @GetMapping
    public List<Company> getAllCompanies() {
        return companyService.getAllCompanies();
    }

    @PostMapping
    public void createCompany(@RequestBody Company company) {
        companyService.saveOrUpdate(company);
    }

    @GetMapping("/{id}")
    public Company getCompanyById(@PathVariable Integer id) {
        return companyService.findById(id);
    }

    @PutMapping("/{id}")
    public void updateCompany(@PathVariable Integer id, @RequestBody Company company) {
        company.setId(id);
        companyService.saveOrUpdate(company);
    }

    @DeleteMapping("/{id}")
    public void deleteCompany(@PathVariable Integer id) {
        companyService.deleteCompany(id);
    }
}