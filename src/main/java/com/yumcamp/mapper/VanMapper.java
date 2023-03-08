package com.yumcamp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yumcamp.entity.Van;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface VanMapper extends BaseMapper<Van> {

}
