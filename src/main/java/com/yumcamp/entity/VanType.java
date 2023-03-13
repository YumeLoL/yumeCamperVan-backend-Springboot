package com.yumcamp.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class VanType implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long vanTypeId;

    private String vanTypeName;

    @TableField(fill = FieldFill.INSERT) //insert autofill
    private LocalDateTime createdAt;

    @TableField(fill = FieldFill.INSERT_UPDATE) //insert & update autofill
    private LocalDateTime updatedAt;

    @TableField(fill = FieldFill.INSERT)
    private Long createUser;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;
}
