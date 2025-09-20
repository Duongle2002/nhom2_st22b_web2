package com.example.nhom2_st22b_web2.dto;

public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    private Integer companyId;

    public RegisterRequest() {}

    public RegisterRequest(String name, String email, String password, Integer companyId) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.companyId = companyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }
}
