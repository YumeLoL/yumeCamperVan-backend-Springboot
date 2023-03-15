package com.yumcamp.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yumcamp.common.R;
import com.yumcamp.entity.Employee;
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

@Slf4j
@RestController
@RequestMapping("/member")
public class MemberController {
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
        // hash user password by md5
        String password = DigestUtils.md5DigestAsHex(member.getMemberPassword().getBytes());
        //String password = member.getMemberPassword();
        // search the username in database
        // sql: SELECT * FROM Employee WHERE username = '?'
        LambdaQueryWrapper<Member> employeeLambdaQueryWrapper = new LambdaQueryWrapper<>();
        employeeLambdaQueryWrapper.eq(Member::getMemberEmail, member.getMemberEmail());
        Member mem = memberService.getOne(employeeLambdaQueryWrapper);

        // if there is no such member email account exists in the database
        if(mem == null){
            return R.error("The email account does not exist!");
        }

        // check password
        if(!mem.getMemberPassword().equals(password)){
            return R.error("Wrong password");
        }

        // if login, save employee info in session
        request.getSession().setAttribute("member", mem.getMemberId());

        return R.success(mem);
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
        member.setMemberName("Member"); // set an initial member name
        // other value like updateTime, createTime, updateUser, and createUser being managed by Auto-fill handler

        memberService.save(member);
        return R.success("Add a new employee successful");
    }
};



