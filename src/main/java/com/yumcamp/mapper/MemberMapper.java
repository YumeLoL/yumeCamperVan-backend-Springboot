package com.yumcamp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yumcamp.entity.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper extends BaseMapper<Member> {
}
