package com.yumcamp.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.yumcamp.enums.BookingStatus;
import lombok.Data;

import java.io.Serial;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class Booking {
    @Serial
    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.ASSIGN_UUID)
    private String bookingId;
    private String vanId;
    private String memberId;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal price;
    private Integer guest;
    private BookingStatus bookingStatus; // confirmed, cancelled, completed
    @TableField(fill = FieldFill.INSERT) //insert autofill
    private LocalDateTime createdAt;
    @TableField(fill = FieldFill.INSERT_UPDATE) //insert & update autofill
    private LocalDateTime updatedAt;
    @TableField(fill = FieldFill.INSERT)
    private String createUser;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateUser;
}
