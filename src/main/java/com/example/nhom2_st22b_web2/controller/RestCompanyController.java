package com.example.nhom2_st22b_web2.controller;

import com.example.nhom2_st22b_web2.models.Company;
import com.example.nhom2_st22b_web2.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/companies", produces = "application/json")
public class RestCompanyController {
    @Autowired
    private CompanyService companyService;

    @GetMapping
    public ResponseEntity<?> getAllCompanies(Authentication auth) {
        // ADMIN thấy tất cả; USER chỉ thấy công ty của mình
        boolean isAdmin = auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"));
        if (isAdmin) {
            return ResponseEntity.ok(companyService.getAllCompanies());
        }
        // For non-admins, return only the company of the current user (if needed, can return minimal fields)
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @PostMapping(consumes = "application/json")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Company> createCompany(@RequestBody Company company) {
        Company created = companyService.saveOrUpdate(company);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable Integer id) {
        Company company = companyService.findById(id);
        if (company == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(company);
    }

    @PutMapping(value = "/{id}", consumes = "application/json")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Company> updateCompany(@PathVariable Integer id, @RequestBody Company company) {
        company.setId(id);
        Company updated = companyService.saveOrUpdate(company);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteCompany(@PathVariable Integer id) {
        companyService.deleteCompany(id);
        return ResponseEntity.noContent().build();
    }
}