package com.yumcamp.config;

import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyHttpSessionListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent event) {

        event.getSession().setMaxInactiveInterval(60);
    }
}