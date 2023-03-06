package com.yumcamp.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yumcamp.common.R;
import com.yumcamp.entity.Van;
import com.yumcamp.service.VanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/campervan")
public class VanController {
    @Autowired
    private VanService carService;


    @GetMapping("/page")
    public R<Page<Van>> page(int page, int pageSize, String name){
        //log.info("page={},pageSize={},name={}", page, pageSize, name);

        Page<Van> pageInfo=new Page<>(page,pageSize);

        LambdaQueryWrapper<Van> queryWrapper = new LambdaQueryWrapper<>();
        // if search by name (name is not empty)
        queryWrapper.like(StringUtils.isNotEmpty(name), Van::getVanName,name);
        queryWrapper.orderByDesc(Van::getCreatedAt); // in desc order

        // search by page, pageSize, or and name
        carService.page(pageInfo,queryWrapper);

        return R.success(pageInfo);
    }

}
