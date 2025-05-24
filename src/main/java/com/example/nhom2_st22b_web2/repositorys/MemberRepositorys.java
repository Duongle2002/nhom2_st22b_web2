package com.example.nhom2_st22b_web2.repositorys;

import com.example.nhom2_st22b_web2.models.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepositorys extends JpaRepository<Member , Long> {
}
