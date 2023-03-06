package com.yumcamp.common;

import lombok.Data;

/**
 * the server generic response data for common use
 * @param <T>
 */
@Data
public class R<T> {

    private Integer code; // 1 success; 0 fail

    private String msg; // error msg

    private T data; // response data

//    private Map map = new HashMap(); // dynamic data


    public static <T> R<T> success(T object) {
        R<T> r = new R<T>();
        r.data = object;
        r.code = 1;
        return r;
    }

    public static <T> R<T> error(String msg) {
        R<T> r = new R<>();
        r.msg = msg;
        r.code = 0;
        return r;
    }

//    public R<T> add(String key, Object value) {
//        this.map.put(key, value);
//        return this;
//    }
}
