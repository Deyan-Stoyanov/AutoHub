package com.autohub.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
class WebConfigurer implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/content/blog/**").addResourceLocations("file:\\C:\\Users\\Lenovo\\autohub\\images\\blog_images\\");
        registry.addResourceHandler("/content/users/**").addResourceLocations("file:\\C:\\Users\\Lenovo\\autohub\\images\\user_images\\");
        registry.addResourceHandler("/content/cars/**").addResourceLocations("file:\\C:\\Users\\Lenovo\\autohub\\images\\car_images\\");
        registry.addResourceHandler("/content/parts/**").addResourceLocations("file:\\C:\\Users\\Lenovo\\autohub\\images\\part_images\\");
    }
}
