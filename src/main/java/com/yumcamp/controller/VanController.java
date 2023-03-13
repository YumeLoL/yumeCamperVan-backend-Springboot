package com.yumcamp.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yumcamp.common.R;
import com.yumcamp.dto.VanWithVanTypeDTO;
import com.yumcamp.entity.Van;
import com.yumcamp.entity.VanType;
import com.yumcamp.enums.VanStatus;
import com.yumcamp.service.VanService;
import com.yumcamp.service.VanTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

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
    public R<Page<VanWithVanTypeDTO>> page(int page, int pageSize, String vanLocation, Integer berths, Long vanTypeId){
        Page<Van> vanInfo=new Page<>(page,pageSize);
        Page<VanWithVanTypeDTO> dtoPage = new Page<>(page,pageSize);

        LambdaQueryWrapper<Van> vanLambdaQueryWrapper = new LambdaQueryWrapper<>();
        // van status must be available
        vanLambdaQueryWrapper.eq(Van::getVanStatus, VanStatus.available);
        vanLambdaQueryWrapper.eq(vanTypeId != null, Van::getVanTypeId, vanTypeId);
        vanLambdaQueryWrapper.like(StringUtils.isNotEmpty(vanLocation), Van::getVanLocation, vanLocation);
        vanLambdaQueryWrapper.le(berths != null,Van::getBerths,berths);
        vanLambdaQueryWrapper.orderByDesc(Van::getCreatedAt);
        vanService.page(vanInfo,vanLambdaQueryWrapper);

        //copy dish to dishDto, get vanTypeName
        BeanUtils.copyProperties(vanInfo,dtoPage,"records");

        //copy 'records' separately
        List<Van> records = vanInfo.getRecords();
        List<VanWithVanTypeDTO> list=records.stream().map((item)->{
            VanWithVanTypeDTO dto=new VanWithVanTypeDTO();

            BeanUtils.copyProperties(item,dto);
            Long typeId = item.getVanTypeId();
            // to set vanTypeName by id
            VanType vanType = vanTypeService.getById(typeId);
            if(vanType!=null){
                String typeName = vanType.getVanTypeName();
                dto.setVanTypeName(typeName);
            }
            return dto;
        }).collect(Collectors.toList());

        dtoPage.setRecords(list);

        return R.success(dtoPage);
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
