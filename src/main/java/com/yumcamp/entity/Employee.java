package com.yumcamp.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.yumcamp.enums.EmployeeRole;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Employee
 */
@Data
public class Employee implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Integer employeeId;

    private String employeeName;

    private String employeePassword;


    private EmployeeRole employeeRole; //  ('admin', 'staff') default 'staff'

    @TableField(fill = FieldFill.INSERT) //insert autofill
    private LocalDateTime createdAt;

    @TableField(fill = FieldFill.INSERT_UPDATE) //insert & update autofill
    private LocalDateTime updatedAt;


}
