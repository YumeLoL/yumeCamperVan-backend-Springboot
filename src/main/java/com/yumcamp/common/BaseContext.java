package com.yumcamp.common;

/**
 *  encapsulate a utility class based on ThreadLocal，record current logged-in member's id
 */
public class BaseContext {
    private static final ThreadLocal<Long> threadLocal = new ThreadLocal<>(); //Long id

    /**
     * set id
     * @param id
     */
    public static void setCurrentId(Long id){
        threadLocal.set(id);
    }

    /**
     * get id
     * @return
     */
    public static Long getCurrentId(){
        return threadLocal.get();
    }
}