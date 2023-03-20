package com.yumcamp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yumcamp.entity.Booking;
import com.yumcamp.enums.BookingStatus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BookingMapper extends BaseMapper<Booking> {
    @Select("SELECT * FROM booking WHERE van_id = #{vanId} AND booking_status = #{status}")
    List<Booking> findByVanIdAndOrderStatus(@Param("vanId") Long vanId, @Param("status") BookingStatus status);
}
