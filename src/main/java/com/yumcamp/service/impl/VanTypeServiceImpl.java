package com.yumcamp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yumcamp.entity.VanType;
import com.yumcamp.mapper.CarTypeMapper;
import com.yumcamp.service.VanTypeService;
import org.springframework.stereotype.Service;

@Service
public class VanTypeServiceImpl extends ServiceImpl<CarTypeMapper, VanType> implements VanTypeService {
}
