package com.yumcamp.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.yumcamp.enums.VanStatus;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Van implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.AUTO)
    private Long vanId;
    private String vanName;
    private Long vanTypeId;
    private String vanLocation;
    private Integer berths;
    private String vanDescription;
    //private String vanImageUrl;
    private VanStatus vanStatus;
    private BigDecimal vanPricePerDay;
    private Integer employeeId;
    @TableField(fill = FieldFill.INSERT) //insert autofill
    private LocalDateTime createdAt;
    @TableField(fill = FieldFill.INSERT_UPDATE) //insert & update autofill
    private LocalDateTime updatedAt;

    @TableField(fill = FieldFill.INSERT)
    private Long createUser;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;
}
