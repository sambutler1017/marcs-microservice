package com.marcs.configs;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.context.annotation.Bean;

import com.marcs.common.date.TimeZoneUtil;

/**
 * Configuration for setting default system time zone.
 * 
 * @author Sam Butler
 * @since January 3, 2023
 */
public class LocalDateConfiguration {

    @Bean
    public LocalDateTime localDateTimeZone() {
        return LocalDateTime.now(TimeZoneUtil.SYSTEM_ZONE);
    }

    @Bean
    public LocalDate localDateZone() {
        return LocalDate.now(TimeZoneUtil.SYSTEM_ZONE);
    }
}
