package com.example.nhom2_st22b_web2.controller;

import com.example.nhom2_st22b_web2.models.Company;
import com.example.nhom2_st22b_web2.models.Member;
import com.example.nhom2_st22b_web2.models.User;
import com.example.nhom2_st22b_web2.services.CompanyService;
import com.example.nhom2_st22b_web2.services.MemberService;
import com.example.nhom2_st22b_web2.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
@RequestMapping("/export")
public class DataExportController {

    @Autowired
    private MemberService memberService;
    
    @Autowired
    private CompanyService companyService;
    
    @Autowired
    private UserService userService;

    @GetMapping("/data-sql")
    public ResponseEntity<ByteArrayResource> exportDataSql() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PrintWriter writer = new PrintWriter(baos, true, StandardCharsets.UTF_8);
            
            // Header
            writer.println("-- Dữ liệu được export từ ứng dụng Spring Boot");
            writer.println("-- Ngày export: " + java.time.LocalDateTime.now());
            writer.println();
            
            // Export Member data
            List<Member> members = memberService.getAllMembers();
            if (!members.isEmpty()) {
                writer.println("-- Dữ liệu mẫu cho bảng Member");
                for (Member member : members) {
                    writer.println("INSERT INTO member (name, email, phone, address) VALUES " +
                            "('" + escapeSql(member.getName()) + "', " +
                            "'" + escapeSql(member.getEmail()) + "', " +
                            "'" + escapeSql(member.getPhone()) + "', " +
                            "'" + escapeSql(member.getAddress()) + "');");
                }
                writer.println();
            }
            
            // Export Company data
            List<Company> companies = companyService.getAllCompanies();
            if (!companies.isEmpty()) {
                writer.println("-- Dữ liệu mẫu cho bảng Company");
                for (Company company : companies) {
                    writer.println("INSERT INTO companies (name, code, description, address, phone, email) VALUES " +
                            "('" + escapeSql(company.getName()) + "', " +
                            "'" + escapeSql(company.getCode()) + "', " +
                            "'" + escapeSql(company.getDescription()) + "', " +
                            "'" + escapeSql(company.getAddress()) + "', " +
                            "'" + escapeSql(company.getPhone()) + "', " +
                            "'" + escapeSql(company.getEmail()) + "');");
                }
                writer.println();
            }
            
            // Export User data
            List<User> users = userService.getAllUsers();
            if (!users.isEmpty()) {
                writer.println("-- Dữ liệu mẫu cho bảng User");
                for (User user : users) {
                    String companyId = user.getCompany() != null ? user.getCompany().getId().toString() : "NULL";
                    writer.println("INSERT INTO users (username, full_name, email, phone, address, role, company_id) VALUES " +
                            "('" + escapeSql(user.getUsername()) + "', " +
                            "'" + escapeSql(user.getFullName()) + "', " +
                            "'" + escapeSql(user.getEmail()) + "', " +
                            "'" + escapeSql(user.getPhone()) + "', " +
                            "'" + escapeSql(user.getAddress()) + "', " +
                            "'" + user.getRole() + "', " +
                            companyId + ");");
                }
            }
            
            writer.close();
            
            ByteArrayResource resource = new ByteArrayResource(baos.toByteArray());
            
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"data_export.sql\"")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
                    
        } catch (Exception e) {
            // Log error for debugging
            System.err.println("Error exporting data: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
    
    private String escapeSql(String value) {
        if (value == null) return "";
        return value.replace("'", "''");
    }
}
