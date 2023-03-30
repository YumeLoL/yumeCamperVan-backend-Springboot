package com.yumcamp.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yumcamp.common.R;
import com.yumcamp.dto.VanDTO;
import com.yumcamp.entity.Booking;
import com.yumcamp.entity.Van;
import com.yumcamp.entity.VanImg;
import com.yumcamp.entity.VanType;
import com.yumcamp.enums.VanStatus;
import com.yumcamp.service.BookingService;
import com.yumcamp.service.VanImgService;
import com.yumcamp.service.VanService;
import com.yumcamp.service.VanTypeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    private VanImgService vanImgService;


    /**
     * get van list & condition query by location and/or berths
     * @param page
     * @param pageSize
     * @param vanLocation
     * @param berths
     * @return Van entity, VanType
     */
    @GetMapping("/page")
    public R<Page<VanDTO>> page(int page, int pageSize, String vanLocation, Integer berths, Long vanTypeId,
                                HttpServletRequest request){
        log.info("member id is {}", request.getSession().getAttribute("member"));

        Page<Van> vanInfo=new Page<>(page,pageSize);
        Page<VanDTO> dtoPage = new Page<>(page,pageSize);

        LambdaQueryWrapper<Van> vanLambdaQueryWrapper = new LambdaQueryWrapper<>();
        // van status must be available
        vanLambdaQueryWrapper.eq(Van::getVanStatus, VanStatus.available);
        // conditional query
        vanLambdaQueryWrapper.eq(vanTypeId != null, Van::getVanTypeId, vanTypeId);
        vanLambdaQueryWrapper.like(StringUtils.isNotEmpty(vanLocation), Van::getVanLocation, vanLocation);
        vanLambdaQueryWrapper.le(berths != null,Van::getBerths,berths);
        vanLambdaQueryWrapper.orderByDesc(Van::getCreatedAt);
        vanService.page(vanInfo,vanLambdaQueryWrapper);

        //copy dish to dishDto, get vanTypeName
        BeanUtils.copyProperties(vanInfo,dtoPage,"records");
        //copy 'records' separately
        List<Van> records = vanInfo.getRecords();
        List<VanDTO> list=records.stream().map((item)->{
            VanDTO vanDTO=new VanDTO();

            BeanUtils.copyProperties(item,vanDTO);

            String vanId = item.getVanId();
            generateVanDTO(vanId, item, vanDTO);
            return vanDTO;
        }).collect(Collectors.toList());
        dtoPage.setRecords(list);

        return R.success(dtoPage);
    }

    /**
     * get all van details by id
     * @param vanId
     * @return
     */
    @GetMapping("/{vanId}")
    public R<VanDTO> getVanById(@PathVariable String vanId){
        Van van = vanService.getById(vanId);
        VanDTO vanDTO = new VanDTO();

        //copy dish to dishDto, get vanTypeName
        BeanUtils.copyProperties(van,vanDTO);
        generateVanDTO(vanId, van, vanDTO);

        return R.success(vanDTO);
    }



    private void generateVanDTO(@PathVariable String vanId, Van van, VanDTO vanDTO) {
        LambdaQueryWrapper<VanImg> imgWrapper=new LambdaQueryWrapper<>();
        imgWrapper.eq(VanImg::getVanId, vanId).select(VanImg::getImgUrl); // only select the img url
        List<String> imgUrls = vanImgService.list(imgWrapper)
                .stream()
                .map(VanImg::getImgUrl)
                .collect(Collectors.toList()); // extract the img urls
        vanDTO.setVanImg(imgUrls);

        String typeId = van.getVanTypeId();
        VanType vanType = vanTypeService.getById(typeId);
        if(vanType!=null){
            String typeName = vanType.getVanTypeName();
            vanDTO.setVanTypeName(typeName);
        }
    }


}
