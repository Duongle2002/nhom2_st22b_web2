package com.example.nhom2_st22b_web2.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

    @GetMapping("/admin-only")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminOnly(Model model) {
        model.addAttribute("message", "Chỉ ADMIN mới có thể truy cập trang này!");
        model.addAttribute("role", "ADMIN");
        return "test_page";
    }

    @GetMapping("/manager-only")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public String managerOnly(Model model) {
        model.addAttribute("message", "ADMIN và MANAGER có thể truy cập trang này!");
        model.addAttribute("role", "MANAGER");
        return "test_page";
    }

    @GetMapping("/user-only")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'USER')")
    public String userOnly(Model model) {
        model.addAttribute("message", "ADMIN, MANAGER và USER có thể truy cập trang này!");
        model.addAttribute("role", "USER");
        return "test_page";
    }

    @GetMapping("/public")
    public String publicPage(Model model) {
        model.addAttribute("message", "Tất cả mọi người đều có thể truy cập trang này!");
        model.addAttribute("role", "PUBLIC");
        return "test_page";
    }
} 