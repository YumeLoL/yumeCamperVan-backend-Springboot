package com.yumcamp.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * auto-fill handler
 */
@Component
@Slf4j
public class MyMetaObjecthandler implements MetaObjectHandler {

    /**
     * Auto-fill specified fields when inserting records
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("Auto-fill specified fields [insert]...");
        //log.info(metaObject.toString());

        metaObject.setValue("createTime", LocalDateTime.now());
        metaObject.setValue("updateTime", LocalDateTime.now());

        // use method BaseContext to get current logined employee's id
        metaObject.setValue("createUser",BaseContext.getCurrentId());
        metaObject.setValue("updateUser",BaseContext.getCurrentId());
    }

    /**
     * Auto-fill specified fields when updating records
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("Auto-fill specified fields [update]...");
        //log.info(metaObject.toString());

        long id = Thread.currentThread().getId();
        //log.info("thread id：{}",id);

        metaObject.setValue("updateTime",LocalDateTime.now());
        metaObject.setValue("updateUser",BaseContext.getCurrentId());

    }
}
