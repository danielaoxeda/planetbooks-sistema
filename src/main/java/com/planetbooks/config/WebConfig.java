package com.planetbooks.config;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:C:/Users/Daniela/Documents/marco-desarrollo/planetbooks-frontend/uploads/");

    }
    @PostConstruct
    public void init() {
        System.out.println("Serving uploads from: C:/Users/Daniela/Documents/marco-desarrollo/planetbooks-frontend/uploads/");
    }

}
