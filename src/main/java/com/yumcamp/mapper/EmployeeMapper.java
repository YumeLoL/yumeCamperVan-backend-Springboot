package com.yumcamp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yumcamp.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
