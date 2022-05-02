package com.example.gccoffee.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfiguration implements WebMvcConfigurer { // 원하는 MVC 설정 확장 가능
    @Override
    public void addCorsMappings(CorsRegistry registry) { // api에 대한 cors 설정 가능
        registry.addMapping("/api/**").allowedOrigins("*");
    }
}
