package com.autohub.config;

import com.autohub.web.interceptors.LoggerGetInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class LoggerGetInterceptorConfig implements WebMvcConfigurer {
    private final LoggerGetInterceptor loggerGetInterceptor;

    @Autowired
    public LoggerGetInterceptorConfig(LoggerGetInterceptor loggerGetInterceptor) {
        this.loggerGetInterceptor = loggerGetInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loggerGetInterceptor);
    }
}
