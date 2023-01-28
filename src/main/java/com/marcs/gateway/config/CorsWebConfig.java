/**
 * Copyright (c) 2023 Marcs App.
 * All rights reserved.
 */
package com.marcs.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * CORS Configuration to setting allowed origins and methods on the response
 * body.
 * 
 * @author Sam Butler
 * @since July 29, 2022
 */
@Configuration
public class CorsWebConfig {
    private static final String CORS_MAPPING = "/**";

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping(CORS_MAPPING)
                        .allowedHeaders(HttpHeaders.AUTHORIZATION, HttpHeaders.CACHE_CONTROL, HttpHeaders.CONTENT_TYPE)
                        .allowedMethods("GET", "POST", "PUT", "DELETE");
            }
        };
    }
}
