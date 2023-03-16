package com.yumcamp.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yumcamp.common.R;
import com.yumcamp.entity.Member;
import com.yumcamp.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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
    public R<Member> login(@RequestBody Member member, HttpServletRequest request,HttpServletResponse response,
                           HttpSession session){
        // hash user password by md5
        String password = DigestUtils.md5DigestAsHex(member.getMemberPassword().getBytes());

        // search the username in database
        LambdaQueryWrapper<Member> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Member::getMemberEmail, member.getMemberEmail());
        Member mem = memberService.getOne(queryWrapper);

        // if there is no such member email account exists in the database
        if(mem == null){
            return R.error("The email account does not exist!");
        }
        // check password
        if(!mem.getMemberPassword().equals(password)){
            return R.error("Wrong password");
        }

        // after verify credentials, save employee info in session
        request.getSession().setAttribute("member", mem.getMemberId());

        // Set a cookie to store the user's login credentials
        //String cookieId = UUID.randomUUID().toString();
        //Cookie cookie = new Cookie("cookie_id", cookieId);
        //cookie.setMaxAge(60 * 1 * 1); // Set cookie expiration to 1 day
        //cookie.setHttpOnly(true); // Set the HttpOnly flag to prevent client-side access
        //response.addCookie(cookie);

        //log.info("cookie is {}", cookie.getValue());

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
}
