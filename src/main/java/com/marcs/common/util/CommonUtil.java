package com.marcs.common.util;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * Class for storing common methods for use accross the application.
 * 
 * @author Sam Butler
 * @since April 21, 2022
 */
public class CommonUtil {

    public static String formatDate(Date dt, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(dt);
    }

    public static String formatDate(LocalDateTime dt, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(dt);
    }

    public static LocalDateTime convertDateToLocalDate(Date d) {
        return d.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }
}
