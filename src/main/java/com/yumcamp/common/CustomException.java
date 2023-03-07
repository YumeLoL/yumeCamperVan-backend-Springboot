package com.yumcamp.common;

/**
 * custom exception
 */
public class CustomException extends RuntimeException {
    public CustomException(String message){
        super(message);
    }
}
