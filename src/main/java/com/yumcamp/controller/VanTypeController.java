package com.yumcamp.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yumcamp.common.R;
import com.yumcamp.entity.VanType;
import com.yumcamp.service.VanTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/vanType")
public class VanTypeController {
    @Autowired
    private VanTypeService vanTypeService;

    @GetMapping
    public R<List<VanType>> getAllType(){
        List<VanType> list = vanTypeService.list();
        return R.success(list);
    }

    @GetMapping("/{vanTypeId}")
    public R<VanType> getVanType(@PathVariable Long vanTypeId){
        VanType vanType = vanTypeService.getById(vanTypeId);
        return R.success(vanType);
    }
}
