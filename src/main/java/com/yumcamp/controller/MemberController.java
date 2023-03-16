package com.yumcamp.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yumcamp.common.R;
import com.yumcamp.entity.Member;
import com.yumcamp.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/member")
public class MemberController {
    @Autowired
    private MemberService memberService;


    @GetMapping
    public R<List<Member>> getAllMember(){
        List<Member> memberList = memberService.list();
        return R.success(memberList);
    }
};



