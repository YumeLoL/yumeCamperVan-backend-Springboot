package com.yumcamp.controller;

import com.yumcamp.common.R;
import com.yumcamp.entity.Booking;
import com.yumcamp.enums.BookingStatus;
import com.yumcamp.service.BookingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/bookings")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @GetMapping
    public R<List<Booking>> getAllBookings() {
        List<Booking> bookingList = bookingService.list();
        return R.success(bookingList);
    }

    /**
     * Get booked dates by vanId
     * Only 'confirmed' bookings being selected
     * @param vanId
     * @return
     */
    @GetMapping("/available-dates/{vanId}")
    public R<List<LocalDate>> getBookedDatesByVanId(@PathVariable Long vanId) {
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
