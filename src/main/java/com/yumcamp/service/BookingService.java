package com.yumcamp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yumcamp.entity.Booking;
import com.yumcamp.enums.BookingStatus;

import java.time.LocalDate;
import java.util.List;

public interface BookingService extends IService<Booking> {
    List<Booking> findByVanIdAndOrderStatus(String vanId, BookingStatus status);
}
