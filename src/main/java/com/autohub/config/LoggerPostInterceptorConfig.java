package com.autohub.config;

import com.autohub.web.interceptors.LoggerPostInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class LoggerPostInterceptorConfig implements WebMvcConfigurer {
    private final LoggerPostInterceptor loggerPostInterceptor;

    @Autowired
    public LoggerPostInterceptorConfig(LoggerPostInterceptor loggerPostInterceptor) {
        this.loggerPostInterceptor = loggerPostInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loggerPostInterceptor);
    }
}
