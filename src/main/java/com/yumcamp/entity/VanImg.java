package com.yumcamp.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VanImg {
    private Long vanId;
    private Long imgId;
    private String imgUrl;

    @TableField(fill = FieldFill.INSERT) //insert autofill
    private LocalDateTime createdAt;

    @TableField(fill = FieldFill.INSERT_UPDATE) //insert & update autofill
    private LocalDateTime updatedAt;

    @TableField(fill = FieldFill.INSERT)
    private Long createUser;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;
}
