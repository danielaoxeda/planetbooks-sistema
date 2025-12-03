package com.planetbooks.config;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        Path uploadDir = Paths.get("uploads/books");
        String uploadPath = uploadDir.toFile().getAbsolutePath();

        registry.addResourceHandler("/img/books/**")
                .addResourceLocations("file:" + uploadPath + "/");
    }

    @PostConstruct
    public void init() {
        Path uploadDir = Paths.get("uploads/books");
        System.out.println("Serving book images from: " + uploadDir.toFile().getAbsolutePath());
    }
}
