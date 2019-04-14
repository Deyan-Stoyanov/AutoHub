package com.autohub.web.interceptors;

import com.autohub.util.AutoHubLogger;
import com.autohub.util.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoggerPostInterceptor extends HandlerInterceptorAdapter {
    private final AutoHubLogger logger;

    @Autowired
    public LoggerPostInterceptor(AutoHubLogger logger) {
        this.logger = logger;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (request.getMethod().toUpperCase().equals(Const.GET_METHOD_TYPE)) {
            if (response.getStatus() >= Const.NOT_FOUND_RESPONSE_CODE) {
                logger.severe(request.getRequestURL().toString());
            } else {
                logger.info(request.getRequestURL().toString());
            }
        }
    }
}
