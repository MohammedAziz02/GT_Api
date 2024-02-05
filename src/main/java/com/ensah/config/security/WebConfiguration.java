package com.ensah.config.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Slf4j
public class WebConfiguration implements WebMvcConfigurer {

    public static Integer x= 3;
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        log.info("Adding CORS headers");
        registry.addMapping("/**") // Adjust the path to match your API endpoints
                .allowedOrigins("http://localhost:4200") // Allow requests from your Angular app's origin
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Specify the HTTP methods you want to allow
                .allowCredentials(true); // If you need to send credentials like cookies or headers, set this to true
    }
}