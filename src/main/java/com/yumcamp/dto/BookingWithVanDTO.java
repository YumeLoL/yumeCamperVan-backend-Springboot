package com.yumcamp.dto;

import com.yumcamp.entity.Booking;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class BookingWithVanDTO extends Booking {
    private String vanName;
    private String vanTypeName;
    private String imgUrl;
    private String vanLocation;
    private BigDecimal vanPricePerDay;
}
