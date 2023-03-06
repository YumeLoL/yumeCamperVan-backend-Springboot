package com.yumcamp.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class VanType implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Integer vanTypeId;

    private String vanTypeName;

    @TableField(fill = FieldFill.INSERT) //insert autofill
    private LocalDateTime createdAt;

    @TableField(fill = FieldFill.INSERT_UPDATE) //insert & update autofill
    private LocalDateTime updatedAt;
}
