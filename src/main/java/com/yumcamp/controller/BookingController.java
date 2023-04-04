package com.yumcamp.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yumcamp.common.R;
import com.yumcamp.dto.BookingWithVanDTO;
import com.yumcamp.entity.Booking;
import com.yumcamp.entity.Van;
import com.yumcamp.entity.VanImg;
import com.yumcamp.entity.VanType;
import com.yumcamp.enums.BookingStatus;
import com.yumcamp.service.BookingService;
import com.yumcamp.service.VanImgService;
import com.yumcamp.service.VanService;
import com.yumcamp.service.VanTypeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value="/member/bookings")
public class BookingController {
    @Autowired
    private BookingService bookingService;
    @Autowired
    private VanService vanService;
    @Autowired
    private VanTypeService vanTypeService;
    @Autowired
    private VanImgService vanImgService;

    /**
     * get all booking requests
     * @return
     */
    @GetMapping("/all")
    public R<List<Booking>> getAllBookings() {
        List<Booking> bookingList = bookingService.list();
        return R.success(bookingList);
    }

    /**
     * save a new booking
     * config 'consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE', because 'Content-Type':'application/x-www-form-urlencoded'
     * using the @ModelAttribute annotation to save data into Booking directly
     * @param booking
     * @return
     */
    @PostMapping(value = "/request", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public R<String> saveNew(@ModelAttribute Booking booking){
        // Access the form data using the @ModelAttribute annotation
        bookingService.save(booking);
        return R.success("make a new booking request successfully");
    }


    /**
     * get all bookings by memberId with bookingStatus
     * @param memberId
     * @param bookingStatus
     * @return
     */
    @GetMapping("/{memberId}")
    public R<List<BookingWithVanDTO>> getBookingsByMemberId(@PathVariable String memberId, BookingStatus bookingStatus){
        LambdaQueryWrapper<Booking> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Booking::getMemberId, memberId);
        queryWrapper.eq(Booking::getBookingStatus, bookingStatus);
        List<Booking> bookingList = bookingService.list(queryWrapper);

        List<BookingWithVanDTO> dtoList = new ArrayList<>();
        for (Booking booking : bookingList) {
            BookingWithVanDTO dto = new BookingWithVanDTO();
            BeanUtils.copyProperties(booking, dto);
            Van van = vanService.getById(booking.getVanId());
            VanType vanType = vanTypeService.getById(van.getVanTypeId());
            if (van != null) {
                dto.setVanName(van.getVanName());
                dto.setVanTypeName(vanType.getVanTypeName());
                dto.setVanLocation(van.getVanLocation());
                dto.setVanPricePerDay(van.getVanPricePerDay());

                // Fetch the first image URL associated with the van
                List<VanImg> vanImgList = vanImgService.list(new QueryWrapper<VanImg>().eq("van_id", van.getVanId()));
                if (!vanImgList.isEmpty()) {
                    dto.setImgUrl(vanImgList.get(0).getImgUrl());
                }
            }
            dtoList.add(dto);
        }

        return R.success(dtoList);
    }


    /**
     * Get booked dates by vanId
     * Only 'confirmed' bookings being selected
     * @param vanId
     * @return
     */
    @GetMapping("/disabledDates/{vanId}")
    public R<List<LocalDate>> getBookedDatesByVanId(@PathVariable String vanId) {
        // get all confirmed bookings by vanId
        List<Booking> bookings = bookingService.findByVanIdAndOrderStatus(vanId, BookingStatus.confirmed);

        List<LocalDate> bookedDates = new ArrayList<>();
        for (Booking booking : bookings) {
            LocalDate startDate = booking.getStartDate();
            LocalDate endDate = booking.getEndDate();
            bookedDates.addAll(getDatesBetween(startDate, endDate));
        }

        return R.success(bookedDates);
    }


    private List<LocalDate> getDatesBetween(LocalDate startDate, LocalDate endDate) {
        List<LocalDate> dates = new ArrayList<>();
        LocalDate date = startDate;
        while (!date.isAfter(endDate)) {
            dates.add(date);
            date = date.plusDays(1);
        }
        return dates;
    }

}
