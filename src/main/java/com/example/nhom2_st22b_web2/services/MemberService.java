package com.example.nhom2_st22b_web2.services;

import com.example.nhom2_st22b_web2.models.Member;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.nhom2_st22b_web2.repositorys.MemberRepositorys;
import com.example.nhom2_st22b_web2.repositorys.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    @Autowired
    private MemberRepositorys memberRepository;

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    public Member getMemberById(Long id) {
        return memberRepository.findById(id).orElse(null);
    }

    public void saveMember(Member member) {
        memberRepository.save(member);
    }
}