package com.web.thuvien.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Cho phép tất cả các phương thức (GET, POST, PUT, DELETE)
        registry.addMapping("/**") // Áp dụng CORS cho tất cả các endpoint
                .allowedOrigins("http://localhost:4200") // Địa chỉ của frontend (chẳng hạn Angular chạy trên cổng 4200)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Các phương thức cho phép
                .allowedHeaders("*")
        ; // Cho phép tất cả các headers
    }
}
