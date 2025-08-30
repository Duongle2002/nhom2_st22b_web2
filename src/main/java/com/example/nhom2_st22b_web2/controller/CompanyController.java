package com.example.nhom2_st22b_web2.controller;

import com.example.nhom2_st22b_web2.models.Company;
import com.example.nhom2_st22b_web2.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @GetMapping("/companies")
    public String listCompanies(Model model, @RequestParam(required = false) String search) {
        if (search != null && !search.trim().isEmpty()) {
            model.addAttribute("companies", companyService.searchCompanies(search));
            model.addAttribute("searchKeyword", search);
        } else {
            model.addAttribute("companies", companyService.getAllCompanies());
        }
        model.addAttribute("totalCompanies", companyService.countCompanies());
        return "company_list";
    }

    @GetMapping("/company/{id}")
    public String companyDetail(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Company> companyOpt = companyService.getCompanyById(id);
        
        if (companyOpt.isPresent()) {
            model.addAttribute("company", companyOpt.get());
            return "company_detail";
        } else {
            redirectAttributes.addFlashAttribute("error", "Không tìm thấy công ty với ID: " + id);
            return "redirect:/companies";
        }
    }

    @GetMapping("/add-company")
//    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public String showAddCompanyForm(Model model) {
        model.addAttribute("company", new Company());
        return "add_company";
    }

    @PostMapping("/add-company")
//    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public String addCompany(@ModelAttribute Company company, RedirectAttributes redirectAttributes) {
        try {
            companyService.saveCompany(company);
            redirectAttributes.addFlashAttribute("success", "Thêm công ty mới thành công!");
            return "redirect:/companies";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            redirectAttributes.addFlashAttribute("company", company);
            return "redirect:/add-company";
        }
    }
    
    @GetMapping("/edit-company/{id}")
//    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public String showEditCompanyForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Company> companyOpt = companyService.getCompanyById(id);
        
        if (companyOpt.isPresent()) {
            model.addAttribute("company", companyOpt.get());
            return "edit_company";
        } else {
            redirectAttributes.addFlashAttribute("error", "Không tìm thấy công ty với ID: " + id);
            return "redirect:/companies";
        }
    }
    
    @PostMapping("/edit-company/{id}")
//    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public String updateCompany(@PathVariable Long id, @ModelAttribute Company company, RedirectAttributes redirectAttributes) {
        try {
            companyService.updateCompany(id, company);
            redirectAttributes.addFlashAttribute("success", "Cập nhật công ty thành công!");
            return "redirect:/companies";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            redirectAttributes.addFlashAttribute("company", company);
            return "redirect:/edit-company/" + id;
        }
    }
    
    @GetMapping("/delete-company/{id}")
//    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public String deleteCompany(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            companyService.deleteCompany(id);
            redirectAttributes.addFlashAttribute("success", "Xóa công ty thành công!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/companies";
    }
}
