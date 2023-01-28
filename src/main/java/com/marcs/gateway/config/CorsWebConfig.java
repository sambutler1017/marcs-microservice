/**
 * Copyright (c) 2023 Marcs App.
 * All rights reserved.
 */
package com.marcs.gateway.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * CORS Configuration to setting allowed origins and methods on the response
 * body.
 * 
 * @author Sam Butler
 * @since July 29, 2022
 */
@Configuration
@EnableWebMvc
public class CorsWebConfig implements WebMvcConfigurer {

    private static final String CORS_MAPPING = "/**";

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping(CORS_MAPPING)
                .allowedHeaders(HttpHeaders.AUTHORIZATION, HttpHeaders.CACHE_CONTROL, HttpHeaders.CONTENT_TYPE)
                .allowedMethods("GET", "POST", "PUT", "DELETE");
    }
}
