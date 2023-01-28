/**
 * Copyright (c) 2023 Marcs App.
 * All rights reserved.
 */
package com.marcs.configs;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.marcs.sql.builder.DatabaseConnectionBuilder;

/**
 * Application Configs for datasource objects.
 * 
 * @author Sam Butler
 * @since April 25, 2022
 */
@Configuration
public class DatasourceConfiguration {

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String dbUsername;

    @Value("${spring.datasource.password}")
    private String dbPassword;

    /**
     * Datasource configuration. This will get called anywhere a {@link DataSource}
     * is autowired into the class. This will only be run when on development and
     * production environment.
     * 
     * @return {@link DataSource} object.
     */
    @Bean
    @Profile({ "production", "local" })
    @ConfigurationProperties("spring.datasource")
    public DataSource dataSource() {
        return DatabaseConnectionBuilder.create().useDefaultProperties().url(dbUrl).username(dbUsername)
                .password(dbPassword).build();
    }
}