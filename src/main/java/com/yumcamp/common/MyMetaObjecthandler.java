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

        metaObject.setValue("createAt", LocalDateTime.now());
        metaObject.setValue("updateAt", LocalDateTime.now());

        // use method BaseContext to get current logined employee's id
//        metaObject.setValue("createUser",BaseContext.getCurrentId());
//        metaObject.setValue("updateUser",BaseContext.getCurrentId());
    }

    @Override
    public void updateFill(MetaObject metaObject) {

    }


}
