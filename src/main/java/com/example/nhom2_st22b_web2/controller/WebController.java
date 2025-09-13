package com.example.nhom2_st22b_web2.controller;

import com.example.nhom2_st22b_web2.models.Company;
import com.example.nhom2_st22b_web2.models.UserDemo;
import com.example.nhom2_st22b_web2.service.CompanyService;
import com.example.nhom2_st22b_web2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class WebController {
    @Autowired
    private UserService userService;

    @Autowired
    private CompanyService companyService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new UserDemo());
        model.addAttribute("companies", companyService.getAllCompanies());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute UserDemo user) {
        userService.saveOrUpdate(user);
        return "redirect:/login";
    }

    @GetMapping("/users")
    public String listUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "user-list";
    }

    @GetMapping("/users/edit/{id}")
    public String showEditUserForm(@PathVariable Integer id, Model model) {
        UserDemo user = userService.findById(id);
        model.addAttribute("user", user);
        model.addAttribute("companies", companyService.getAllCompanies());
        return "edit-user";
    }

    @PostMapping("/users/edit")
    public String updateUser(@ModelAttribute UserDemo user) {
        userService.saveOrUpdate(user);
        return "redirect:/users";
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }

    @GetMapping("/companies")
    public String listCompanies(Model model) {
        model.addAttribute("companies", companyService.getAllCompanies());
        return "company-list";
    }

    @GetMapping("/companies/add")
    public String showAddCompanyForm(Model model) {
        model.addAttribute("company", new Company());
        return "add-company";
    }

    @PostMapping("/companies/add")
    public String addCompany(@ModelAttribute Company company) {
        companyService.saveOrUpdate(company);
        return "redirect:/companies";
    }

    @GetMapping("/companies/edit/{id}")
    public String showEditCompanyForm(@PathVariable Integer id, Model model) {
        Company company = companyService.findById(id);
        model.addAttribute("company", company);
        return "edit-company";
    }

    @PostMapping("/companies/edit")
    public String updateCompany(@ModelAttribute Company company) {
        companyService.saveOrUpdate(company);
        return "redirect:/companies";
    }

    @GetMapping("/companies/delete/{id}")
    public String deleteCompany(@PathVariable Integer id) {
        companyService.deleteCompany(id);
        return "redirect:/companies";
    }
}