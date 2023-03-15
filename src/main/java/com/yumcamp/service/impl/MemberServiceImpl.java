package com.yumcamp.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yumcamp.entity.Member;
import com.yumcamp.mapper.MemberMapper;
import com.yumcamp.service.MemberService;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {
}
