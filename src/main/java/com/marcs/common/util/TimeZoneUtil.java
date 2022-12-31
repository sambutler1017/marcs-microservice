package com.marcs.common.util;

import java.time.ZoneId;

/**
 * Deals with timezone management
 * 
 * @author Sam Butler
 * @since December 31, 2022
 */
public class TimeZoneUtil {

    /**
     * Grabs the default zone to be used.
     * 
     * @return The default zone id.
     */
    public static ZoneId defaultZone() {
        return ZoneId.of("America/New_York");
    }
}
