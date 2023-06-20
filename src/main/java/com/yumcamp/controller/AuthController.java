package com.yumcamp.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yumcamp.common.BaseContext;
import com.yumcamp.common.R;
import com.yumcamp.entity.Member;
import com.yumcamp.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

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

        // The input member's password is hashed using MD5
        String password = DigestUtils.md5DigestAsHex(member.getMemberPassword().getBytes());

        // search the member email in database
        LambdaQueryWrapper<Member> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Member::getMemberEmail, member.getMemberEmail());
        Member emp = memberService.getOne(lambdaQueryWrapper);

        // If there is no member with that email, an error message is returned
        if(emp == null){
            return R.error("The member account does not exist!");
        }

        // If there is a member with that email, their hashed password is compared to the input password
        if(!emp.getMemberPassword().equals(password)){
            return R.error("Wrong password");
        }

        // If the passwords match, the member's ID is saved in the session
        request.getSession().setAttribute("member", emp.getMemberId());
//        request.getSession().setMaxInactiveInterval(30);
        log.info("....member :{} is login....",request.getSession().getAttribute("member"));

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


    /**
     * some page or button may need loginCheck before redirect
     * @param request
     * @return
     */
    @GetMapping("/loginCheck")
    public R<String> loginCheck(HttpServletRequest request){
        if(request.getAttribute("member") != null) {
            return R.success("Member already logged in");
        }else {return R.error("Member not login yet, please login in first");}
    }
}

