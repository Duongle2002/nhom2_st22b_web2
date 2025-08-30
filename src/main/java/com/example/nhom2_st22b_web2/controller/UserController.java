package com.example.nhom2_st22b_web2.controller;

import com.example.nhom2_st22b_web2.models.Company;
import com.example.nhom2_st22b_web2.models.Role;
import com.example.nhom2_st22b_web2.models.User;
import com.example.nhom2_st22b_web2.services.CompanyService;
import com.example.nhom2_st22b_web2.services.UserService;
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
public class UserController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private CompanyService companyService;

    @GetMapping("/users")
    public String listUsers(Model model, @RequestParam(required = false) String search) {
        if (search != null && !search.trim().isEmpty()) {
            model.addAttribute("users", userService.searchUsers(search));
            model.addAttribute("searchKeyword", search);
        } else {
            model.addAttribute("users", userService.getAllUsers());
        }
        model.addAttribute("totalUsers", userService.countUsers());
        return "user_list";
    }

    @GetMapping("/user/{id}")
    public String userDetail(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<User> userOpt = userService.getUserById(id);
        
        if (userOpt.isPresent()) {
            model.addAttribute("user", userOpt.get());
            return "user_detail";
        } else {
            redirectAttributes.addFlashAttribute("error", "Không tìm thấy người dùng với ID: " + id);
            return "redirect:/users";
        }
    }

    @GetMapping("/add-user")
    @PreAuthorize("hasRole('MANAGER')")
    public String showAddUserForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("companies", companyService.getAllCompanies());
        model.addAttribute("userRoles", Role.values());
        return "add_user";
    }

    @PostMapping("/add-user")
    @PreAuthorize("hasRole('MANAGER')")
    public String addUser(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        try {
            userService.saveUser(user);
            redirectAttributes.addFlashAttribute("success", "Thêm người dùng mới thành công!");
            return "redirect:/users";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            redirectAttributes.addFlashAttribute("user", user);
            redirectAttributes.addFlashAttribute("companies", companyService.getAllCompanies());
            redirectAttributes.addFlashAttribute("userRoles", Role.values());
            return "redirect:/add-user";
        }
    }
    
    @GetMapping("/edit-user/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    public String showEditUserForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<User> userOpt = userService.getUserById(id);
        
        if (userOpt.isPresent()) {
            model.addAttribute("user", userOpt.get());
            model.addAttribute("companies", companyService.getAllCompanies());
            model.addAttribute("userRoles", Role.values());
            return "edit_user";
        } else {
            redirectAttributes.addFlashAttribute("error", "Không tìm thấy người dùng với ID: " + id);
            return "redirect:/users";
        }
    }
    
    @PostMapping("/edit-user/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    public String updateUser(@PathVariable Long id, @ModelAttribute User user, RedirectAttributes redirectAttributes) {
        try {
            userService.updateUser(id, user);
            redirectAttributes.addFlashAttribute("success", "Cập nhật người dùng thành công!");
            return "redirect:/users";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            redirectAttributes.addFlashAttribute("user", user);
            redirectAttributes.addFlashAttribute("companies", companyService.getAllCompanies());
            redirectAttributes.addFlashAttribute("userRoles", Role.values());
            return "redirect:/edit-user/" + id;
        }
    }
    
    @GetMapping("/delete-user/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    public String deleteUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            userService.deleteUser(id);
            redirectAttributes.addFlashAttribute("success", "Xóa người dùng thành công!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/users";
    }
    
    @GetMapping("/users/company/{companyId}")
    public String getUsersByCompany(@PathVariable Long companyId, Model model, RedirectAttributes redirectAttributes) {
        Optional<Company> companyOpt = companyService.getCompanyById(companyId);
        
        if (companyOpt.isPresent()) {
            Company company = companyOpt.get();
            model.addAttribute("company", company);
            model.addAttribute("users", userService.getUsersByCompany(companyId));
            model.addAttribute("totalUsers", userService.countUsersByCompany(companyId));
            return "company_users";
        } else {
            redirectAttributes.addFlashAttribute("error", "Không tìm thấy công ty với ID: " + companyId);
            return "redirect:/companies";
        }
    }
}
