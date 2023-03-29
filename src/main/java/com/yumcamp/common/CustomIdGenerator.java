package com.yumcamp.common;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.yumcamp.entity.*;
import org.springframework.stereotype.Component;

import java.util.UUID;
@Component
public class CustomIdGenerator implements IdentifierGenerator {
    @Override
    public Number nextId(Object entity) {
        return null;
    }

    @Override
    public String nextUUID(Object entity) {
        if (entity instanceof Booking) {
            return "Booking" + UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10);
        } else if (entity instanceof Employee) {
            return "Employee" + UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10);
        }else if (entity instanceof Member) {
            return "Member" + UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10);
        }else if (entity instanceof Van) {
            return "Van" + UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10);
        }else if (entity instanceof VanType) {
            return "VanType" + UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10);
        }else if (entity instanceof VanImg) {
            return "VanImg" + UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10);
        }

        throw new UnsupportedOperationException("Unsupported entity type");
    }
}
