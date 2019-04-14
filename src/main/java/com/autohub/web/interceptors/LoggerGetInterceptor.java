package com.autohub.web.interceptors;

import com.autohub.util.AutoHubLogger;
import com.autohub.util.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoggerGetInterceptor extends HandlerInterceptorAdapter {
    private final AutoHubLogger logger;

    @Autowired
    public LoggerGetInterceptor(AutoHubLogger logger) {
        this.logger = logger;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getMethod().toUpperCase().equals(Const.GET_METHOD_TYPE)) {
            if (response.getStatus() >= Const.NOT_FOUND_RESPONSE_CODE) {
                logger.severe(request.getRequestURL().toString());
            } else {
                logger.info(request.getRequestURL().toString());
            }
        }
        return true;
    }
}
