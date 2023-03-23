package com.yumcamp.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yumcamp.common.R;
import com.yumcamp.entity.Member;
import com.yumcamp.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping
public class AuthController {
    @Autowired
    private MemberService memberService;


    /**
     * Member Login
     * @param member
     * @param request
     * @return
     */
    @PostMapping("/login")
    public R<Member> login(@RequestBody Member member, HttpServletRequest request){
        log.info("member login: {}", member);

        // hash user password by md5
        String password = DigestUtils.md5DigestAsHex(member.getMemberPassword().getBytes());

        // search the member email in database
        LambdaQueryWrapper<Member> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Member::getMemberEmail, member.getMemberEmail());
        Member emp = memberService.getOne(lambdaQueryWrapper);

        // if there is no such member email exists in the database
        if(emp == null){
            return R.error("The member account does not exist!");
        }

        // check password
        if(!emp.getMemberPassword().equals(password)){
            return R.error("Wrong password");
        }

        // if login, save member info in session
        request.getSession().setAttribute("member", emp.getMemberId());
        log.info("Session ID when setting attribute: {}", request.getSession().getId());

        return R.success(emp);
    }


    /**
     * member logout
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        request.getSession().removeAttribute("member");
        return R.success("Logout successful");
    }



    /**
     * sign up as a new member
     * @param member
     * @return
     */
    @PostMapping("/signup")
    public R<String> signup(@RequestBody Member member){
        // init password using Hash md5
        String password = member.getMemberPassword();
        member.setMemberPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
        member.setMemberName("member"); // set an initial member name
        // other value like updateTime, createTime, updateUser, and createUser being managed by Auto-fill handler

        memberService.save(member);
        return R.success("Add a new member successful");
    }

}

