package com.yumcamp.dto;

import com.yumcamp.entity.Van;
import com.yumcamp.entity.VanImg;
import lombok.Data;

import java.util.List;


@Data
public class VanDTO extends Van{
    private String vanTypeName;
    private List<String> vanImg;
}
