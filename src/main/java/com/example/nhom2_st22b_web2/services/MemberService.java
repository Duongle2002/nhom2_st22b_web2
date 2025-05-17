package com.example.nhom2_st22b_web2.services;

import com.example.nhom2_st22b_web2.models.Member;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MemberService {
    private List<Member> members;

    public MemberService() {
        // Khởi tạo danh sách thành viên mẫu
        members = new ArrayList<>();
        members.add(new Member(1L, "Nguyễn Văn A", "a@gmail.com", "0123456789", "Hà Nội"));
        members.add(new Member(2L, "Trần Thị B", "b@gmail.com", "0987654321", "TP.HCM"));
        members.add(new Member(3L, "Lê Văn C", "c@gmail.com", "0912345678", "Đà Nẵng"));
    }

    public List<Member> getAllMembers() {
        return members;
    }

    public Member getMemberById(Long id) {
        return members.stream()
                .filter(member -> member.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}