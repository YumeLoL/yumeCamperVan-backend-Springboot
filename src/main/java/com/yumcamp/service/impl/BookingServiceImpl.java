package com.yumcamp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yumcamp.entity.Booking;
import com.yumcamp.enums.BookingStatus;
import com.yumcamp.mapper.BookingMapper;
import com.yumcamp.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookingServiceImpl extends ServiceImpl<BookingMapper, Booking> implements BookingService {
    @Autowired
    private BookingMapper bookingMapper;

    @Override
    public List<Booking> findByVanIdAndOrderStatus(String vanId, BookingStatus status) {
        return bookingMapper.findByVanIdAndOrderStatus(vanId, status);
    }
}
