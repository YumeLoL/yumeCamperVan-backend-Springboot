package com.yumcamp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yumcamp.entity.Van;
import com.yumcamp.mapper.VanMapper;
import com.yumcamp.service.VanService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class VanServiceImpl extends ServiceImpl<VanMapper, Van> implements VanService {

}
