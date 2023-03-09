package com.yumcamp.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yumcamp.common.R;
import com.yumcamp.entity.Van;
import com.yumcamp.entity.VanType;
import com.yumcamp.enums.VanStatus;
import com.yumcamp.service.VanService;
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
@RequestMapping("/campervan")
public class VanController {
    @Autowired
    private VanService vanService;

    @Autowired
    private VanTypeService vanTypeService;



    /**
     * get van list & condition query by location and/or berths
     * @param page
     * @param pageSize
     * @param vanLocation
     * @param berths
     * @return Van entity, VanType
     */
    @GetMapping("/page")
    public R<Page<Van>> page(int page, int pageSize, String vanLocation, Integer berths, Long vanTypeId){
        Page<Van> pageInfo=new Page<>(page,pageSize);

        LambdaQueryWrapper<Van> vanLambdaQueryWrapper = new LambdaQueryWrapper<>();
        // van status must be available
        vanLambdaQueryWrapper.eq(Van::getVanStatus, VanStatus.available);
        vanLambdaQueryWrapper.eq(vanTypeId != null, Van::getVanTypeId, vanTypeId);
        vanLambdaQueryWrapper.like(StringUtils.isNotEmpty(vanLocation), Van::getVanLocation, vanLocation);
        vanLambdaQueryWrapper.le(berths != null,Van::getBerths,berths);
        vanLambdaQueryWrapper.orderByDesc(Van::getCreatedAt);

        vanService.page(pageInfo,vanLambdaQueryWrapper);

        return R.success(pageInfo);
    }

    @GetMapping("/{vanId}")
    public R<Van> getVanById(@PathVariable Long vanId){
        Van van = vanService.getById(vanId);

        log.info("van: {}", van);

        if(van == null){
            return R.error("No such employee under this id");
        }

        return R.success(van);
    }


}
