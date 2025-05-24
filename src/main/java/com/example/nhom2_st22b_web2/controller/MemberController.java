package com.example.nhom2_st22b_web2.controller;

import com.example.nhom2_st22b_web2.models.Member;
import com.example.nhom2_st22b_web2.services.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MemberController {

    @Autowired
    private MemberService memberService;

    @GetMapping("/")
    public String listMembers(Model model) {
        model.addAttribute("members", memberService.getAllMembers());
        return "member_list";
    }

    @GetMapping("/member/{id}")
    public String memberDetail(@PathVariable Long id, Model model) {
        Member member = memberService.getMemberById(id);
        model.addAttribute("member", member);
        return "member_detail";
    }

    @GetMapping("/add-member")
    public String showAddMemberForm(Model model) {
        model.addAttribute("member", new Member());
        return "add_member";
    }

    @PostMapping("/add-member")
    public String addMember(@ModelAttribute Member member) {
        memberService.saveMember(member);
        return "redirect:/";
    }
}