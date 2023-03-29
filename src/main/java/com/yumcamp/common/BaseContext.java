package com.yumcamp.common;

/**
 *  encapsulate a utility class based on ThreadLocal，record current logged-in member's id
 */
public class BaseContext {
    private static final ThreadLocal<String> threadLocal = new ThreadLocal<>(); //String id

    /**
     * set id
     * @param id
     */
    public static void setCurrentId(String id){
        threadLocal.set(id);
    }

    /**
     * get id
     * @return
     */
    public static String getCurrentId(){
        return threadLocal.get();
    }
}