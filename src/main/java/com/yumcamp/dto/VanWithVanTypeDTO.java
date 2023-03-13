package com.yumcamp.dto;

import com.yumcamp.entity.Van;
import lombok.Data;


@Data
public class VanWithVanTypeDTO extends Van{
    private String vanTypeName;
}
